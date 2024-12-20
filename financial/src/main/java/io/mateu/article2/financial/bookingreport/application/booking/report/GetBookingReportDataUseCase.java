package io.mateu.article2.financial.bookingreport.application.booking.report;

import io.mateu.article2.financial.jooq.model.default_schema.tables.Booking;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Invoice;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Payment;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.List;

import static org.jooq.impl.DSL.sum;

@Service
public class GetBookingReportDataUseCase {

    private final DSLContext context;

    public GetBookingReportDataUseCase(DSLContext context) {
        this.context = context;
    }

    @SneakyThrows
    public Mono<BookingReportData> handle(GetBookingReportDataRequest request) {
        return Flux.from(context.select(Booking.BOOKING._VALUE)
                .from(Booking.BOOKING).where(Booking.BOOKING.ID.eq(request.bookingId())))
                .collectList().map(this::getValue)
                .zipWith(Flux.from(context.select(sum(Invoice.INVOICE._VALUE))
                                .from(Invoice.INVOICE).where(Invoice.INVOICE.BOOKING_ID.eq(request.bookingId())))
                        .collectList().map(this::getValue))
                .zipWith(Flux.from(context.select(sum(Payment.PAYMENT._VALUE))
                                .from(Payment.PAYMENT).where(Payment.PAYMENT.BOOKING_ID.eq(request.bookingId())))
                        .collectList().map(this::getValue)).map(t -> new BookingReportData(
                                t.getT1().getT1().doubleValue(),
                        t.getT1().getT2().doubleValue(),
                        t.getT2().doubleValue()
                ));
    }

    private BigDecimal getValue(List<Record1<BigDecimal>> record) {
        BigDecimal value = BigDecimal.ZERO;
        if (record != null) {
            if (record.size() == 1) {
                var first = record.get(0);
                if (first.value1() != null) {
                    value = first.value1();
                }
            }
        }
        return value;
    }

}
