package io.mateu.article2.booking.infra.secondary.persistence.booking;

import io.mateu.article2.booking.domain.booking.Booking;
import io.mateu.article2.booking.domain.booking.BookingFactory;
import io.mateu.article2.booking.domain.booking.BookingRepository;
import io.mateu.article2.booking.domain.booking.BookingState;
import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.booking.infra.secondary.persistence.event.EventEntity;
import io.mateu.article2.booking.infra.secondary.persistence.event.EventEntityRepository;
import io.mateu.article2.booking.infra.secondary.persistence.event.EventProcessingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class MongoBookingRepository implements BookingRepository {

    private final BookingEntityRepository bookingEntityRepository;
    private final EventEntityRepository eventEntityRepository;
    private final BookingFactory bookingFactory;

    public MongoBookingRepository(BookingEntityRepository bookingEntityRepository, EventEntityRepository eventEntityRepository, BookingFactory bookingFactory) {
        this.bookingEntityRepository = bookingEntityRepository;
        this.eventEntityRepository = eventEntityRepository;
        this.bookingFactory = bookingFactory;
    }

    @Override
    public Mono<Void> save(Booking booking) {
        var state = booking.getState();
        return bookingEntityRepository.save(new BookingEntity(
                state.id().id(),
                        state.customerName().name(),
                        state.serviceDescription().description(),
                        state.startDate().date(),
                        state.endDate().date(),
                        state.value().value(),
                        state.status().name(),
                        state.customerName().name() + " " + state.serviceDescription().description()
        ))
                .then(Flux.fromStream(booking.popEvents().stream()
                        .map(e -> new EventEntity(
                                e.id(),
                                e.source(),
                                e.when(),
                                e.payload(),
                                EventProcessingStatus.Pending
                        )))
                        .flatMap(eventEntityRepository::save).then());
    }

    @Override
    public Mono<Page<Booking>> search(String text, Pageable pageable) {
        if (text == null) {
            text = "";
        }
        return bookingEntityRepository.findAllBySearchableTextContainingIgnoreCase(
                text, pageable
        ).map(this::mapEntityToBooking).collectList().map(l -> new PageImpl<>(l, pageable, l.size()));
    }

    private Booking mapEntityToBooking(BookingEntity entity) {
        return bookingFactory.ofState(new BookingState(
                new BookingId(entity.id),
                new CustomerName(entity.customerName),
                new ServiceDescription(entity.serviceDescription),
                new BookingStartDate(entity.serviceStartDate),
                new BookingEndDate(entity.serviceEndDate),
                new BookingValue(entity.value),
                BookingStatus.valueOf(entity.status)
        ));
    }

    @Override
    public Mono<Booking> findById(BookingId bookingId) {
        if (bookingId == null) {
            throw new IllegalArgumentException("Booking id cannot be null");
        }
        return bookingEntityRepository.findById(bookingId.id()).map(this::mapEntityToBooking);
    }
}
