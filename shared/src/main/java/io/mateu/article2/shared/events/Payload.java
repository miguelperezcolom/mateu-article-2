package io.mateu.article2.shared.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.mateu.article2.shared.events.payloads.BookingCreated;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "payloadType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BookingCreated.class, name = "BookingCreated"),
})
public interface Payload {
}
