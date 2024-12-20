package io.mateu.article2.financial.invoice.domain.invoice;

import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.BookingId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceDate;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceValue;
import io.mateu.article2.shared.events.DomainEvent;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import io.mateu.article2.shared.events.payloads.InvoiceCreated;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class InvoiceFactory {

    private final ApplicationContext applicationContext;

    public InvoiceFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Invoice create(InvoiceId id, BookingId bookingId, InvoiceDate invoiceDate, InvoiceValue value) {
        return create(new InvoiceState(
                id,
                bookingId,
                invoiceDate,
                value
        ));
    }

    public Invoice create(InvoiceState invoiceState) {
        var invoice = applicationContext.getBean(Invoice.class);
        invoice.setState(invoiceState);
        invoice.addEvent(new DomainEvent(
                UUID.randomUUID().toString(),
                "invoice-" + invoiceState.id().id(),
                LocalDateTime.now(),
                new InvoiceCreated(
                        invoiceState.bookingId().id(),
                        invoiceState.id().id(),
                        invoiceState.date().date(),
                        invoiceState.value().value())
        ));
        return invoice;
    }

    public Invoice ofState(InvoiceState invoiceState) {
        var invoice = applicationContext.getBean(Invoice.class);
        invoice.setState(invoiceState);
        return invoice;
    }
}
