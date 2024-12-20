package io.mateu.article2.shared.events.payloads;

import io.mateu.article2.shared.events.Payload;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentCreated(
        String bookingId,
        String paymentId,
        LocalDate paymentDate,
        BigDecimal value
        ) implements Payload {
}
