package io.mateu.article2.booking.domain.booking;

import io.mateu.article2.booking.domain.booking.valueobjects.*;

public record Booking(
        BookingId id,
        CustomerName customerName,
        ServiceDescription serviceDescription,
        BookingStartDate startDate,
        BookingEndDate endDate,
        BookingValue value,
        BookingStatus status
) {
}
