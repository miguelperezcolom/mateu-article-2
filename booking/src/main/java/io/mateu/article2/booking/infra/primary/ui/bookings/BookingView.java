package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.application.cancelbooking.CancelBookingRequest;
import io.mateu.article2.booking.application.cancelbooking.CancelBookingUseCase;
import io.mateu.article2.booking.application.findbooking.FindBookingRequest;
import io.mateu.article2.booking.application.findbooking.FindBookingUseCase;
import io.mateu.article2.booking.application.savebooking.SaveBookingRequest;
import io.mateu.article2.booking.application.savebooking.SaveBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingEndDate;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingStartDate;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingStatus;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingValue;
import io.mateu.article2.booking.domain.booking.valueobjects.CustomerName;
import io.mateu.article2.booking.domain.booking.valueobjects.ServiceDescription;
import io.mateu.uidl.annotations.ActionPosition;
import io.mateu.uidl.annotations.ActionTarget;
import io.mateu.uidl.annotations.ActionThemeVariant;
import io.mateu.uidl.annotations.ActionType;
import io.mateu.uidl.annotations.HorizontalLayouted;
import io.mateu.uidl.annotations.Ignored;
import io.mateu.uidl.annotations.MainAction;
import io.mateu.uidl.annotations.RequestFocus;
import io.mateu.uidl.annotations.Title;
import io.mateu.uidl.annotations.Width;
import io.mateu.uidl.data.Destination;
import io.mateu.uidl.data.DestinationType;
import io.mateu.uidl.data.Result;
import io.mateu.uidl.data.ResultType;
import io.mateu.uidl.data.Status;
import io.mateu.uidl.data.StatusType;
import io.mateu.uidl.interfaces.ActionHandler;
import io.mateu.uidl.interfaces.Container;
import io.mateu.uidl.interfaces.HasStatus;
import io.mateu.uidl.interfaces.MicroFrontend;
import io.mateu.uidl.interfaces.UpdatesHash;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Scope("prototype")
class BookingInfoSection implements HasStatus, ActionHandler {

    private final ApplicationContext applicationContext;
    private final CancelBookingUseCase cancelBookingUseCase;
    private final SaveBookingUseCase saveBookingUseCase;


    @Ignored
    String id;
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

    @Override
    public String toString() {
        return "Booking " + id;
    }
}

@Service
@Scope("prototype")
public class BookingView implements Container, UpdatesHash {

    @Ignored
    private final FindBookingUseCase findBookingUseCase;
    @Ignored
    private final BookingInfoSection bookingInfoSection;
    @Ignored
    String id;

    @HorizontalLayouted
    List content = null;

    public BookingView(FindBookingUseCase findBookingUseCase, BookingInfoSection bookingInfoSection) {
        this.findBookingUseCase = findBookingUseCase;
        this.bookingInfoSection = bookingInfoSection;
    }

    public Mono<BookingView> load(String id) {
        this.id = id;

        return findBookingUseCase.handle(new FindBookingRequest(new BookingId(id)))
                .map(i -> {

                    bookingInfoSection.status = mapStatus(i.status());
                    bookingInfoSection.id = i.id();
                    bookingInfoSection.leadName = i.leadName();
                    bookingInfoSection.service = i.service();
                    bookingInfoSection.serviceStartDate = i.serviceStartDate();
                    bookingInfoSection.serviceEndDate = i.serviceEndDate();
                    bookingInfoSection.value = i.value();

                    content = List.of(
                            bookingInfoSection,
                            new MicroFrontend(
                                    "/financial/bookingreport",
                                    Map.of("id", id))
                            );

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

    @Override
    public String getHash() {
        return id;
    }
}
