package io.mateu.article2.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingData(
        String leadName,
        String service,
        LocalDate serviceStartDate,
        LocalDate serviceEndDate,
        BigDecimal value
) {
}
