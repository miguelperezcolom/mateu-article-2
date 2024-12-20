package io.mateu.article2.booking.infra.secondary.cqrs;

import io.mateu.article2.booking.domain.ports.output.CQRSReader;
import io.mateu.article2.booking.domain.ports.output.ListedBooking;
import io.mateu.dtos.Listener;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class R2dbcCQRSReader implements CQRSReader {

    private final ConnectionFactory connectionFactory;
    private final DatabaseClient client;

    public R2dbcCQRSReader(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        client = DatabaseClient.create(connectionFactory);
    }

    @Override
    public Mono<Page<ListedBooking>> search(String text, Pageable pageable) {
        if (text == null) {
            text = "%";
        } else {
            text = "%" + text + "%";
        }
        return client.sql("select count(*) from booking where searchable_text ilike :text").bind("text", text)
                .map(r -> (long) r.get(0)).first()
                .zipWith(client.sql("SELECT id, " +
                                "customer, " +
                                "service, " +
                                "start_date, " +
                                "end_date, " +
                                "status, " +
                                "value, " +
                                "invoiced, " +
                                "paid " +
                                "FROM booking WHERE searchable_text ilike (:text)")
                        .bind("text", text).map(r -> {
                            int col = 0;
                            return new ListedBooking(
                                    (String) r.get(col++),
                                    (String) r.get(col++),
                                    (String) r.get(col++),
                                    (String) r.get(col++),
                                    (String) r.get(col++),
                                    (String) r.get(col++),
                                    (BigDecimal) r.get(col++),
                                    (BigDecimal) r.get(col++),
                                    (BigDecimal) r.get(col)
                            );
                        }).all().collectList()).map(z -> new PageImpl<>(
                        z.getT2(),
                        pageable, z.getT1()
                ));
    }
}
