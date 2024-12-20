package io.mateu.article2.financial.invoice.infra.secondary.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceFactory;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceRepository;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceState;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.BookingId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceDate;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceValue;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Event;
import io.mateu.article2.financial.jooq.model.default_schema.tables.Invoice;
import io.mateu.article2.shared.events.EventProcessingStatus;
import io.mateu.article2.shared.events.Payload;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;

@Transactional
@Service
public class JooqInvoiceRepository implements InvoiceRepository {

    private final InvoiceFactory invoiceFactory;
    private final DSLContext context;
    private final ObjectMapper objectMapper;

    public JooqInvoiceRepository(InvoiceFactory invoiceFactory, DSLContext context) {
        this.invoiceFactory = invoiceFactory;
        this.context = context;
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }


    @Override
    public Mono<Void> save(io.mateu.article2.financial.invoice.domain.invoice.Invoice invoice) {
        return Mono.just(invoice)
                .map(i -> i.getState())
                .flatMap(state -> Mono.from(context
                        .insertInto(Invoice.INVOICE)
                        .columns(Invoice.INVOICE.ID, Invoice.INVOICE.BOOKING_ID, Invoice.INVOICE._DATE, Invoice.INVOICE._VALUE)
                        .values(
                                state.id().id(),
                                state.bookingId().id(),
                                state.date().date(),
                                state.value().value())))
                .flatMapMany(i -> Flux.fromStream(invoice.popEvents().stream()))
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
    public Mono<io.mateu.article2.financial.invoice.domain.invoice.Invoice> find(InvoiceId id) {
        return Flux.from(context.select(Invoice.INVOICE.ID, Invoice.INVOICE.BOOKING_ID, Invoice.INVOICE._DATE, Invoice.INVOICE._VALUE)
                .from(Invoice.INVOICE)
                        .where(Invoice.INVOICE.ID.eq(id.id())))
                .map(r -> r)
                .take(1)
                .map(r -> new InvoiceState(
                        new InvoiceId(r.getValue(0, String.class)),
                        new BookingId(r.getValue(1, String.class)),
                        new InvoiceDate(r.getValue(2, LocalDate.class)),
                        new InvoiceValue(r.getValue(3, BigDecimal.class))
                ))
                .map(invoiceFactory::ofState)
                .collectList()
                .flatMap(l -> Mono.just(l.get(0)))
                ;
    }

    @Override
    public Flux<io.mateu.article2.financial.invoice.domain.invoice.Invoice> findByBookingId(BookingId bookingId) {
        return Flux.from(context.select(Invoice.INVOICE.ID, Invoice.INVOICE.BOOKING_ID, Invoice.INVOICE._DATE, Invoice.INVOICE._VALUE)
                        .from(Invoice.INVOICE)
                        .where(Invoice.INVOICE.BOOKING_ID.eq(bookingId.id()))
                )
                .map(r -> r)
                .map(r -> new InvoiceState(
                        new InvoiceId(r.getValue(0, String.class)),
                        new BookingId(r.getValue(1, String.class)),
                        new InvoiceDate(r.getValue(2, LocalDate.class)),
                        new InvoiceValue(r.getValue(3, BigDecimal.class))
                ))
                .map(invoiceFactory::ofState);
    }
}
