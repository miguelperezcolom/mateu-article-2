package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.application.createrandombookings.CreateRandomBookingsRequest;
import io.mateu.article2.booking.application.createrandombookings.CreateRandomBookingsUseCase;
import io.mateu.article2.booking.application.searchbookings.SearchBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingStatus;
import io.mateu.uidl.annotations.*;
import io.mateu.uidl.data.*;
import io.mateu.uidl.data.Status;
import io.mateu.uidl.interfaces.ConsumesHash;
import io.mateu.uidl.interfaces.Crud;
import io.mateu.uidl.interfaces.UpdatesHash;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.UUID;

record BookingRow(
        String id,
        @Sortable(serverSide = true)
        String customer,
        @Width("140px")
        @Sortable(serverSide = true)
        String startDate,
        @Width("140px")
        String endDate,
        String service,
        @Width("100px")
        Status status,
        @Money
        BigDecimal value,
        @Money
        BigDecimal invoiced,
        @Money
        BigDecimal paid) {
}

record BookingFilters() {
}


@Service
@Scope("prototype")
@Title("Bookings")
public class BookingCrud implements Crud<BookingFilters, BookingRow>, ConsumesHash, UpdatesHash {

    final BookingView bookingView;
    final BookingCreationForm bookingCreationForm;
    final SearchBookingUseCase searchBookingUseCase;
    final CreateRandomBookingsUseCase createRandomBookingsUseCase;

    public BookingCrud(BookingView bookingView, BookingCreationForm bookingCreationForm, SearchBookingUseCase searchBookingUseCase, CreateRandomBookingsUseCase createRandomBookingsUseCase) {
        this.bookingView = bookingView;
        this.bookingCreationForm = bookingCreationForm;
        this.searchBookingUseCase = searchBookingUseCase;
        this.createRandomBookingsUseCase = createRandomBookingsUseCase;
    }

    @Override
    public Mono<Page<BookingRow>> fetchRows(String searchText, BookingFilters filters, Pageable pageable) throws Throwable {
        return searchBookingUseCase.search(searchText, pageable)
                .map(p -> new PageImpl<>(p.getContent().stream()
                        .map(b -> new BookingRow(
                                b.id(),
                                b.customer(),
                                b.startDate(),
                                b.endDate(),
                                b.serviceDescription(),
                                new Status(getStatusType(BookingStatus.valueOf(b.status())), b.status()),
                                b.value(),
                                b.invoiced(),
                                b.paid()
                        )).toList()
                        , p.getPageable(), p.getTotalElements()));
    }

    private StatusType getStatusType(BookingStatus status) {
        return switch (status) {
            case Cancelled -> StatusType.DANGER;
            case Confirmed -> StatusType.SUCCESS;
        };
    }

    @Override
    public Object getNewRecordForm() throws Throwable {
        bookingCreationForm.id = UUID.randomUUID().toString();
        return bookingCreationForm;
    }

    @Override
    public Mono<BookingView> getDetail(BookingRow bookingRow) throws Throwable {
        return bookingView.load(bookingRow.id());
    }

    @Override
    public String getCaptionForNew() {
        return "Create booking";
    }

    @Action(target = ActionTarget.NewModal)
    public Mono<CloseModal> createRandomBookings(@Min(1)@Max(500)@Help("Max 500") int quantity) {
        return createRandomBookingsUseCase.createBookings(new CreateRandomBookingsRequest(quantity))
                .flatMap(s -> Mono.just(new CloseModal(this, ActionTarget.View))
                        .delayElement(Duration.ofSeconds(3)));
    }

    @Override
    public Object consume(String urlFragment, ServerHttpRequest serverHttpRequest) {
        if ("".equals(urlFragment)) {
            return this;
        }
        if ("new".equals(urlFragment)) {
            return bookingCreationForm;
        }
        return bookingView.load(urlFragment);
    }

    @Override
    public String getHash() {
        return "";
    }
}
