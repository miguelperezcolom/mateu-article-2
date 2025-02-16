package io.mateu.article2.financial.invoice.infra.primary.events;

import io.mateu.article2.shared.events.IntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class InvoiceStreamConsumersConfig {

    @Bean
    public Consumer<IntegrationEvent> consumeAllForInvoice() {
        return message -> {
            log.info("Received message {}", message);
        };
    }

}
