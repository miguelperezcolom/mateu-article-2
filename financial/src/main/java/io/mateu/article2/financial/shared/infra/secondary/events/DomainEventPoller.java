package io.mateu.article2.financial.shared.infra.secondary.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.mateu.article2.financial.shared.domain.ports.output.EventBus;
import io.mateu.article2.shared.events.DomainEvent;
import io.mateu.article2.shared.events.EventProcessingStatus;
import io.mateu.article2.shared.events.Payload;
import lombok.SneakyThrows;
import org.jooq.DSLContext;
import org.jooq.Record5;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static io.mateu.article2.financial.jooq.model.default_schema.tables.Event.EVENT;

@Service
public class DomainEventPoller {

    private final DSLContext context;
    private final EventBus eventBus;
    private final ObjectMapper objectMapper;

    public DomainEventPoller(DSLContext context, EventBus eventBus, ObjectMapper objectMapper) {
        this.context = context;
        this.eventBus = eventBus;
        this.objectMapper = objectMapper;
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 1000)
    public void run() {
        pollAndSend().blockLast();
    }

    /*
    todo: needs to be moved to another micro service, which can have 1 replica only, as mongodb does not support select for update
     */
    @Transactional
    protected Flux<DomainEvent> pollAndSend() {
        return Flux.from(context.select(EVENT.ID, EVENT.SOURCE, EVENT._WHEN, EVENT.PAYLOAD, EVENT.STATUS)
                .from(EVENT).where(EVENT.STATUS.eq(EventProcessingStatus.Pending.name())))
                .collectList()
                .flatMapMany(l -> Flux.fromStream(l.stream()))
                .map(this::toEvent)
                .flatMap(e -> eventBus.send(e.payload()).then(Mono.just(e)))
                .flatMap(this::save);
    }

    private DomainEvent toEvent(Record5<String, String, LocalDateTime, String, String> record) {
        return new DomainEvent(
                record.component1(),
                record.component2(),
                record.component3(),
                fromJson(record.component4())
                );
    }

    private Mono<DomainEvent> save(DomainEvent domainEvent) {
        return Mono.from(context.update(EVENT).set(EVENT.STATUS, EventProcessingStatus.Done.name())).then(Mono.just(domainEvent));
    }

    @SneakyThrows
    private Payload fromJson(String json) {
        return objectMapper.readValue(json, Payload.class);
    }


}
