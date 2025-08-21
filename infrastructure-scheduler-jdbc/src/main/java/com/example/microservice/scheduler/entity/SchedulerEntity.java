package com.example.microservice.scheduler.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class SchedulerEntity {

    private UUID code;

    private LocalDate date;

    private UUID worker;
}
