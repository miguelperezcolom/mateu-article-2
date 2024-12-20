package io.mateu.article2.financial.invoice.domain.invoice;

import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.BookingId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceDate;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceValue;

public record InvoiceState(
        InvoiceId id,
        BookingId bookingId,
        InvoiceDate date,
        InvoiceValue value
) {
}
