package io.mateu.article2.booking.infra.primary.api.bookings;

import io.mateu.article2.booking.application.findbooking.BookingInfo;
import io.mateu.article2.booking.application.findbooking.FindBookingRequest;
import io.mateu.article2.booking.application.findbooking.FindBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/booking/api")
@Slf4j
public class FindBookingController {

    private final FindBookingUseCase findBookingUseCase;


    public FindBookingController(FindBookingUseCase findBookingUseCase) {
        this.findBookingUseCase = findBookingUseCase;
    }

    @GetMapping("/bookings/{bookingId}")
    public Mono<BookingInfo> findBooking(@PathVariable String bookingId) {
        return findBookingUseCase.handle(new FindBookingRequest(new BookingId(bookingId)));
    }

}
