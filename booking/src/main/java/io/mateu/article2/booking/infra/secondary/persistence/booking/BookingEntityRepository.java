package io.mateu.article2.booking.infra.secondary.persistence.booking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookingEntityRepository extends ReactiveCrudRepository<BookingEntity, String> {

    Flux<BookingEntity> findAllBySearchableTextContainingIgnoreCase(String search, Pageable pageable);

}
