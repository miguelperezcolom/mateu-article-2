package io.mateu.article2.booking.domain.booking;

import io.mateu.article2.booking.domain.booking.valueobjects.*;
import io.mateu.article2.shared.events.DomainEvent;

public record BookingState(
        BookingId id,
        CustomerName customerName,
        ServiceDescription serviceDescription,
        BookingStartDate startDate,
        BookingEndDate endDate,
        BookingValue value,
        BookingStatus status
) {
}
