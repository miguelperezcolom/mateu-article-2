package io.mateu.article2.booking.infra.primary.api.bookings;

import io.mateu.article2.booking.application.searchbookings.SearchBookingUseCase;
import io.mateu.article2.booking.domain.ports.output.ListedBooking;
import io.mateu.article2.booking.infra.primary.api.bookings.dtos.Direction;
import io.mateu.article2.booking.infra.primary.api.bookings.dtos.SearchBookingsRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/booking/api")
@Slf4j
public class SearchBookingsController {

    private final SearchBookingUseCase searchBookingUseCase;

    public SearchBookingsController(SearchBookingUseCase searchBookingUseCase) {
        this.searchBookingUseCase = searchBookingUseCase;
    }


    @PostMapping("/bookings/search")
    public Mono<Page<ListedBooking>> findBooking(@RequestBody SearchBookingsRequestDto data) {
        return searchBookingUseCase.search(data.text(),
                PageRequest.of(
                        data.pageable().page(),
                        data.pageable().size(),
                        map(data.pageable().sort())
                ));
    }

    private Sort map(io.mateu.article2.booking.infra.primary.api.bookings.dtos.Sort sort) {
        if (sort == null) {
            return Sort.unsorted();
        }
        if (Direction.descending.equals(sort.direction())) {
            return Sort.by(Sort.Order.desc(sort.field()));
        } else {
            return Sort.by(Sort.Order.asc(sort.field()));
        }
    }

}
