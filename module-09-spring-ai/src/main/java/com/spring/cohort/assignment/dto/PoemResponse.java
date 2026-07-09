package com.spring.cohort.assignment.dto;

public record PoemResponse(
        String title,
        String poem,
        String rhymeScheme
) {
}
