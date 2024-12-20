package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.application.createbooking.CreateBookingRequest;
import io.mateu.article2.booking.application.createbooking.CreateBookingUseCase;
import io.mateu.uidl.annotations.Ignored;
import io.mateu.uidl.annotations.MainAction;
import io.mateu.uidl.annotations.RequestFocus;
import io.mateu.uidl.data.Destination;
import io.mateu.uidl.data.DestinationType;
import io.mateu.uidl.data.Result;
import io.mateu.uidl.data.ResultType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Scope("prototype")
public class BookingCreationForm {

    final CreateBookingUseCase createBookingUseCase;
    final ApplicationContext applicationContext;

    @Ignored
    String id = UUID.randomUUID().toString();

    @NotEmpty
    @RequestFocus
    String customerName;

    @NotNull
    LocalDate startDate;

    @NotNull
    LocalDate endDate;

    @NotEmpty
    String serviceDescription;

    @NotNull
    double value;

    public BookingCreationForm(CreateBookingUseCase createBookingUseCase, ApplicationContext applicationContext) {
        this.createBookingUseCase = createBookingUseCase;
        this.applicationContext = applicationContext;
    }

    @MainAction
    Mono<Result> createBooking() {
        return createBookingUseCase.createBooking(new CreateBookingRequest(
                id,
                customerName,
                serviceDescription,
                startDate,
                endDate,
                BigDecimal.valueOf(value)
        )).then(Mono.just(new Result(
                "Booking created",
                ResultType.Success,
                "Your booking has beeen successfully created",
                List.of(),
                new Destination("xbookings", DestinationType.View, "Back to bookings", "bookings"),
                null,
                (target, actionId, serverHttpRequest) -> applicationContext.getBean(BookingCrud.class)

        )));
    }

}
