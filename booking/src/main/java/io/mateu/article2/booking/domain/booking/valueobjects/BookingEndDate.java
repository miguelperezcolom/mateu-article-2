package io.mateu.article2.booking.domain.booking.valueobjects;

import java.time.LocalDate;

public record BookingEndDate(LocalDate date) implements Date {
}
