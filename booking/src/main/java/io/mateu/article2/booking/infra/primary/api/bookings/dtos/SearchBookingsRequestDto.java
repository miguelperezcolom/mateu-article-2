package io.mateu.article2.booking.infra.primary.api.bookings.dtos;



public record SearchBookingsRequestDto(String text, Pageable pageable) {
}
