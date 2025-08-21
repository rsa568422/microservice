package com.example.microservice.application.dto;

import com.example.microservice.domain.model.Priority;
import lombok.NonNull;

import java.time.Duration;

public record NewTaskDTO(@NonNull String description, @NonNull Priority priority, @NonNull Duration duration) {
}
