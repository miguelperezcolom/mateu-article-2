package io.mateu.article2.booking.infra.primary.api.bookings;

import io.mateu.article2.booking.application.createbooking.CreateBookingRequest;
import io.mateu.article2.booking.application.createbooking.CreateBookingUseCase;
import io.mateu.article2.booking.application.findbooking.BookingInfo;
import io.mateu.article2.booking.application.findbooking.FindBookingRequest;
import io.mateu.article2.booking.application.findbooking.FindBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/booking/api")
@Slf4j
public class CreateBookingController {

    private final CreateBookingUseCase createBookingUseCase;

    public CreateBookingController(CreateBookingUseCase createBookingUseCase) {
        this.createBookingUseCase = createBookingUseCase;
    }


    @PostMapping("/bookings")
    public Mono<Void> findBooking(@RequestBody CreateBookingRequest request) {
        return createBookingUseCase.createBooking(request);
    }

}
