package com.example.microservice.application.dto;

import lombok.NonNull;

import java.time.LocalDate;

public record NewSchedulerDTO(@NonNull LocalDate date, @NonNull String workerCode) {
}
