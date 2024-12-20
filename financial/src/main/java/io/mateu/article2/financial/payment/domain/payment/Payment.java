package io.mateu.article2.financial.payment.domain.payment;

import io.mateu.article2.shared.domain.AggregateRoot;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class Payment extends AggregateRoot {

    @Getter
    @Setter(AccessLevel.PROTECTED)
    private PaymentState state;



}
