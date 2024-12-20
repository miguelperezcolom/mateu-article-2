package io.mateu.article2.booking.application.findbooking;

import io.mateu.article2.booking.domain.booking.valueobjects.BookingId;

public record FindBookingRequest(BookingId bookingId) {
}
