package io.mateu.article2.financial.payment.application.create;

import io.mateu.article2.financial.payment.domain.payment.valueobjects.BookingId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentDate;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentValue;

public record CreatePaymentRequest(PaymentId id,
                                   BookingId bookingId,
                                   PaymentDate date,
                                   PaymentValue value) {
}
