package com.example.microservice.domain.repository;

import com.example.microservice.domain.model.MonthlyScheduler;
import lombok.NonNull;

import java.time.Year;
import java.util.Set;

public interface MonthlySchedulerRepository {

    Set<MonthlyScheduler> findByYear(@NonNull Year year);

    void save(@NonNull MonthlyScheduler scheduler);
}
