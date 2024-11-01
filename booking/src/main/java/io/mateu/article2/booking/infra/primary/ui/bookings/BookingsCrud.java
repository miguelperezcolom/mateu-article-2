package io.mateu.article2.booking.infra.primary.ui.bookings;

import java.time.LocalDate;

record BookingRow(String id, String customer, LocalDate startDate, LocalDate endDate, String status) {
}

record BookingFilters() {
}


public class BookingsCrud {
}
