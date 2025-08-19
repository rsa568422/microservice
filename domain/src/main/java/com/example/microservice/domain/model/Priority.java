package com.example.microservice.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Priority {
    CRITICAL    (1),
    HIGH        (2),
    MEDIUM      (3),
    LOW         (4);

    private final Integer value;

    public static Priority fromValue(Integer value) {
        return Arrays.stream(Priority.values())
                .filter(priority -> priority.getValue().equals(value))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid priority value: %s".formatted(value)));
    }
}
