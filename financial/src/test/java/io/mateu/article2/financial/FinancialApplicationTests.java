package io.mateu.article2.financial;

import io.mateu.article2.financial.jooq.model.default_schema.tables.TestTable;
import io.mateu.article2.financial.jooq.model.default_schema.tables.TestTable5;
import org.jooq.DSLContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

@SpringBootTest
class FinancialApplicationTests {

    @Autowired
    DSLContext context;

    @Test
    void contextLoads() {
    }

    @Test
    void jooqWorks() throws SQLException {

        var record = context.newRecord(TestTable5.TEST_TABLE5);
        record.setTestColumn(UUID.randomUUID().toString());
        record.store();

    }

    @Test
    void selectWorks() throws SQLException {

        var record = context.select(TestTable5.TEST_TABLE5.TEST_COLUMN)
                .from(TestTable5.TEST_TABLE5).fetch();
        var list = record.stream().toList();
        System.out.println("list = " + list.stream().map(r -> r.component1()).toList());
        System.out.println("list.size = " + list.size());
    }

    @Test
    void reactiveSelectWorks() throws SQLException {

        var list = Flux.from(context.select(TestTable5.TEST_TABLE5.TEST_COLUMN)
                .from(TestTable5.TEST_TABLE5)).map(r -> r.component1()).collectList().block();
        System.out.println("list = " + list);
        System.out.println("list.size = " + list.size());
    }

}
