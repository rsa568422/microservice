package com.example.microservice.task.adapter;

import com.example.microservice.application.port.out.WorkerRepository;
import com.example.microservice.domain.model.Worker;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WorkerRepositoryAdapter implements WorkerRepository {
    // TODO
    @Override
    public List<Worker> findAvailableWorkers(@NonNull LocalDate date) {
        return List.of();
    }

    @Override
    public List<Worker> findAll() {
        return List.of();
    }

    @Override
    public Optional<Worker> findByCode(@NonNull UUID code) {
        return Optional.empty();
    }

    @Override
    public void save(@NonNull Worker model) {

    }
}
