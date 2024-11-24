package io.mateu.article2.booking.infra.secondary.events;

import io.mateu.article2.booking.domain.ports.output.EventBus;
import io.mateu.article2.booking.infra.secondary.persistence.event.EventEntity;
import io.mateu.article2.booking.infra.secondary.persistence.event.EventEntityRepository;
import io.mateu.article2.booking.infra.secondary.persistence.event.EventProcessingStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DomainEventPoller {

    private final EventEntityRepository eventEntityRepository;
    private final EventBus eventBus;

    public DomainEventPoller(EventEntityRepository eventEntityRepository, EventBus eventBus) {
        this.eventEntityRepository = eventEntityRepository;
        this.eventBus = eventBus;
    }

    @Scheduled(initialDelay = 5000, fixedDelay = 1000)
    public void run() {
        pollAndSend().blockLast();
    }

    @Transactional
    protected Flux<EventEntity> pollAndSend() {
        return eventEntityRepository.findAllByProcessingStatus(EventProcessingStatus.Pending)
                .flatMap(e -> eventBus.send(e.getPayload()).then(Mono.just(e)))
                .map(e -> new EventEntity(
                        e.getId(),
                        e.getSource(),
                        e.getWhen(),
                        e.getPayload(),
                        EventProcessingStatus.Done
                ))
                .flatMap(eventEntityRepository::save);
    }

}
