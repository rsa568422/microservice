package com.example.microservice.application.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record NewDailyScheduler(LocalDate date, Set<String> taskCodes) { }
