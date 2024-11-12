package io.mateu.article2.financial.invoice.infra.secondary.persistence;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor@NoArgsConstructor
public class InvoiceEntity {

    @Id
    String id;

    String bookingId;

    LocalDate date;

    String value;

    String searchableText;

}
