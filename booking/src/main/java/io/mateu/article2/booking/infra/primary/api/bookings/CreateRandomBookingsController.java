package io.mateu.article2.booking.infra.primary.api.bookings;

import io.mateu.article2.booking.application.cancelbooking.CancelBookingRequest;
import io.mateu.article2.booking.application.cancelbooking.CancelBookingUseCase;
import io.mateu.article2.booking.application.createrandombookings.CreateRandomBookingsRequest;
import io.mateu.article2.booking.application.createrandombookings.CreateRandomBookingsUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingValue;
import lombok.extern.slf4j.Slf4j;
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
public class CreateRandomBookingsController {

    private final CreateRandomBookingsUseCase createRandomBookingsUseCase;

    public CreateRandomBookingsController(CreateRandomBookingsUseCase createRandomBookingsUseCase) {
        this.createRandomBookingsUseCase = createRandomBookingsUseCase;
    }


    @PostMapping("/bookings/create-random")
    public Mono<String> cancelBooking(@RequestBody int quantity) {
        return createRandomBookingsUseCase.createBookings(new CreateRandomBookingsRequest(quantity));
    }
}
