package io.mateu.article2.financial.payment.infra.primary.events;

import io.mateu.article2.shared.events.IntegrationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@Slf4j
public class PaymentStreamConsumersConfig {

    @Bean
    public Consumer<IntegrationEvent> consumeAllForPayment() {
        return message -> {
            log.info("Received message {}", message);
        };
    }

}
