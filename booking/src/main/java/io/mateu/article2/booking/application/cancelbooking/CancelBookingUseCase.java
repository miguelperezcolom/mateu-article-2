package io.mateu.article2.booking.application.cancelbooking;

import io.mateu.article2.booking.domain.booking.BookingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CancelBookingUseCase
{
    final BookingRepository bookingRepository;

    public CancelBookingUseCase(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Mono<Void> cancelBooking(CancelBookingRequest request) {
        return bookingRepository.findById(request.bookingId())
                .flatMap(booking -> booking.cancel(request.newValue()))
                .flatMap(bookingRepository::save);
    }
}
