package io.mateu.article2.booking.infra.secondary.events;

import io.mateu.article2.booking.domain.ports.output.EventBus;
import io.mateu.article2.shared.events.IntegrationEvent;
import io.mateu.article2.shared.events.Payload;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Profile("!test")
public class KafkaEventBus implements EventBus {

    final StreamBridge streamBridge;

    public KafkaEventBus(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Override
    public Mono<Payload> send(Payload payload) {
        streamBridge.send("output", new IntegrationEvent(
                UUID.randomUUID().toString(),
                "booking",
                LocalDateTime.now(),
                payload
        ));
        return Mono.just(payload);
    }
}
