package io.mateu.article2.model;

import java.math.BigDecimal;

public record BookingListRow(String id,
                             String customer,
                             String startDate,
                             String endDate,
                             String serviceDescription,
                             BookingStatus status,
                             BigDecimal value,
                             BigDecimal invoiced,
                             BigDecimal paid) {
}
