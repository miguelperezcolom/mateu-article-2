package io.mateu.article2.financial.invoice.application.create;

import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.BookingId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceDate;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceValue;

public record CreateInvoiceRequest(InvoiceId id,
                                   BookingId bookingId,
                                   InvoiceDate date,
                                   InvoiceValue value) {
}
