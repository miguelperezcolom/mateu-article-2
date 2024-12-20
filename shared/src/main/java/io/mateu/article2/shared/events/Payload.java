package io.mateu.article2.shared.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.mateu.article2.shared.events.payloads.BookingCreated;
import io.mateu.article2.shared.events.payloads.BookingUpdated;
import io.mateu.article2.shared.events.payloads.InvoiceCreated;
import io.mateu.article2.shared.events.payloads.PaymentCreated;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "payloadType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BookingCreated.class, name = "BookingCreated"),
        @JsonSubTypes.Type(value = BookingUpdated.class, name = "BookingUpdated"),
        @JsonSubTypes.Type(value = InvoiceCreated.class, name = "InvoiceCreated"),
        @JsonSubTypes.Type(value = PaymentCreated.class, name = "PaymentCreated"),
})
public interface Payload {
}
