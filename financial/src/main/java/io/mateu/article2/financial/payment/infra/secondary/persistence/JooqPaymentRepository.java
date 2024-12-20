package io.mateu.article2.financial.payment.infra.secondary.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Event;
import io.mateu.article2.financial.payment.domain.payment.PaymentFactory;
import io.mateu.article2.financial.payment.domain.payment.PaymentRepository;
import io.mateu.article2.financial.payment.domain.payment.PaymentState;
import io.mateu.article2.financial.payment.domain.payment.Payment;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.BookingId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentDate;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentValue;
import io.mateu.article2.shared.events.EventProcessingStatus;
import io.mateu.article2.shared.events.Payload;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.mateu.article2.financial.jooq.model.default_schema.tables.Payment.PAYMENT;

@Service
public class JooqPaymentRepository implements PaymentRepository {

    private final PaymentFactory paymentFactory;
    private final DSLContext context;
    private final ObjectMapper objectMapper;

    public JooqPaymentRepository(PaymentFactory paymentFactory, DSLContext context) {
        this.paymentFactory = paymentFactory;
        this.context = context;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Override
    public Mono<Void> save(Payment payment) {
        return Mono.just(payment)
                .map(i -> i.getState())
                .flatMap(state -> Mono.from(context
                        .insertInto(PAYMENT)
                        .columns(PAYMENT.ID, PAYMENT.BOOKING_ID, PAYMENT._DATE, PAYMENT._VALUE)
                        .values(
                                state.id().id(),
                                state.bookingId().id(),
                                state.date().date(),
                                state.value().value())))
                .flatMapMany(i -> Flux.fromStream(payment.popEvents().stream()))
                .flatMap(i -> Mono.from(context
                        .insertInto(Event.EVENT)
                        .columns(Event.EVENT.ID, Event.EVENT.SOURCE, Event.EVENT._WHEN, Event.EVENT.PAYLOAD, Event.EVENT.STATUS)
                        .values(
                                i.id(),
                                i.source(),
                                i.when(),
                                toJson(i.payload()),
                                EventProcessingStatus.Pending.name()))
                )
                .then();
    }

    @SneakyThrows
    private String toJson(Payload payload) {
        return objectMapper.writeValueAsString(payload);
    }

    @Override
    public Mono<Payment> find(PaymentId id) {
        return Flux.from(context.select(PAYMENT.ID, PAYMENT.BOOKING_ID, PAYMENT._DATE, PAYMENT._VALUE)
                .from(PAYMENT)
                        .where(PAYMENT.ID.eq(id.id())))
                .map(r -> r)
                .take(1)
                .map(r -> new PaymentState(
                        new PaymentId(r.getValue(0, String.class)),
                        new BookingId(r.getValue(1, String.class)),
                        new PaymentDate(r.getValue(2, LocalDate.class)),
                        new PaymentValue(r.getValue(3, BigDecimal.class))
                ))
                .map(paymentFactory::ofState)
                .collectList()
                .flatMap(l -> Mono.just(l.get(0)))
                ;
    }

    @Override
    public Flux<Payment> findByBookingId(BookingId bookingId) {
        return Flux.from(context.select(PAYMENT.ID, PAYMENT.BOOKING_ID, PAYMENT._DATE, PAYMENT._VALUE)
                        .from(PAYMENT)
                        .where(PAYMENT.BOOKING_ID.eq(bookingId.id()))
                )
                .map(r -> r)
                .map(r -> new PaymentState(
                        new PaymentId(r.getValue(0, String.class)),
                        new BookingId(r.getValue(1, String.class)),
                        new PaymentDate(r.getValue(2, LocalDate.class)),
                        new PaymentValue(r.getValue(3, BigDecimal.class))
                ))
                .map(paymentFactory::ofState);
    }
}
