package io.mateu.article2.financial.bookingreport.application.booking.created;

import io.mateu.article2.financial.jooq.model.default_schema.tables.Booking;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import lombok.SneakyThrows;
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

    @SneakyThrows
    public void handle(BookingCreated event) {
        Flux.from(context
                .insertInto(Booking.BOOKING)
                .columns(Booking.BOOKING.ID, Booking.BOOKING._VALUE)
                .values(event.bookingId(), event.value())).then().toFuture().get();
    }

}
