package io.mateu.article2.booking.infra.secondary.persistence.event;

import io.mateu.article2.shared.events.Payload;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EventEntity {

    @Id
    String id;

    String source;

    @Temporal(TemporalType.DATE)
    LocalDateTime when;

    Payload payload;

    EventProcessingStatus processingStatus;

}
