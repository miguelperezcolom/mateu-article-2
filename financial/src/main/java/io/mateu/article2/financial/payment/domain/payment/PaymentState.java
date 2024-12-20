package io.mateu.article2.financial.payment.domain.payment;

import io.mateu.article2.financial.payment.domain.payment.valueobjects.BookingId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentDate;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentValue;

public record PaymentState(
        PaymentId id,
        BookingId bookingId,
        PaymentDate date,
        PaymentValue value
) {
}
