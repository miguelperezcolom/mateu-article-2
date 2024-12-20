package io.mateu.article2.financial.shared.infra.primary.ui;

import io.mateu.article2.financial.bookingreport.infra.primary.ui.BookingReport;
import io.mateu.article2.financial.invoice.infra.primary.ui.InvoiceCrud;
import io.mateu.article2.financial.payment.infra.primary.ui.PaymentCrud;
import io.mateu.uidl.annotations.MateuUI;
import io.mateu.uidl.annotations.MenuOption;
import io.mateu.uidl.annotations.Submenu;

class FinancialMenu {

    @MenuOption
    InvoiceCrud invoices;

    @MenuOption
    PaymentCrud payments;

    @MenuOption(visible = false)
    BookingReport bookingReport;

}

@MateuUI("/financial")
public class FinancialUI {

    @Submenu
    FinancialMenu financial;

}
