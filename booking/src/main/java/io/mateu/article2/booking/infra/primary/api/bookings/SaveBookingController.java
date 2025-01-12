package io.mateu.article2.booking.infra.primary.api.bookings;

import io.mateu.article2.booking.application.savebooking.SaveBookingRequest;
import io.mateu.article2.booking.application.savebooking.SaveBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingEndDate;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingStartDate;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingValue;
import io.mateu.article2.booking.domain.booking.valueobjects.CustomerName;
import io.mateu.article2.booking.domain.booking.valueobjects.ServiceDescription;
import io.mateu.article2.booking.infra.primary.api.bookings.dtos.SaveBookingDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/booking/api")
@Slf4j
public class SaveBookingController {

    private final SaveBookingUseCase saveBookingUseCase;

    public SaveBookingController(SaveBookingUseCase saveBookingUseCase) {
        this.saveBookingUseCase = saveBookingUseCase;
    }


    @PutMapping("/bookings/{bookingId}")
    public Mono<Void> findBooking(@PathVariable String bookingId, @RequestBody SaveBookingDto data) {
        return saveBookingUseCase.updateBooking(new SaveBookingRequest(
                new BookingId(bookingId),
                new CustomerName(data.leadName()),
                new ServiceDescription(data.service()),
                new BookingStartDate(data.serviceStartDate()),
                new BookingEndDate(data.serviceEndDate()),
                new BookingValue(data.value())
        ));
    }

}
