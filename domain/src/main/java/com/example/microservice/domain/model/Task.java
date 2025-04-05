package com.example.microservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;

@Getter
@Builder
public class Task {

    private final String code;

    private final String description;

    private final Priority priority;

    private final Duration duration;

    @Setter
    private Status status;

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return code.equals(task.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }
}
