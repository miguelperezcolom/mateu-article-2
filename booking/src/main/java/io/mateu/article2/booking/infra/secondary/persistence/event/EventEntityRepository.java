package io.mateu.article2.booking.infra.secondary.persistence.event;

import io.mateu.article2.shared.events.EventProcessingStatus;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventEntityRepository extends ReactiveCrudRepository<EventEntity, String> {

    Flux<EventEntity> findAllByProcessingStatus(EventProcessingStatus processingStatus);

}
