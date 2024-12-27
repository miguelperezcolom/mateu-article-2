package io.mateu.article2.cqrssink.infra.primary.events;

import io.mateu.article2.cqrssink.application.booking.created.HandleBookingCreatedUseCase;
import io.mateu.article2.cqrssink.application.booking.updated.HandleBookingUpdatedUseCase;
import io.mateu.article2.cqrssink.application.invoice.HandleInvoiceCreatedUseCase;
import io.mateu.article2.cqrssink.application.payment.HandlePaymentCreatedUseCase;
import io.mateu.article2.shared.events.IntegrationEvent;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import io.mateu.article2.shared.events.payloads.BookingUpdated;
import io.mateu.article2.shared.events.payloads.InvoiceCreated;
import io.mateu.article2.shared.events.payloads.PaymentCreated;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class StreamConsumersConfig {

    private final HandleBookingCreatedUseCase handleBookingCreatedUseCase;
    private final HandleBookingUpdatedUseCase handleBookingUpdatedUseCase;
    private final HandleInvoiceCreatedUseCase handleInvoiceCreatedUseCase;
    private final HandlePaymentCreatedUseCase handlePaymentCreatedUseCase;

    public StreamConsumersConfig(HandleBookingCreatedUseCase handleBookingCreatedUseCase, HandleBookingUpdatedUseCase handleBookingUpdatedUseCase, HandleInvoiceCreatedUseCase handleInvoiceCreatedUseCase, HandlePaymentCreatedUseCase handlePaymentCreatedUseCase) {
        this.handleBookingCreatedUseCase = handleBookingCreatedUseCase;
        this.handleBookingUpdatedUseCase = handleBookingUpdatedUseCase;
        this.handleInvoiceCreatedUseCase = handleInvoiceCreatedUseCase;
        this.handlePaymentCreatedUseCase = handlePaymentCreatedUseCase;
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
            if (message.payload() instanceof InvoiceCreated invoiceCreated) {
                handleInvoiceCreatedUseCase.handle(invoiceCreated);
            }
            if (message.payload() instanceof PaymentCreated paymentCreated) {
                handlePaymentCreatedUseCase.handle(paymentCreated);
            }
        };
    }

}
