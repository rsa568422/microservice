package com.example.microservice.domain.repository;

import com.example.microservice.domain.model.DailyScheduler;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.Optional;

public interface DailySchedulerRepository {

    Optional<DailyScheduler> findByDate(@NonNull LocalDate date);

    void save(@NonNull DailyScheduler scheduler);
}
