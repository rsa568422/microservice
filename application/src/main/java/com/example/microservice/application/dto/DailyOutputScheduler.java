package com.example.microservice.application.dto;

import lombok.Builder;

@Builder
public record DailyOutputScheduler(String description, String duration) { }
