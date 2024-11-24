package io.mateu.article2.booking.application.createbooking;

import io.mateu.article2.booking.domain.booking.BookingFactory;
import io.mateu.article2.booking.domain.booking.BookingState;
import io.mateu.article2.booking.domain.booking.BookingRepository;
import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.booking.domain.ports.output.EventBus;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateBookingUseCase
{
    final BookingRepository bookingRepository;
    final EventBus eventBus;
    final BookingFactory bookingFactory;

    public CreateBookingUseCase(BookingRepository bookingRepository, EventBus eventBus, BookingFactory bookingFactory) {
        this.bookingRepository = bookingRepository;
        this.eventBus = eventBus;
        this.bookingFactory = bookingFactory;
    }

    public Mono<Void> createBooking(CreateBookingRequest request) {
        return bookingRepository.save(bookingFactory.create(
                new BookingId(request.bookingId()),
                new CustomerName(request.customerName()),
                new ServiceDescription(request.serviceDescription()),
                new BookingStartDate(request.serviceStartDate()),
                new BookingEndDate(request.serviceEndDate()),
                new BookingValue(request.value())
        ));
    }
}
