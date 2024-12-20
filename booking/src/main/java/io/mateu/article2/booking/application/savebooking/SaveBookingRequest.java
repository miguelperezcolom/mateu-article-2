package io.mateu.article2.booking.application.savebooking;

import io.mateu.article2.booking.domain.booking.valueobjects.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaveBookingRequest(
        BookingId bookingId,
        CustomerName customerName,
        ServiceDescription serviceDescription,
        BookingStartDate serviceStartDate,
        BookingEndDate serviceEndDate,
        BookingValue value
) {
}
