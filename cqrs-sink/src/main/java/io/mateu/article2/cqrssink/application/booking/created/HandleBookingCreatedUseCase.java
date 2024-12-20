package io.mateu.article2.cqrssink.application.booking.created;

import io.mateu.article2.cqrssink.infra.secondary.jooq.model.default_schema.tables.Booking;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HandleBookingCreatedUseCase {

    private final DSLContext context;

    public HandleBookingCreatedUseCase(DSLContext context) {
        this.context = context;
    }

    public void handle(BookingCreated event) {
        var record = context.newRecord(Booking.BOOKING);
        record.setId(event.bookingId());
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
