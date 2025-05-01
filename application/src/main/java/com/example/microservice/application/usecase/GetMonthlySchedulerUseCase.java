package com.example.microservice.application.usecase;

import com.example.microservice.application.dto.OutputMonthlyScheduler;
import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.service.SchedulerService;
import lombok.RequiredArgsConstructor;

import java.time.Month;
import java.time.Year;
import java.util.Optional;

@RequiredArgsConstructor
public class GetMonthlySchedulerUseCase {

    private final SchedulerService schedulerService;

    private final SchedulerMapper schedulerMapper;

    public Optional<OutputMonthlyScheduler> execute(int year, int month) {
        return schedulerService.findByYearAndMonth(Year.of(year), Month.of(month)).map(schedulerMapper::toOutputMonthlyScheduler);
    }
}
