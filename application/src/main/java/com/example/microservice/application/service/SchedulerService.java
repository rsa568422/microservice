package com.example.microservice.application.service;

import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.model.MonthlyScheduler;
import com.example.microservice.domain.repository.DailySchedulerRepository;
import com.example.microservice.domain.repository.MonthlySchedulerRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Optional;

@RequiredArgsConstructor
public class SchedulerService {

    private final DailySchedulerRepository dailySchedulerRepository;

    private final MonthlySchedulerRepository monthlySchedulerRepository;

    public Optional<DailyScheduler> findByDate(@NonNull LocalDate date) {
        return dailySchedulerRepository.findByDate(date);
    }

    public Optional<MonthlyScheduler> findByYearAndMont(@NonNull Year year, @NonNull Month month) {
        return monthlySchedulerRepository.findByYear(year)
                .stream()
                .filter(monthly -> month.equals(monthly.getMonth()))
                .findAny();
    }

    public void save(@NonNull DailyScheduler scheduler) {
        dailySchedulerRepository.save(scheduler);
    }

    public void save(@NonNull MonthlyScheduler scheduler) {
        monthlySchedulerRepository.save(scheduler);
    }
}
