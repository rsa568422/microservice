package com.example.microservice.mybatis.adapter;

import com.example.microservice.domain.model.MonthlyScheduler;
import com.example.microservice.domain.repository.MonthlySchedulerRepository;
import com.example.microservice.mybatis.entity.SchedulerEntity;
import com.example.microservice.mybatis.mapper.SchedulerMyBatisMapper;
import com.example.microservice.mybatis.repository.SchedulerMyBatisRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MonthlySchedulerRepositoryAdapter implements MonthlySchedulerRepository {

    private final SchedulerMyBatisRepository repository;

    private final SchedulerMyBatisMapper mapper;

    @Override
    public Optional<MonthlyScheduler> findByYearAndMonth(@NonNull Year year, @NonNull Month month) {
        final var schedulersByDay = repository.findByYearAndMonth(year.getValue(), month.getValue())
                .stream()
                .collect(Collectors.groupingBy(SchedulerEntity::getDay));
        final var scheduler = MonthlyScheduler.builder().year(year).month(month).build();
        mapper.toModel(year, month, schedulersByDay).forEach(scheduler::add);
        return Optional.of(scheduler);
    }
}
