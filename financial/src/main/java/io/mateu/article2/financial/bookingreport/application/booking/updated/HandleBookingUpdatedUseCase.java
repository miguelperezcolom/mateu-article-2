package io.mateu.article2.financial.bookingreport.application.booking.updated;

import io.mateu.article2.financial.jooq.model.default_schema.tables.Booking;
import io.mateu.article2.shared.events.payloads.BookingUpdated;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class HandleBookingUpdatedUseCase {

    private final DSLContext context;

    public HandleBookingUpdatedUseCase(DSLContext context) {
        this.context = context;
    }

    @SneakyThrows
    public void handle(BookingUpdated event) {
        Flux.from(context
                .update(Booking.BOOKING)
                .set(Booking.BOOKING._VALUE, event.value())
                .where(Booking.BOOKING.ID.eq(event.bookingId()))
        ).then().toFuture().get();
    }

}
