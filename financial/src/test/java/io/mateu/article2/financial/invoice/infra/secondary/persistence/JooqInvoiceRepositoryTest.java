package io.mateu.article2.financial.invoice.infra.secondary.persistence;

import io.mateu.article2.financial.invoice.domain.invoice.InvoiceFactory;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceRepository;
import io.mateu.article2.financial.invoice.domain.invoice.InvoiceState;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.BookingId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceDate;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceValue;
import org.instancio.Instancio;
import org.instancio.When;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.ExecutionException;

import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class JooqInvoiceRepositoryTest {

    @Autowired
    InvoiceFactory invoiceFactory;
    @Autowired
    InvoiceRepository invoiceRepository;

    @Test
    void invoiceIsSavedAndReturned() throws ExecutionException, InterruptedException {
        InvoiceState invoiceState = Instancio.create(InvoiceState.class);

        invoiceRepository.save(invoiceFactory.create(invoiceState)).block();

        var invoice = invoiceRepository.find(invoiceState.id()).block();
        assertNotNull(invoice);
        assertEquals(invoiceState, invoice.getState());
    }


    @Test
    void returnsInvoices() {
        var bookingId = Instancio.create(String.class);

        saveInvoice(bookingId);
        saveInvoice(bookingId);

        var invoices = invoiceRepository.findByBookingId(new BookingId(bookingId)).collectList().block();
        assertNotNull(invoices);
        assertEquals(2, invoices.size());

    }

    private void saveInvoice(String bookingId) {
        InvoiceState invoiceState = new InvoiceState(
                new InvoiceId(Instancio.create(String.class)),
                new BookingId(bookingId),
                new InvoiceDate(Instancio.create(LocalDate.class)),
                new InvoiceValue(Instancio.create(BigDecimal.class))
        );
        invoiceRepository.save(invoiceFactory.create(invoiceState)).block();
    }
}