package io.mateu.article2.booking.domain.ports.output;

import io.mateu.article2.shared.events.Payload;

public interface EventBus {

    void send(Payload payload);

}
