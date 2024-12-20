package io.mateu.article2.cqrssink.application.booking.updated;

import io.mateu.article2.cqrssink.infra.secondary.jooq.model.default_schema.tables.Booking;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import io.mateu.article2.shared.events.payloads.BookingUpdated;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;

@Service
public class HandleBookingUpdatedUseCase {

    private final DSLContext context;

    public HandleBookingUpdatedUseCase(DSLContext context) {
        this.context = context;
    }

    public void handle(BookingUpdated event) {
        var record = context.selectFrom(Booking.BOOKING).where(Booking.BOOKING.ID.eq(event.bookingId())).fetchOne();
        if (record == null) {
            record = context.newRecord(Booking.BOOKING);
            record.setId(event.bookingId());
        }
        record.setCustomer(event.leadName());
        record.setService(event.service());
        record.setStartDate(event.serviceStartDate().toString());
        record.setEndDate(event.serviceEndDate().toString());
        record.setStatus(event.status());
        record.setValue(event.value());
        record.setSearchableText((event.leadName() +
                " " + event.service() +
                " " + event.serviceStartDate() +
                " " + event.serviceEndDate()).toLowerCase());
        record.store();
    }

}
