package io.mateu.article2.booking.infra.secondary.persistence.booking;

import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;

@Document
@AllArgsConstructor
@NoArgsConstructor
public class BookingEntity {

    @Id
    String id;

    String customerName;

    String serviceDescription;

    @Temporal(TemporalType.DATE)
    LocalDate serviceStartDate;

    @Temporal(TemporalType.DATE)
    LocalDate serviceEndDate;

    BigDecimal value;

    String status;

    String searchableText;
}
