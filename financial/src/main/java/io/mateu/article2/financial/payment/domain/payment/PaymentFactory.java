package io.mateu.article2.financial.payment.domain.payment;

import io.mateu.article2.financial.payment.domain.payment.valueobjects.BookingId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentDate;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentValue;
import io.mateu.article2.shared.events.DomainEvent;
import io.mateu.article2.shared.events.payloads.PaymentCreated;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentFactory {

    private final ApplicationContext applicationContext;

    public PaymentFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public Payment create(PaymentId id, BookingId bookingId, PaymentDate paymentDate, PaymentValue value) {
        return create(new PaymentState(
                id,
                bookingId,
                paymentDate,
                value
        ));
    }

    public Payment create(PaymentState paymentState) {
        var payment = applicationContext.getBean(Payment.class);
        payment.setState(paymentState);
        payment.addEvent(new DomainEvent(
                UUID.randomUUID().toString(),
                "invoice-" + paymentState.id().id(),
                LocalDateTime.now(),
                new PaymentCreated(
                        paymentState.bookingId().id(),
                        paymentState.id().id(),
                        paymentState.date().date(),
                        paymentState.value().value())
        ));
        return payment;
    }

    public Payment ofState(PaymentState paymentState) {
        var payment = applicationContext.getBean(Payment.class);
        payment.setState(paymentState);
        return payment;
    }
}
