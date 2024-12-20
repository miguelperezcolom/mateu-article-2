package io.mateu.article2.booking.domain.ports.output;

import io.mateu.uidl.data.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ListedBooking(
        String id,
        String customer,
        String serviceDescription,
        String startDate,
        String endDate,
        String status,
        BigDecimal value,
        BigDecimal invoiced,
        BigDecimal paid
) {
}
