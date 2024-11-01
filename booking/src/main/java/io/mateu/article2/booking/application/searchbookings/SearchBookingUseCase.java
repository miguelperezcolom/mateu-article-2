package io.mateu.article2.booking.application.searchbookings;

import io.mateu.article2.booking.domain.booking.Booking;
import io.mateu.article2.booking.domain.booking.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SearchBookingUseCase {

    final BookingRepository bookingRepository;

    public SearchBookingUseCase(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public Mono<Page<Booking>> search(String text, Pageable pageable) {
        return bookingRepository.search(text, pageable);
    }

}
