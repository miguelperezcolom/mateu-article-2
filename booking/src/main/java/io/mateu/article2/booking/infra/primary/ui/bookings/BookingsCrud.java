package io.mateu.article2.booking.infra.primary.ui.bookings;

import io.mateu.article2.booking.application.searchbookings.SearchBookingUseCase;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingStatus;
import io.mateu.uidl.data.Status;
import io.mateu.uidl.data.StatusType;
import io.mateu.uidl.interfaces.Crud;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

record BookingRow(String id, String customer, LocalDate startDate, LocalDate endDate, Status status) {
}

record BookingFilters() {
}


public class BookingsCrud implements Crud<BookingFilters, BookingRow> {

    final BookingCreationForm bookingCreationForm;
    final SearchBookingUseCase searchBookingUseCase;

    public BookingsCrud(BookingCreationForm bookingCreationForm, SearchBookingUseCase searchBookingUseCase) {
        this.bookingCreationForm = bookingCreationForm;
        this.searchBookingUseCase = searchBookingUseCase;
    }

    @Override
    public Mono<Page<BookingRow>> fetchRows(String searchText, BookingFilters filters, Pageable pageable) throws Throwable {
        return searchBookingUseCase.search(searchText, pageable)
                .map(p -> new PageImpl<>(p.getContent().stream()
                        .map(b -> new BookingRow(
                                b.id().id(),
                                b.customerName().name(),
                                b.startDate().date(),
                                b.endDate().date(),
                                new Status(getStatusType(b.status()), b.status().name())
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
        return bookingCreationForm;
    }
}
