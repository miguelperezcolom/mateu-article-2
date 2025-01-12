package io.mateu.article2.booking.application.createbooking;

import io.mateu.article2.booking.domain.booking.BookingFactory;
import io.mateu.article2.booking.domain.booking.BookingRepository;
import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.booking.domain.ports.output.EventBus;
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
                new BookingId(request.id()),
                new CustomerName(request.leadName()),
                new ServiceDescription(request.service()),
                new BookingStartDate(request.serviceStartDate()),
                new BookingEndDate(request.serviceEndDate()),
                new BookingValue(request.value())
        ));
    }
}
