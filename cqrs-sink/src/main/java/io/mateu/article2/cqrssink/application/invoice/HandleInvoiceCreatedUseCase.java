package io.mateu.article2.cqrssink.application.invoice;

import io.mateu.article2.cqrssink.infra.secondary.jooq.model.default_schema.tables.Booking;
import io.mateu.article2.shared.events.payloads.BookingUpdated;
import io.mateu.article2.shared.events.payloads.InvoiceCreated;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
public class HandleInvoiceCreatedUseCase {

    private final DSLContext context;

    public HandleInvoiceCreatedUseCase(DSLContext context) {
        this.context = context;
    }

    public void handle(InvoiceCreated event) {
        var record = context.selectFrom(Booking.BOOKING).where(Booking.BOOKING.ID.eq(event.bookingId())).fetchOne();
        if (record == null) {
            record = context.newRecord(Booking.BOOKING);
            record.setId(event.bookingId());
        }
        record.setInvoiced(event.value().add(record.getInvoiced()));
        record.store();
    }

}
