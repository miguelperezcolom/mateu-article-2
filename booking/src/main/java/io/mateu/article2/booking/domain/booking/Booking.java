package io.mateu.article2.booking.domain.booking;

import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.shared.domain.AggregateRoot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Scope("prototype")
public class Booking extends AggregateRoot {

    @Getter@Setter(AccessLevel.PROTECTED)
    private BookingState state;


    public Mono<Booking> cancel() {
        return Mono.just(this);
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
        return Mono.just(this);
    }

}
