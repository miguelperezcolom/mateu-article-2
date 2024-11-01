package io.mateu.article2.shared.events;

import java.time.LocalDateTime;

public record IntegrationEvent(
        String id,
        String source,
        LocalDateTime when,
        Payload payload
) {
}
