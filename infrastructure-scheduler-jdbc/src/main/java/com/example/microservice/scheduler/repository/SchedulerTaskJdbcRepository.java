package com.example.microservice.scheduler.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SchedulerTaskJdbcRepository {

    private final NamedParameterJdbcTemplate template;

    private final SchedulerTaskRowMapper rowMapper;

    public List<UUID> getTaskCodesByScheduler(UUID scheduler) {
        final var parameters = Map.of(SchedulerTaskJdbcQueries.SCHEDULER, scheduler);
        return template.query(SchedulerTaskJdbcQueries.FIND_TASK_CODES_BY_SCHEDULER, parameters, rowMapper);
    }

    public void insert(@NonNull UUID scheduler, @NonNull List<UUID> tasks) {
        final var parameters = getParameters(scheduler, tasks);
        template.batchUpdate(SchedulerTaskJdbcQueries.INSERT, parameters);
    }

    public void delete(@NonNull UUID scheduler, @NonNull List<UUID> tasks) {
        final var parameters = getParameters(scheduler, tasks);
        template.batchUpdate(SchedulerTaskJdbcQueries.DELETE, parameters);
    }

    private static Map<String, UUID>[] getParameters(@NonNull UUID scheduler, @NonNull List<UUID> tasks) {
        return tasks.stream()
                .map(task -> getParameters(scheduler, task))
                .<Map<String, UUID>>toArray(Map[]::new);
    }

    private static Map<String, UUID> getParameters(@NonNull UUID scheduler, @NonNull UUID task) {
        return Map.of(SchedulerTaskJdbcQueries.SCHEDULER, scheduler, SchedulerTaskJdbcQueries.TASK, task);
    }
}
