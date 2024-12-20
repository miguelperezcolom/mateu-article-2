package io.mateu.article2.shared.events.payloads;

import io.mateu.article2.shared.events.Payload;

import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceCreated(
        String bookingId,
        String invoiceId,
        LocalDate invoiceDate,
        BigDecimal value
        ) implements Payload {
}
