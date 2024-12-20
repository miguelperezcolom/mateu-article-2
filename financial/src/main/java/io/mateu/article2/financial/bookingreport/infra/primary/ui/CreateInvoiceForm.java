package io.mateu.article2.financial.bookingreport.infra.primary.ui;

import io.mateu.article2.financial.invoice.application.create.CreateInvoiceRequest;
import io.mateu.article2.financial.invoice.application.create.CreateInvoiceUseCase;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.BookingId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceDate;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceId;
import io.mateu.article2.financial.invoice.domain.invoice.valueobjects.InvoiceValue;
import io.mateu.uidl.annotations.*;
import io.mateu.uidl.data.CloseModal;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Title("Create invoice")
@Scope("prototype")
public class CreateInvoiceForm {

    private final CreateInvoiceUseCase createInvoiceUseCase;
    private final ApplicationContext applicationContext;
    @DataOnly
    String bookingId;

    public CreateInvoiceForm(CreateInvoiceUseCase createInvoiceUseCase, ApplicationContext applicationContext) {
        this.createInvoiceUseCase = createInvoiceUseCase;
        this.applicationContext = applicationContext;
    }

    @RequestFocus
    @NotNull
    LocalDate date;
    @NotNull
    BigDecimal value;

    @SneakyThrows
    @MainAction(target = ActionTarget.View)
    Mono<CloseModal<BookingReport>> create() {
        return createInvoiceUseCase.handle(new CreateInvoiceRequest(
                new InvoiceId(UUID.randomUUID().toString()),
                new BookingId(bookingId),
                new InvoiceDate(date),
                new InvoiceValue(value)
        )).then(applicationContext.getBean(BookingReport.class).load(bookingId)).map(CloseModal::new);
    }

    public CreateInvoiceForm load(String bookingId) {
        this.bookingId = bookingId;
        return this;
    }
}
