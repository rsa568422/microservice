package com.example.microservice.mybatis.mapper;

import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.repository.TaskRepository;
import com.example.microservice.mybatis.entity.SchedulerEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SchedulerMyBatisMapper {

    private final TaskRepository taskRepository;

    public Optional<DailyScheduler> toModel(List<SchedulerEntity> schedulers) {
        if (Objects.isNull(schedulers) || schedulers.isEmpty()) {
            return Optional.empty();
        }
        final var first = schedulers.get(0);
        return toModel(first.getYear(), Month.of(first.getMonth()), first.getDay(), schedulers);
    }

    public Optional<DailyScheduler> toModel(@NonNull Integer year, @NonNull Month month, @NonNull Integer day,
                                            @NonNull List<SchedulerEntity> schedulers) {
        final var scheduler = DailyScheduler.builder().date(LocalDate.of(year, month, day)).build();
        schedulers.stream()
                .map(scheduledTask -> taskRepository.findByCode(scheduledTask.getTask()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(scheduler::add);
        return Optional.of(scheduler);
    }

    public List<SchedulerEntity> toEntities(@NonNull DailyScheduler scheduler) {
        return scheduler.getTasks().stream().map(task -> toEntity(scheduler.getDate(), task.getCode())).toList();
    }

    public static SchedulerEntity toEntity(LocalDate date, String code) {
        final var scheduler = new SchedulerEntity();
        scheduler.setYear(date.getYear());
        scheduler.setMonth(date.getMonthValue());
        scheduler.setDay(date.getDayOfMonth());
        scheduler.setTask(code);
        return scheduler;
    }

    public List<DailyScheduler> toModel(@NonNull Year year, @NonNull Month month, Map<Integer, List<SchedulerEntity>> map) {
        return map.entrySet()
                .stream()
                .map(entry -> toModel(year.getValue(), month, entry.getKey(), entry.getValue()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
