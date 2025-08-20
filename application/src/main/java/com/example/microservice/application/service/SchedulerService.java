package com.example.microservice.application.service;

import com.example.microservice.application.dto.NewSchedulerDTO;
import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.port.in.AddTasksToSchedulerUseCase;
import com.example.microservice.application.port.in.GetActualSchedulerByWorkerUseCase;
import com.example.microservice.application.port.in.RegisterNewSchedulerUseCase;
import com.example.microservice.application.port.out.SchedulerRepository;
import com.example.microservice.application.port.out.TaskRepository;
import com.example.microservice.application.port.out.WorkerRepository;
import com.example.microservice.domain.model.Scheduler;
import com.example.microservice.domain.service.SchedulerDomainService;
import lombok.NonNull;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class SchedulerService extends SchedulerDomainService<SchedulerRepository>
        implements AddTasksToSchedulerUseCase, GetActualSchedulerByWorkerUseCase, RegisterNewSchedulerUseCase {

    private final TaskRepository taskRepository;

    private final WorkerRepository workerRepository;

    private final SchedulerMapper schedulerMapper;

    public SchedulerService(@NonNull SchedulerRepository schedulerRepository,
                            @NonNull TaskRepository taskRepository,
                            @NonNull WorkerRepository workerRepository,
                            @NonNull SchedulerMapper schedulerMapper) {
        super(schedulerRepository);
        this.taskRepository = taskRepository;
        this.workerRepository = workerRepository;
        this.schedulerMapper = schedulerMapper;
    }

    @Override
    public void addTasksToScheduler(@NonNull UUID taskCode, @NonNull UUID schedulerCode) {
        final var scheduler = findByCode(schedulerCode)
                .orElseThrow(() -> new NoSuchElementException("Scheduler not found"));
        final var task = taskRepository.findByCode(taskCode)
                .orElseThrow(() -> new NoSuchElementException("Task not found"));
        scheduler.addTask(task);
        save(scheduler);
    }

    @Override
    public Optional<Scheduler> getActualSchedulerByWorker(@NonNull UUID workerCode) {
        return repository.findActualSchedulerByWorker(workerCode);
    }

    @Override
    public void registerNewScheduler(@NonNull NewSchedulerDTO newScheduler) {
        final var worker = workerRepository.findByCode(UUID.fromString(newScheduler.worker()))
                .orElseThrow(() -> new NoSuchElementException("Worker not found"));
        save(schedulerMapper.toDomain(newScheduler.date(), worker));
    }
}
