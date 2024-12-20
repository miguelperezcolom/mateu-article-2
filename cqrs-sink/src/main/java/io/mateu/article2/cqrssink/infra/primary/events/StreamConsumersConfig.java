package io.mateu.article2.cqrssink.infra.primary.events;

import io.mateu.article2.cqrssink.application.booking.created.HandleBookingCreatedUseCase;
import io.mateu.article2.cqrssink.application.booking.updated.HandleBookingUpdatedUseCase;
import io.mateu.article2.shared.events.IntegrationEvent;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import io.mateu.article2.shared.events.payloads.BookingUpdated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class StreamConsumersConfig {

    private final HandleBookingCreatedUseCase handleBookingCreatedUseCase;
    private final HandleBookingUpdatedUseCase handleBookingUpdatedUseCase;

    public StreamConsumersConfig(HandleBookingCreatedUseCase handleBookingCreatedUseCase, HandleBookingUpdatedUseCase handleBookingUpdatedUseCase) {
        this.handleBookingCreatedUseCase = handleBookingCreatedUseCase;
        this.handleBookingUpdatedUseCase = handleBookingUpdatedUseCase;
    }


    @Bean
    public Consumer<IntegrationEvent> consumeAll() {
        return message -> {
            log.info("Received message {}", message);
            if (message.payload() instanceof BookingCreated bookingCreated) {
                handleBookingCreatedUseCase.handle(bookingCreated);
            }
            if (message.payload() instanceof BookingUpdated bookingUpdated) {
                handleBookingUpdatedUseCase.handle(bookingUpdated);
            }
        };
    }

}
