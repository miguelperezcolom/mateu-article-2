package io.mateu.article2.booking;

import io.mateu.article2.booking.domain.booking.Booking;
import io.mateu.article2.booking.domain.booking.BookingFactory;
import io.mateu.article2.booking.domain.booking.BookingState;
import io.mateu.article2.booking.domain.booking.valueobjects.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class BookingMother {

    private final BookingFactory bookingFactory;

    public BookingMother(BookingFactory bookingFactory) {
        this.bookingFactory = bookingFactory;
    }

    public Booking createBooking() {
        return bookingFactory.ofState(new BookingState(
                new BookingId(UUID.randomUUID().toString()),
                new CustomerName("Mateu"),
                new ServiceDescription("1 double room at Hotel Formentor"),
                new BookingStartDate(LocalDate.of(2024, 6, 23)),
                new BookingEndDate(LocalDate.of(2024, 6, 23)),
                new BookingValue(BigDecimal.valueOf(300.14)),
                BookingStatus.Confirmed
                ));
    }

}
