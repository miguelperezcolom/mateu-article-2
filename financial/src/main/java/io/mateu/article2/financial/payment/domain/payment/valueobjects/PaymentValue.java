package io.mateu.article2.financial.payment.domain.payment.valueobjects;

import java.math.BigDecimal;

public record PaymentValue(BigDecimal value) {

    public PaymentValue(BigDecimal value) {
        this.value = value.setScale(30, BigDecimal.ROUND_HALF_UP);
    }
}
