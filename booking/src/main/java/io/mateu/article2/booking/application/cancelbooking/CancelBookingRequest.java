package io.mateu.article2.booking.application.cancelbooking;

import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;
import io.mateu.article2.booking.domain.booking.valueobjects.BookingValue;
import io.mateu.article2.booking.infra.primary.ui.bookings.BookingView;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CancelBookingRequest(
        BookingId bookingId,
        BookingValue newValue
) {
}
