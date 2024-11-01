package io.mateu.article2.booking.application.createbooking;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateBookingRequest(
        String bookingId,
        String customerName,
        String serviceDescription,
        LocalDate serviceStartDate,
        LocalDate serviceEndDate,
        BigDecimal value
) {
}
