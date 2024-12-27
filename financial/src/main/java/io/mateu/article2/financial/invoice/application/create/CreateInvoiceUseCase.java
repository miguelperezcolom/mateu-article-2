package io.mateu.article2.financial.invoice.application.create;

import io.mateu.article2.financial.invoice.domain.invoice.InvoiceFactory;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceRepository;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceState;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateInvoiceUseCase {

    private final InvoiceFactory invoiceFactory;
    private final InvoiceRepository invoiceRepository;

    public CreateInvoiceUseCase(InvoiceFactory invoiceFactory, InvoiceRepository invoiceRepository) {
        this.invoiceFactory = invoiceFactory;
        this.invoiceRepository = invoiceRepository;
    }

    public Mono<Void> handle(CreateInvoiceRequest request) {
        return Mono.just(request)
                .map(r -> new InvoiceState(
                        r.id(),
                        r.bookingId(),
                        r.date(),
                        r.value()
                ))
                .map(invoiceFactory::create)
                .flatMap(invoiceRepository::save);
    }

}
