package io.mateu.article2.financial.invoice.domain.invoice.valueobjects;

import java.math.BigDecimal;

public record InvoiceValue(BigDecimal value) {

    public InvoiceValue(BigDecimal value) {
        this.value = value.setScale(30, BigDecimal.ROUND_HALF_UP);
    }
}
