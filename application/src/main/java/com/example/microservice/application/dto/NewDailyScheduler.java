package com.example.microservice.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Builder
public class NewDailyScheduler {

    private final LocalDate date;

    private final Set<String> taskCodes;
}
