package io.mateu.article2.booking.application.createbooking;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateBookingRequest(
        String id,
        String leadName,
        String service,
        LocalDate serviceStartDate,
        LocalDate serviceEndDate,
        BigDecimal value
) {
}
