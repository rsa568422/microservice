package com.example.microservice.application.mapper;

import com.example.microservice.application.dto.OutputMonthlyScheduler;
import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.model.MonthlyScheduler;
import com.example.microservice.domain.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface SchedulerMapper {

    TaskMapper TASK_MAPPER = Mappers.getMapper(TaskMapper.class);

    DailyScheduler toDailyScheduler(LocalDate date);

    @Mapping(target = "year", expression = "java(java.time.Year.from(date))")
    @Mapping(target = "month", expression = "java(java.time.Month.from(date))")
    MonthlyScheduler toMonthlyScheduler(LocalDate date);

    default OutputMonthlyScheduler toOutputMonthlyScheduler(MonthlyScheduler scheduler) {
        final var days = scheduler.getDays()
                .entrySet()
                .stream()
                .map(entry -> Map.entry(entry.getKey(), TASK_MAPPER.toOutputSet(entry.getValue().getTasks())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return OutputMonthlyScheduler.builder()
                .year(scheduler.getYear())
                .month(scheduler.getMonth())
                .days(days)
                .build();
    }

    default DailyScheduler toDailyScheduler(LocalDate date, Set<Task> tasks) {
        final var scheduler = toDailyScheduler(date);
        tasks.forEach(scheduler::add);
        return scheduler;
    }
}
