package io.mateu.article2.booking.application.savebooking;

import io.mateu.article2.booking.domain.booking.BookingFactory;
import io.mateu.article2.booking.domain.booking.BookingRepository;
import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.booking.domain.ports.output.EventBus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SaveBookingUseCase
{
    final BookingRepository bookingRepository;

    public SaveBookingUseCase(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Mono<Void> updateBooking(SaveBookingRequest request) {
        return bookingRepository.findById(request.bookingId())
                .flatMap(booking -> booking.update(
                        request.customerName(),
                        request.serviceDescription(),
                        request.serviceStartDate(),
                        request.serviceEndDate(),
                        request.value()))
                .flatMap(bookingRepository::save);
    }
}
