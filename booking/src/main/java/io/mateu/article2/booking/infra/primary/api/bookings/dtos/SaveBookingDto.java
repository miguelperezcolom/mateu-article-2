package io.mateu.article2.booking.infra.primary.api.bookings.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public record SaveBookingDto(
        String leadName,
        String service,
        LocalDate serviceStartDate,
        LocalDate serviceEndDate,
        BigDecimal value
) {
}
