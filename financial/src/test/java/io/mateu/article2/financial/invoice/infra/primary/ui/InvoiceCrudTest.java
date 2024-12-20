package io.mateu.article2.financial.invoice.infra.primary.ui;


import io.mateu.article2.financial.invoice.domain.invoice.InvoiceFactory;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceRepository;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceState;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.BookingId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceDate;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceValue;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class InvoiceCrudTest {

    @Autowired
    private InvoiceCrud invoiceCrud;

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private InvoiceFactory invoiceFactory;

    @Test
    public void returnsPage() throws Throwable {
        var bookingId = Instancio.create(BookingId.class);

        for (int i = 0; i < 30; i++) {
            createInvoice(bookingId);
        }

        var page = invoiceCrud.fetchRows(
                bookingId.id(),
                new InvoiceCrudFilters(),
                PageRequest.of(0, 10, Sort.unsorted()))
                .block()
                ;
        assertThat(page).isNotNull();
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getTotalElements()).isEqualTo(30);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent()).isNotNull();
        assertThat(page.getContent().size()).isEqualTo(10);
    }

    @Test
    public void returnsExpectedPage() throws Throwable {
        var bookingId = Instancio.create(BookingId.class);

        for (int i = 0; i < 3; i++) {
            createInvoice(bookingId);
        }

        var page = invoiceCrud.fetchRows(
                        bookingId.id(),
                        new InvoiceCrudFilters(),
                        PageRequest.of(0, 10, Sort.unsorted()))
                .block()
                ;
        assertThat(page).isNotNull();
        assertThat(page.getNumber()).isEqualTo(0);
        assertThat(page.getSize()).isEqualTo(10);
        assertThat(page.getTotalElements()).isEqualTo(3);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getContent()).isNotNull();
        assertThat(page.getContent().size()).isEqualTo(3);
    }

    private void createInvoice(BookingId bookingId) {
        invoiceRepository.save(invoiceFactory.create(
                Instancio.create(InvoiceId.class),
                bookingId,
                Instancio.create(InvoiceDate.class),
                Instancio.create(InvoiceValue.class)
                )).block();
    }

}