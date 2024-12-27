package io.mateu.article2.cqrssink.application.payment;

import io.mateu.article2.cqrssink.infra.secondary.jooq.model.default_schema.tables.Booking;
import io.mateu.article2.shared.events.payloads.InvoiceCreated;
import io.mateu.article2.shared.events.payloads.PaymentCreated;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
public class HandlePaymentCreatedUseCase {

    private final DSLContext context;

    public HandlePaymentCreatedUseCase(DSLContext context) {
        this.context = context;
    }

    public void handle(PaymentCreated event) {
        var record = context.selectFrom(Booking.BOOKING).where(Booking.BOOKING.ID.eq(event.bookingId())).fetchOne();
        if (record == null) {
            record = context.newRecord(Booking.BOOKING);
            record.setId(event.bookingId());
        }
        record.setPaid(record.getPaid() == null?event.value():event.value().add(record.getPaid()));
        record.store();
    }

}
