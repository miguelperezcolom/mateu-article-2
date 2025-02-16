package io.mateu.article2.financial.shared.domain.ports.output;

import io.mateu.article2.shared.events.Payload;
import reactor.core.publisher.Mono;

public interface EventBus {

    Mono<Payload> send(Payload payload);

}
