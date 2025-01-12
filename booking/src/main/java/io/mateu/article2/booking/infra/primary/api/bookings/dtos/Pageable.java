package io.mateu.article2.booking.infra.primary.api.bookings.dtos;

public record Pageable(int page, int size, Sort sort) {
}
