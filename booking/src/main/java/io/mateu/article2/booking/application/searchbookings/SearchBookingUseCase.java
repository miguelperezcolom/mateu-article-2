package io.mateu.article2.booking.application.searchbookings;

import io.mateu.article2.booking.domain.booking.Booking;
import io.mateu.article2.booking.domain.booking.BookingState;
import io.mateu.article2.booking.domain.booking.BookingRepository;
import io.mateu.article2.booking.domain.ports.output.CQRSReader;
import io.mateu.article2.booking.domain.ports.output.ListedBooking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SearchBookingUseCase {

    final CQRSReader cqrsReader;

    public SearchBookingUseCase(CQRSReader cqrsReader) {
        this.cqrsReader = cqrsReader;
    }


    public Mono<Page<ListedBooking>> search(String text, Pageable pageable) {
        return cqrsReader.search(text, pageable);
    }

}
