package com.example.microservice.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Locale;

@Getter
@RequiredArgsConstructor
public enum Status {
    PENDING ("P"),
    WORKING ("W");

    private final String value;

    public static Status fromValue(String value) {
        return Arrays.stream(Status.values())
                .filter(status -> status.getValue().equals(value.toUpperCase(Locale.ROOT).trim()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status value: %s".formatted(value)));
    }
}
