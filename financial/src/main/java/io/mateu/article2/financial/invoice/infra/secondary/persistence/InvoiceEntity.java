package io.mateu.article2.financial.invoice.infra.secondary.persistence;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@AllArgsConstructor@NoArgsConstructor
public class InvoiceEntity {

    @Id
    String id;

    String bookingId;

    LocalDate date;

    String value;

    String searchableText;

}
