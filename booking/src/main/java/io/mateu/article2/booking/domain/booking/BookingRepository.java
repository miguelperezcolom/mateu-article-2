package io.mateu.article2.booking.domain.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface BookingRepository {

    Mono<Void> save(Booking booking);

    Mono<Page<Booking>> search(String text, Pageable pageable);
}
