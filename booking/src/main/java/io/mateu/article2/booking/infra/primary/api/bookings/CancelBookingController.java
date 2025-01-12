package io.mateu.article2.booking.infra.primary.api.bookings;

import io.mateu.article2.booking.application.cancelbooking.CancelBookingRequest;
import io.mateu.article2.booking.application.cancelbooking.CancelBookingUseCase;
import io.mateu.article2.booking.application.findbooking.BookingInfo;
import io.mateu.article2.booking.application.findbooking.FindBookingRequest;
import io.mateu.article2.booking.application.findbooking.FindBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/booking/api")
@Slf4j
public class CancelBookingController {

    private final CancelBookingUseCase cancelBookingUseCase;

    public CancelBookingController(CancelBookingUseCase cancelBookingUseCase) {
        this.cancelBookingUseCase = cancelBookingUseCase;
    }


    @PostMapping("/bookings/{bookingId}/cancel")
    public Mono<Void> cancelBooking(@PathVariable String bookingId, @RequestBody BigDecimal newValue) {
        return cancelBookingUseCase.cancelBooking(new CancelBookingRequest(new BookingId(bookingId), new BookingValue(newValue)));
    }

}
