package com.example.microservice.application.mapper;

import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.model.MonthlyScheduler;
import com.example.microservice.domain.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.Set;

@Mapper
public interface SchedulerMapper {

    DailyScheduler toDailyScheduler(LocalDate date);

    @Mapping(target = "year", expression = "java(java.time.Year.from(date))")
    @Mapping(target = "month", expression = "java(java.time.Month.from(date))")
    MonthlyScheduler toMonthlyScheduler(LocalDate date);

    default DailyScheduler toDailyScheduler(LocalDate date, Set<Task> tasks) {
        final var scheduler = toDailyScheduler(date);
        tasks.forEach(scheduler::add);
        return scheduler;
    }
}
