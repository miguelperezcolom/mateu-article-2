package io.mateu.article2.shared.events.payloads;

import io.mateu.article2.shared.events.Payload;

public record BookingCreated(String bookingId) implements Payload {
}
