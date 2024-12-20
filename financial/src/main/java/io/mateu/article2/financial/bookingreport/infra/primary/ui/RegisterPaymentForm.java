package io.mateu.article2.financial.bookingreport.infra.primary.ui;

import io.mateu.article2.financial.payment.application.create.CreatePaymentRequest;
import io.mateu.article2.financial.payment.application.create.CreatePaymentUseCase;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.BookingId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentDate;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentId;
import io.mateu.article2.financial.payment.domain.payment.valueobjects.PaymentValue;
import io.mateu.uidl.annotations.*;
import io.mateu.uidl.data.CloseModal;
import jakarta.validation.constraints.NotNull;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Service
@Title("Register payment")
@Scope("prototype")
public class RegisterPaymentForm {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final ApplicationContext applicationContext;
    @DataOnly
    String bookingId;

    public RegisterPaymentForm(CreatePaymentUseCase createPaymentUseCase, ApplicationContext applicationContext) {
        this.createPaymentUseCase = createPaymentUseCase;
        this.applicationContext = applicationContext;
    }

    @RequestFocus
    @NotNull
    LocalDate date;
    @NotNull
    BigDecimal value;

    @MainAction(target = ActionTarget.View)
    Mono<CloseModal<BookingReport>> create() {
        return createPaymentUseCase.handle(new CreatePaymentRequest(
                new PaymentId(UUID.randomUUID().toString()),
                new BookingId(bookingId),
                new PaymentDate(date),
                new PaymentValue(value)
        )).then(applicationContext.getBean(BookingReport.class).load(bookingId)).map(CloseModal::new);
    }

    public RegisterPaymentForm load(String bookingId) {
        this.bookingId = bookingId;
        return this;
    }
}
