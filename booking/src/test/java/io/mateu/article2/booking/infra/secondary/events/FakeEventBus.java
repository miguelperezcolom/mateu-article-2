package io.mateu.article2.booking.infra.secondary.events;

import io.mateu.article2.booking.domain.ports.output.EventBus;
import io.mateu.article2.shared.events.Payload;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service@Primary
public class FakeEventBus implements EventBus {

    private final List<Payload> sent = new ArrayList<>();

    @Override
    public Mono<Payload> send(Payload payload) {
        return Mono.just(payload).doOnNext(sent::add);
    }

    public List<Payload> popAll() {
        var all = new ArrayList<>(sent);
        sent.clear();
        return all;
    }

}
