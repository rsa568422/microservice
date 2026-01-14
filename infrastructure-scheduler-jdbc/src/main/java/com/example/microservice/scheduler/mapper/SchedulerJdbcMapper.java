package com.example.microservice.scheduler.mapper;

import com.example.microservice.application.service.TaskService;
import com.example.microservice.application.service.WorkerService;
import com.example.microservice.domain.model.Scheduler;
import com.example.microservice.domain.model.Task;
import com.example.microservice.domain.model.Worker;
import com.example.microservice.scheduler.entity.SchedulerEntity;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class SchedulerJdbcMapper {

    @Autowired
    private TaskService taskService;

    @Autowired
    private WorkerService workerService;

    @Mapping(target = "worker", source = "worker", qualifiedByName = "uuidToWorker")
    public abstract Scheduler toModel(SchedulerEntity schedulerEntity);

    public Scheduler merge(@NonNull Scheduler scheduler, @NonNull List<UUID> tasks) {
        tasks.stream().map(this::uuidToTask).forEach(scheduler::addTask);
        return scheduler;
    }

    @Named("uuidToWorker")
    protected Worker uuidToWorker(@NonNull UUID workerCode) {
        return workerService.findByCode(workerCode)
                .orElseThrow(() -> new NoSuchElementException("Worker %s not found".formatted(workerCode)));
    }

    private Task uuidToTask(@NonNull UUID taskCode) {
        return taskService.findByCode(taskCode)
                .orElseThrow(() -> new NoSuchElementException("Task %s not found".formatted(taskCode)));
    }
}
