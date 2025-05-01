package com.example.microservice.domain.repository;

import com.example.microservice.domain.model.MonthlyScheduler;
import lombok.NonNull;

import java.time.Month;
import java.time.Year;
import java.util.Optional;

public interface MonthlySchedulerRepository {

    Optional<MonthlyScheduler> findByYearAndMonth(@NonNull Year year, @NonNull Month month);
}
