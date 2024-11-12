package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.application.createbooking.CreateBookingRequest;
import io.mateu.article2.booking.application.createbooking.CreateBookingUseCase;
import io.mateu.uidl.annotations.MainAction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingCreationForm {

    final CreateBookingUseCase createBookingUseCase;


    String customerName;

    LocalDate startDate;

    LocalDate endDate;

    String serviceDescription;

    double value;

    public BookingCreationForm(CreateBookingUseCase createBookingUseCase) {
        this.createBookingUseCase = createBookingUseCase;
    }

    @MainAction
    Mono<Void> createBooking() {
        return createBookingUseCase.createBooking(new CreateBookingRequest(
                UUID.randomUUID().toString(),
                customerName,
                serviceDescription,
                startDate,
                endDate,
                BigDecimal.valueOf(value)
        ));
    }

}
