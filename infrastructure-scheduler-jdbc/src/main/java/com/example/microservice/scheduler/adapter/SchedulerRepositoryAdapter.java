package com.example.microservice.scheduler.adapter;

import com.example.microservice.application.port.out.SchedulerRepository;
import com.example.microservice.domain.model.Scheduler;
import com.example.microservice.domain.model.Task;
import com.example.microservice.scheduler.entity.SchedulerEntity;
import com.example.microservice.scheduler.mapper.SchedulerJdbcMapper;
import com.example.microservice.scheduler.repository.SchedulerJdbcRepository;
import com.example.microservice.scheduler.repository.SchedulerTaskJdbcRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SchedulerRepositoryAdapter implements SchedulerRepository {

    private final SchedulerJdbcRepository schedulerJdbcRepository;

    private final SchedulerTaskJdbcRepository schedulerTaskJdbcRepository;

    private final SchedulerJdbcMapper schedulerJdbcMapper;

    @Override
    public List<Scheduler> findAll() {
        return schedulerJdbcRepository.findAll().stream().map(this::toModel).toList();
    }

    @Override
    public Optional<Scheduler> findByCode(@NonNull UUID code) {
        return schedulerJdbcRepository.findByCode(code).map(this::toModel);
    }

    @Override
    public Optional<Scheduler> findActualSchedulerByWorker(@NonNull UUID worker) {
        return schedulerJdbcRepository.findSchedulerByDateAndWorker(LocalDate.now(), worker).map(this::toModel);
    }

    @Override
    public void save(@NonNull Scheduler scheduler) {
        if (Objects.isNull(scheduler.getCode())) {
            insert(scheduler);
        } else {
            final var tasks = scheduler.getTasks().stream().map(Task::getCode).distinct().toList();
            update(scheduler.getCode(), tasks);
        }
    }

    private void insert(@NonNull Scheduler scheduler) {
        final var date = scheduler.getDate();
        final var worker = scheduler.getWorker().getCode();
        schedulerJdbcRepository.insert(date, worker);
        final var schedulerEntity = schedulerJdbcRepository.findSchedulerByDateAndWorker(date, worker)
                .orElseThrow(() -> new IllegalStateException("Scheduler not found"));
        final var tasks = scheduler.getTasks().stream().map(Task::getCode).distinct().toList();
        schedulerTaskJdbcRepository.insert(schedulerEntity.getCode(), tasks);
    }

    private void update(@NonNull UUID scheduler, @NonNull List<UUID> tasks) {
        schedulerJdbcRepository.findByCode(scheduler)
                .orElseThrow(() -> new IllegalStateException("Scheduler not found"));
        final var storedTasks = schedulerTaskJdbcRepository.getTaskCodesByScheduler(scheduler);
        final var newTasks = new LinkedList<>(tasks);
        final var deletedTasks = new LinkedList<>(storedTasks);
        newTasks.removeAll(storedTasks);
        deletedTasks.removeAll(tasks);
        schedulerTaskJdbcRepository.insert(scheduler, newTasks);
        schedulerTaskJdbcRepository.delete(scheduler, deletedTasks);
    }

    private Scheduler toModel(@NonNull SchedulerEntity schedulerEntity) {
        final var scheduler = schedulerJdbcMapper.toModel(schedulerEntity);
        final var taskCodes = schedulerTaskJdbcRepository.getTaskCodesByScheduler(scheduler.getCode());
        return schedulerJdbcMapper.merge(scheduler, taskCodes);
    }
}
