package io.mateu.article2.booking.infra.secondary.persistence.booking;

import io.mateu.article2.booking.domain.booking.Booking;
import io.mateu.article2.booking.domain.booking.BookingRepository;
import io.mateu.article2.booking.domain.booking.valueobjects.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MongoBookingRepository implements BookingRepository {

    final BookingEntityRepository bookingEntityRepository;

    public MongoBookingRepository(BookingEntityRepository bookingEntityRepository) {
        this.bookingEntityRepository = bookingEntityRepository;
    }

    @Override
    public Mono<Void> save(Booking booking) {
        return bookingEntityRepository.save(new BookingEntity(
                booking.id().id(),
                booking.customerName().name(),
                booking.serviceDescription().description(),
                booking.startDate().date(),
                booking.endDate().date(),
                booking.value().value(),
                booking.status().name(),
                booking.customerName().name() + " " + booking.serviceDescription().description()
        )).then();
    }

    @Override
    public Mono<Page<Booking>> search(String text, Pageable pageable) {
        if (text == null) {
            text = "";
        }
        return bookingEntityRepository.findAllBySearchableTextContainingIgnoreCase(
                text, pageable
        ).map(b -> new Booking(
                        new BookingId(b.id),
                        new CustomerName(b.customerName),
                        new ServiceDescription(b.serviceDescription),
                        new BookingStartDate(b.serviceStartDate),
                        new BookingEndDate(b.serviceEndDate),
                        new BookingValue(b.value),
                        BookingStatus.valueOf(b.status)
                )).collectList().map(l -> new PageImpl<>(l, pageable, l.size()));
    }
}
