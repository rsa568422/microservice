package com.example.microservice.mybatis.mapper;

import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.repository.TaskRepository;
import com.example.microservice.mybatis.entity.SchedulerEntity;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class SchedulerMyBatisMapper {

    private final TaskRepository taskRepository;

    public Optional<DailyScheduler> toModel(List<SchedulerEntity> schedulers) {
        if (Objects.isNull(schedulers) || schedulers.isEmpty()) {
            return Optional.empty();
        }
        final var first = schedulers.get(0);
        final var date = LocalDate.of(first.getYear(), first.getMonth(), first.getDay());
        final var scheduler = DailyScheduler.builder().date(date).build();
        schedulers.stream()
                .map(scheduledTask -> taskRepository.findByCode(scheduledTask.getTask().toString()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(scheduler::add);
        return Optional.of(scheduler);
    }

    public List<SchedulerEntity> toEntities(@NonNull DailyScheduler scheduler) {
        return scheduler.getTasks().stream().map(task -> toEntity(scheduler.getDate(), task.getCode())).toList();
    }

    private static SchedulerEntity toEntity(LocalDate date, String code) {
        final var scheduler = new SchedulerEntity();
        scheduler.setYear(date.getYear());
        scheduler.setMonth(date.getMonthValue());
        scheduler.setDay(date.getDayOfMonth());
        scheduler.setTask(UUID.fromString(code));
        return scheduler;
    }
}
