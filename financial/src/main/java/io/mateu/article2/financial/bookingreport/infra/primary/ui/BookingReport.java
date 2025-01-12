package io.mateu.article2.financial.bookingreport.infra.primary.ui;

import io.mateu.article2.financial.bookingreport.application.booking.report.GetBookingReportDataRequest;
import io.mateu.article2.financial.bookingreport.application.booking.report.GetBookingReportDataUseCase;
import io.mateu.uidl.annotations.*;
import io.mateu.uidl.data.VGap;
import io.mateu.uidl.interfaces.ConsumesContextData;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Scope;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.Callable;

@MateuUI("/financial/bookingreport")
@Service
@Title("")
@Scope("prototype")
public class BookingReport implements ConsumesContextData {

    private final GetBookingReportDataUseCase getBookingReportDataUseCase;

    @DataOnly
    String id;

    @Section(value = "", sidePositionedLabel = true, itemLabelWidth = "80px")

    VGap gap1 = new VGap("23px");

    @Output
    @Money
    double value;

    @Output
    @Money
    double invoiced;

    @Output
    @Money
    double paid;

    @Output
    @Money
    @Bold
    double pending;

    VGap gap2 = new VGap("10px");

    @Button(type = ActionType.Secondary, target = ActionTarget.NewModal)
    @Width("100%")
    Callable<CreateInvoiceForm> createInvoice;

    @Button(type = ActionType.Secondary, target = ActionTarget.NewModal)
    @Width("100%")
    Callable<RegisterPaymentForm> registerPayment;

    public BookingReport(GetBookingReportDataUseCase getBookingReportDataUseCase, CreateInvoiceForm createInvoiceForm, RegisterPaymentForm registerPaymentForm) {
        this.getBookingReportDataUseCase = getBookingReportDataUseCase;
        createInvoice = () -> createInvoiceForm.load(id);
        registerPayment = () -> registerPaymentForm.load(id);
    }

    public Mono<BookingReport> load(String id) {
        return getBookingReportDataUseCase.handle(new GetBookingReportDataRequest(id)).map(data -> {
            this.id = id;
            this.value = data.value();
            this.invoiced = data.invoiced();
            this.paid = data.paid();
            this.pending = data.value() - data.paid();
            return this;
        });
    }

    @SneakyThrows
    @Override
    public void consume(Map<String, Object> context, ServerHttpRequest serverHttpRequest) {
        var id = (String) context.getOrDefault("id", "");
        load(id).toFuture().get();
    }
}
