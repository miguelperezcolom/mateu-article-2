package io.mateu.article2;

import io.mateu.article2.model.Booking;
import io.mateu.article2.model.BookingListRow;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface Article2Client {

    Mono<Void> create(Booking booking);

    Mono<Void> update(Booking booking);

    Mono<Void> cancel(String bookingId, BigDecimal newValue);

    Mono<Booking> findById(String bookingId);

    Mono<Page<BookingListRow>> search(String text, Pageable pageable);

}
