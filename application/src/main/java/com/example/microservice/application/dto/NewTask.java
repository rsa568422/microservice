package com.example.microservice.application.dto;

import com.example.microservice.domain.model.Priority;
import lombok.Builder;

@Builder
public record NewTask(String description, Priority priority, Integer duration) { }
