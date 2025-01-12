package io.mateu.article2.model;

public record SearchRequest(String text, Pageable pageable) {
}
