package com.example.microservice.application.dto;

import lombok.NonNull;

public record NewWorkerDTO(@NonNull String name, @NonNull String surname) {
}
