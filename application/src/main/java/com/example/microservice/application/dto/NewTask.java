package com.example.microservice.application.dto;

import com.example.microservice.domain.model.Priority;
import lombok.Builder;
import lombok.Getter;

import java.time.Duration;

@Getter
@Builder
public class NewTask {

    private final String description;

    private final Priority priority;

    private final Duration duration;
}
