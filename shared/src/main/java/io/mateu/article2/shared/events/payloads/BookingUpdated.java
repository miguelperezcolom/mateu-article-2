package io.mateu.article2.shared.events.payloads;

import io.mateu.article2.shared.events.Payload;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingUpdated(
        String bookingId,
        String leadName,
        String service,
        LocalDate serviceStartDate,
        LocalDate serviceEndDate,
        BigDecimal value,
        String status
        ) implements Payload {
}
