package io.mateu.article2.booking.domain.booking.valueobjects;

import java.time.LocalDate;

public record BookingStartDate(LocalDate date) implements Date {
}
