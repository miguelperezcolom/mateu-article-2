package io.mateu.article2.financial.payment.infra.primary.ui;

import io.mateu.uidl.annotations.Money;
import io.mateu.uidl.annotations.Title;
import io.mateu.uidl.interfaces.Crud;
import lombok.extern.slf4j.Slf4j;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static io.mateu.article2.financial.jooq.model.default_schema.tables.Payment.PAYMENT;

record PaymentCrudRow(String id, String bookingId, LocalDate date, @Money BigDecimal value) {

}

record PaymentCrudFilters() {

}

@Service
@Title("Payments")
@Slf4j
public class PaymentCrud implements Crud<PaymentCrudFilters, PaymentCrudRow> {

    private final DSLContext context;

    public PaymentCrud(DSLContext context) {
        this.context = context;
    }

    @Override
    public Mono<Page<PaymentCrudRow>> fetchRows(String searchText, PaymentCrudFilters filters, Pageable pageable) throws Throwable {
        var cleanSearchText = (searchText != null && !searchText.isEmpty())?"%" + searchText + "%":searchText;
        return getList(cleanSearchText, filters, pageable)
                .collectList()
                .zipWith(getCount(cleanSearchText, filters))
                .flatMap(t -> Mono.just(new PageImpl<>(t.getT1(), pageable, t.getT2())));
    }

    private Mono<Long> getCount(String cleanSearchText, PaymentCrudFilters filters) {
        var select = context.select(DSL.field("count(*)", SQLDataType.BIGINT))
                .from(PAYMENT);

        var query = addWhereClause(cleanSearchText, select);
        return Flux.from(query)
                .collectList()
                .flatMap(r -> Mono.just(r.get(0).get(0, BigInteger.class).longValue()));
    }

    private static SelectConditionStep<? extends Record> addWhereClause(String cleanSearchText, SelectJoinStep<? extends Record> select) {
        var query = select.where();
        if (cleanSearchText != null && !cleanSearchText.isEmpty()) {
            query = select.where(PAYMENT.ID.likeIgnoreCase(cleanSearchText).or(PAYMENT.BOOKING_ID.likeIgnoreCase(cleanSearchText)));
        }
        return query;
    }

    private Flux<PaymentCrudRow> getList(String cleanSearchText, PaymentCrudFilters filters, Pageable pageable) {
        var query = addWhereClause(cleanSearchText, context.select(PAYMENT.ID, PAYMENT.BOOKING_ID, PAYMENT._DATE, PAYMENT._VALUE)
                .from(PAYMENT))
                .orderBy(map(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.toLimit().max());
        log.info(query.getSQL());
        return Flux.from(query)
                .map(r -> new PaymentCrudRow(
                        r.get(0, String.class),
                        r.get(1, String.class),
                        r.get(2, LocalDate.class),
                        r.get(3, BigDecimal.class)
                ));

    }

    private Collection<SortField<?>> map(Sort sort) {
        List<SortField<?>> orderFields = new ArrayList<>();
        if (sort != null) {
            orderFields.addAll(sort.stream().map(s -> {
                var field = getField(s);
                if (s.isDescending()) {
                    return field.desc();
                }
                return field.asc();
            }).toList());
        }
        return orderFields;
    }

    private static Field<?> getField(Sort.Order s) {
        return switch (s.getProperty()) {
            case "id" -> PAYMENT.ID;
            case "bookingId" -> PAYMENT.BOOKING_ID;
            case "date" -> PAYMENT._DATE;
            case "value" -> PAYMENT._VALUE;
            default -> throw new IllegalStateException("Unexpected value: " + s.getProperty());
        };
    }
}
