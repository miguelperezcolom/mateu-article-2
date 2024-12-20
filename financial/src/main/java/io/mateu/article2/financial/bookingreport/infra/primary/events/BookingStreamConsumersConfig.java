package io.mateu.article2.financial.bookingreport.infra.primary.events;

import io.mateu.article2.financial.bookingreport.application.booking.created.HandleBookingCreatedUseCase;
import io.mateu.article2.financial.bookingreport.application.booking.updated.HandleBookingUpdatedUseCase;
import io.mateu.article2.shared.events.IntegrationEvent;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import io.mateu.article2.shared.events.payloads.BookingUpdated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class BookingStreamConsumersConfig {

    private final HandleBookingCreatedUseCase handleBookingCreatedUseCase;
    private final HandleBookingUpdatedUseCase handleBookingUpdatedUseCase;

    public BookingStreamConsumersConfig(HandleBookingCreatedUseCase handleBookingCreatedUseCase, HandleBookingUpdatedUseCase handleBookingUpdatedUseCase) {
        this.handleBookingCreatedUseCase = handleBookingCreatedUseCase;
        this.handleBookingUpdatedUseCase = handleBookingUpdatedUseCase;
    }


    @Bean
    public Consumer<IntegrationEvent> consumeAllForBooking() {
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
