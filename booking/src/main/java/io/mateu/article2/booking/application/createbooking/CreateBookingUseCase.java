package io.mateu.article2.booking.application.createbooking;

import io.mateu.article2.booking.domain.booking.Booking;
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

    public CreateBookingUseCase(BookingRepository bookingRepository, EventBus eventBus) {
        this.bookingRepository = bookingRepository;
        this.eventBus = eventBus;
    }

    public Mono<Void> createBooking(CreateBookingRequest request) {
        return bookingRepository.save(new Booking(
                new BookingId(request.bookingId()),
                new CustomerName(request.customerName()),
                new ServiceDescription(request.serviceDescription()),
                new BookingStartDate(request.serviceStartDate()),
                new BookingEndDate(request.serviceEndDate()),
                new BookingValue(request.value()),
                BookingStatus.Confirmed
        )).then(Mono.fromRunnable(() ->
                eventBus.send(new BookingCreated(request.bookingId()))));
    }
}
