package io.mateu.article2.financial.invoice.domain.invoice;

import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.BookingId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceId;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface InvoiceRepository {

    Mono<Void> save(Invoice invoice);

    Mono<Invoice> find(InvoiceId id);

    Flux<Invoice> findByBookingId(BookingId bookingId);

}
