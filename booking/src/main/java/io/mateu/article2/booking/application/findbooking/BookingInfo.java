package io.mateu.article2.booking.application.findbooking;

import io.mateu.article2.booking.domain.booking.valueobjects.BookingStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingInfo(String id,
                          String leadName,
                          String service,
                          LocalDate serviceStartDate,
                          LocalDate serviceEndDate,
                          BigDecimal value,
                          BookingStatus status) {
}