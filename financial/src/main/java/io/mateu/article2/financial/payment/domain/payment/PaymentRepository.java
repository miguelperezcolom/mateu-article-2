package io.mateu.article2.financial.payment.domain.payment;

import io.mateu.article2.financial.payment.domain.payment.valueobjects.BookingId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PaymentRepository {

    Mono<Void> save(Payment invoice);

    Mono<Payment> find(PaymentId id);

    Flux<Payment> findByBookingId(BookingId bookingId);

}
