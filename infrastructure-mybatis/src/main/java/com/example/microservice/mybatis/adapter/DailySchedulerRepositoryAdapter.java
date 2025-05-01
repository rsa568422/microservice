package com.example.microservice.mybatis.adapter;

import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.repository.DailySchedulerRepository;
import com.example.microservice.mybatis.entity.SchedulerEntity;
import com.example.microservice.mybatis.mapper.SchedulerMyBatisMapper;
import com.example.microservice.mybatis.repository.SchedulerMyBatisRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DailySchedulerRepositoryAdapter implements DailySchedulerRepository {

    private final SchedulerMyBatisRepository repository;

    private final SchedulerMyBatisMapper mapper;

    @Override
    public Optional<DailyScheduler> findByDate(@NonNull LocalDate date) {
        final var schedulers = repository.findByDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth());
        return mapper.toModel(schedulers);
    }

    @Override
    public void save(@NonNull DailyScheduler scheduler) {
        final var date = scheduler.getDate();
        final var tasks = repository.findByDate(date.getYear(), date.getMonthValue(), date.getDayOfMonth())
                .stream()
                .map(SchedulerEntity::getTask)
                .toList();
        scheduler.getTasks()
                .stream()
                .filter(task -> !tasks.contains(task.getCode()))
                .map(task -> SchedulerMyBatisMapper.toEntity(date, task.getCode()))
                .forEach(repository::save);
    }
}
