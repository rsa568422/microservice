package com.example.microservice.application.usecase;

import com.example.microservice.application.service.SchedulerService;
import com.example.microservice.domain.model.DailyScheduler;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
public class GetDailySchedulerUseCase {

    private final SchedulerService schedulerService;

    public Optional<DailyScheduler> execute(@NonNull LocalDate date) {
        return schedulerService.findByDate(date);
    }
}
