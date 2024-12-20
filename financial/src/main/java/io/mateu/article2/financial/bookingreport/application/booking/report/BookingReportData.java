package io.mateu.article2.financial.bookingreport.application.booking.report;

public record BookingReportData(
        double value,
        double invoiced,
        double paid
) {
}
