package com.example.microservice.application.service;

import com.example.microservice.application.dto.NewWorkerDTO;
import com.example.microservice.application.mapper.WorkerMapper;
import com.example.microservice.application.port.in.GetAvailableWorkersUseCase;
import com.example.microservice.application.port.in.RegisterNewWorkerUseCase;
import com.example.microservice.application.port.out.WorkerRepository;
import com.example.microservice.domain.model.Worker;
import com.example.microservice.domain.service.WorkerDomainService;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

public class WorkerService extends WorkerDomainService<WorkerRepository>
        implements RegisterNewWorkerUseCase, GetAvailableWorkersUseCase {

    private final WorkerMapper workerMapper;

    public WorkerService(@NonNull WorkerRepository workerRepository,
                         @NonNull WorkerMapper workerMapper) {
        super(workerRepository);
        this.workerMapper = workerMapper;
    }

    @Override
    public void registerNewWorker(@NonNull NewWorkerDTO newWorker) {
        save(workerMapper.toDomain(newWorker));
    }

    @Override
    public List<Worker> getAvailableWorkers(@NonNull LocalDate date) {
        return repository.findAvailableWorkers(date);
    }
}
