package com.example.microservice.application.dto;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record TaskToAdd(String code, LocalDate date) { }
