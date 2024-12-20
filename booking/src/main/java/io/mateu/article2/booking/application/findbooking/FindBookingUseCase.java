package io.mateu.article2.booking.application.findbooking;

import io.mateu.article2.booking.domain.booking.BookingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class FindBookingUseCase {

    final BookingRepository bookingRepository;

    public FindBookingUseCase(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Mono<BookingInfo> handle(FindBookingRequest request) {
        return bookingRepository.findById(request.bookingId())
                .map(b -> b.getState())
                .map(s -> new BookingInfo(
                        s.id().id(),
                        s.customerName().name(),
                        s.serviceDescription().description(),
                        s.startDate().date(),
                        s.endDate().date(),
                        s.value().value(),
                        s.status()
                ));
    }
}
