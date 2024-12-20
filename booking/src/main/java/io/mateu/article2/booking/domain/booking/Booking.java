package io.mateu.article2.booking.domain.booking;

import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.shared.domain.AggregateRoot;
import io.mateu.article2.shared.events.DomainEvent;
import io.mateu.article2.shared.events.payloads.BookingUpdated;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Scope("prototype")
public class Booking extends AggregateRoot {

    @Getter@Setter(AccessLevel.PROTECTED)
    private BookingState state;


    public Mono<Booking> cancel(BookingValue newValue) {
        this.state = new BookingState(
                state.id(),
                state.customerName(),
                state.serviceDescription(),
                state.startDate(),
                state.endDate(),
                newValue,
                BookingStatus.Cancelled
        );
        addUpdatedEvent();
        return Mono.just(this);
    }

    private void addUpdatedEvent() {
        addEvent(new DomainEvent(
                UUID.randomUUID().toString(),
                "booking-" + state.id(),
                LocalDateTime.now(),
                new BookingUpdated(
                        state.id().id(),
                        state.customerName().name(),
                        state.serviceDescription().description(),
                        state.startDate().date(),
                        state.endDate().date(),
                        state.value().value(),
                        state.status().name())
        ));

    }

    public Mono<Booking> invoice() {
        return Mono.just(this);
    }

    public Mono<Booking> update(
            CustomerName customerName,
            ServiceDescription serviceDescription,
            BookingStartDate startDate,
            BookingEndDate endDate,
            BookingValue value
    ) {
        this.state = new BookingState(
                state.id(),
                customerName,
                serviceDescription,
                startDate,
                endDate,
                value,
                state.status()
        );
        addUpdatedEvent();
        return Mono.just(this);
    }

}
