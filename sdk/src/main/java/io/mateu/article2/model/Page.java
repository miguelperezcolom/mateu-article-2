package io.mateu.article2.model;

import java.util.List;

public record Page<T>(
        List<T> content,
        int totalElements
) {

}
