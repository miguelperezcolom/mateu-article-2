package io.mateu.article2.financial.payment.application.create;

import io.mateu.article2.financial.payment.domain.payment.PaymentFactory;
import io.mateu.article2.financial.payment.domain.payment.PaymentRepository;
import io.mateu.article2.financial.payment.domain.payment.PaymentState;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreatePaymentUseCase {

    private final PaymentFactory paymentFactory;
    private final PaymentRepository paymentRepository;

    public CreatePaymentUseCase(PaymentFactory paymentFactory, PaymentRepository paymentRepository) {
        this.paymentFactory = paymentFactory;
        this.paymentRepository = paymentRepository;
    }

    public Mono<Void> handle(CreatePaymentRequest request) {
        return Mono.just(request)
                .map(r -> new PaymentState(
                        r.id(),
                        r.bookingId(),
                        r.date(),
                        r.value()
                ))
                .map(paymentFactory::ofState)
                .flatMap(paymentRepository::save);
    }

}
