package com.example.microservice.application.dto;

import lombok.Builder;

import java.time.Month;
import java.time.Year;
import java.util.Map;
import java.util.Set;

@Builder
public record OutputMonthlyScheduler(Year year, Month month, Map<Integer, Set<OutputTask>> days) { }
