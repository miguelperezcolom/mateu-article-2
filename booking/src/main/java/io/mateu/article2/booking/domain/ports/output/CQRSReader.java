package io.mateu.article2.booking.domain.ports.output;

import io.mateu.article2.booking.domain.booking.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

public interface CQRSReader {

    Mono<Page<ListedBooking>> search(String text, Pageable pageable);

}
