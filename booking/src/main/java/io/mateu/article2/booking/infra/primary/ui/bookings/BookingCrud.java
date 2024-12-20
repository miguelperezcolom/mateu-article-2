package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.application.searchbookings.SearchBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingStatus;
import io.mateu.uidl.annotations.Money;
import io.mateu.uidl.annotations.Title;
import io.mateu.uidl.annotations.Width;
import io.mateu.uidl.data.Status;
import io.mateu.uidl.data.StatusType;
import io.mateu.uidl.interfaces.Crud;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.UUID;

record BookingRow(
        String id,
        String customer,
        @Width("140px")
        String startDate,
        @Width("140px")
        String endDate,
        String service,
        @Width("100px")
        Status status,
        @Money
        BigDecimal value) {
}

record BookingFilters() {
}


@Service
@Scope("prototype")
@Title("Bookings")
public class BookingCrud implements Crud<BookingFilters, BookingRow> {

    final BookingView bookingView;
    final BookingCreationForm bookingCreationForm;
    final SearchBookingUseCase searchBookingUseCase;

    public BookingCrud(BookingView bookingView, BookingCreationForm bookingCreationForm, SearchBookingUseCase searchBookingUseCase) {
        this.bookingView = bookingView;
        this.bookingCreationForm = bookingCreationForm;
        this.searchBookingUseCase = searchBookingUseCase;
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
                                b.value()
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
    public Object getDetail(BookingRow bookingRow) throws Throwable {
        bookingView.load(bookingRow.id()).toFuture().get();
        return bookingView;
    }

    @Override
    public String getCaptionForNew() {
        return "Create booking";
    }
}
