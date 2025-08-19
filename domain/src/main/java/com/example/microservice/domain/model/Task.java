package com.example.microservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.util.UUID;

@Getter
@Builder
public class Task {

    private final UUID code;

    private final String description;

    private final Priority priority;

    private final Duration duration;

    @Setter
    private Status status;
}
