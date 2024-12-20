package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.application.cancelbooking.CancelBookingRequest;
import io.mateu.article2.booking.application.cancelbooking.CancelBookingUseCase;
import io.mateu.article2.booking.application.findbooking.FindBookingRequest;
import io.mateu.article2.booking.application.findbooking.FindBookingUseCase;
import io.mateu.article2.booking.application.savebooking.SaveBookingRequest;
import io.mateu.article2.booking.application.savebooking.SaveBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.uidl.annotations.*;
import io.mateu.uidl.data.*;
import io.mateu.uidl.data.Status;
import io.mateu.uidl.interfaces.ActionHandler;
import io.mateu.uidl.interfaces.HasStatus;
import io.mateu.uidl.interfaces.View;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Title("")
@Service
@Scope("prototype")
class BookingInfoSection implements HasStatus, ActionHandler {

    private final ApplicationContext applicationContext;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final SaveBookingUseCase saveBookingUseCase;


    @Ignored String id;
    @Ignored Status status;
    @NotEmpty
            @RequestFocus
    String leadName;
    @NotEmpty
    String service;
    @NotNull
    LocalDate serviceStartDate;
    @NotNull
    LocalDate serviceEndDate;
    @NotNull
    BigDecimal value;

    BookingInfoSection(ApplicationContext applicationContext, CancelBookingUseCase cancelBookingUseCase, SaveBookingUseCase saveBookingUseCase) {
        this.applicationContext = applicationContext;
        this.cancelBookingUseCase = cancelBookingUseCase;
        this.saveBookingUseCase = saveBookingUseCase;
    }

    @MainAction(order = 0, target = ActionTarget.View, type = ActionType.Tertiary, position = ActionPosition.Left)
    BookingCrud back() {
        return applicationContext.getBean(BookingCrud.class);
    }

    @MainAction(order = 1, target = ActionTarget.View, type = ActionType.Secondary, variants = {ActionThemeVariant.Error})
    Mono<Result> cancelBooking(BigDecimal cancellationCost) {
        return cancelBookingUseCase.cancelBooking(new CancelBookingRequest(new BookingId(id), new BookingValue(cancellationCost)))
                .then(Mono.just(new Result(
                        "Booking cancelled",
                        ResultType.Success,
                        "Booking with id " + id + " has been successfully cancelled.",
                        List.of(new Destination("back-to-list", DestinationType.View, "Back to booking list", id)),
                        new Destination(id, DestinationType.View, "Back to booking", id),
                        null,
                        this
                )));
    }

    @MainAction(target = ActionTarget.View)
    Mono<Result> save() {
        return saveBookingUseCase.updateBooking(new SaveBookingRequest(
                new BookingId(id),
                new CustomerName(leadName),
                new ServiceDescription(service),
                new BookingStartDate(serviceStartDate),
                new BookingEndDate(serviceEndDate),
                new BookingValue(value)
        )).then(Mono.just(new Result(
                "Booking saved",
                ResultType.Success,
                "Booking with id " + id + " has been successfully saved.",
                List.of(new Destination("back-to-list", DestinationType.View, "Back to booking list", id)),
                new Destination(id, DestinationType.View, "Back to booking", id),
                null,
                this
        )));
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public Object handle(Object target, String actionId, ServerHttpRequest serverHttpRequest) {
        if ("back-to-list".equals(actionId)) {
            return applicationContext.getBean(BookingCrud.class);
        } else {
            return applicationContext.getBean(BookingView.class).load(actionId);
        }
    }
}

@Title("")
record MyHorizontalLayout(
        BookingInfoSection bookingInfoSection,
        @Width("500px")
        RemoteJourney financialReport) {

}


@Service
@Scope("prototype")
public class BookingView implements View {

    private final FindBookingUseCase findBookingUseCase;
    private final BookingInfoSection bookingInfoSection;
    @Ignored
    String id;

    @Slot(SlotName.main)
            @HorizontalLayout
    MyHorizontalLayout main;

    public BookingView(FindBookingUseCase findBookingUseCase, BookingInfoSection bookingInfoSection) {
        this.findBookingUseCase = findBookingUseCase;
        this.bookingInfoSection = bookingInfoSection;
    }

    public Mono<BookingView> load(String id) {
        this.id = id;

        var financialSection = new RemoteJourney(
                "/financial/mateu/v3",
                "io.mateu.article2.financial.shared.infra.primary.ui.FinancialUI",
                "financial_bookingReport",
                "{\"bookingId\":\"" + id + "\"}");

        main = new MyHorizontalLayout(bookingInfoSection, financialSection);

        return findBookingUseCase.handle(new FindBookingRequest(new BookingId(id)))
                .map(i -> {

                    bookingInfoSection.status = mapStatus(i.status());
                    bookingInfoSection.id = i.id();
                    bookingInfoSection.leadName = i.leadName();
                    bookingInfoSection.service = i.service();
                    bookingInfoSection.serviceStartDate = i.serviceStartDate();
                    bookingInfoSection.serviceEndDate = i.serviceEndDate();
                    bookingInfoSection.value = i.value();

                    return i;
                }).then(Mono.just(this));
    }

    private Status mapStatus(BookingStatus status) {
        if (status == null) {
            return new Status(StatusType.NONE, "null");
        }
        return new Status(status.name().equalsIgnoreCase("cancelled")? StatusType.DANGER: StatusType.SUCCESS, status.name());
    }

    @Override
    public String toString() {
        return "Booking " + id;
    }
}
