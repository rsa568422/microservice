package com.example.microservice.application.dto;

import com.example.microservice.domain.model.Status;
import lombok.Builder;

@Builder
public record OutputTask(String code, String description, String duration, Status status) { }
