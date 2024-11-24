package io.mateu.article2.booking.infra.secondary.persistence.event;

import io.mateu.article2.booking.infra.secondary.persistence.booking.BookingEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventEntityRepository extends ReactiveCrudRepository<EventEntity, String> {

    Flux<EventEntity> findAllByProcessingStatus(EventProcessingStatus processingStatus);

}
