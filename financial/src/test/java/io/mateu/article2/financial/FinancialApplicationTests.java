package io.mateu.article2.financial;

import io.mateu.article2.financial.jooq.model.default_schema.tables.Booking;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import java.sql.SQLException;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class FinancialApplicationTests {

    @Autowired
    DSLContext context;

    @Test
    void contextLoads() {
    }

    @Test
    void jooqWorks() throws SQLException {
        Flux.from(context.insertInto(Booking.BOOKING).columns(Booking.BOOKING.ID).values(UUID.randomUUID().toString())).collectList().block();
    }

    @Test
    @Disabled
    void selectWorks() throws SQLException {

        var record = context.select(Booking.BOOKING.ID)
                .from(Booking.BOOKING).fetch();
        var list = record.stream().toList();
        System.out.println("list = " + list.stream().map(r -> r.component1()).toList());
        System.out.println("list.size = " + list.size());
    }

    @Test
    void reactiveSelectWorks() throws SQLException {

        var list = Flux.from(context.select(Booking.BOOKING.ID)
                .from(Booking.BOOKING)).map(r -> r.component1()).collectList().block();
        System.out.println("list = " + list);
        System.out.println("list.size = " + list.size());
    }

}
