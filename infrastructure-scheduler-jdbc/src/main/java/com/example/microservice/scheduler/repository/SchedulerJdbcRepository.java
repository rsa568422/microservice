package com.example.microservice.scheduler.repository;

import com.example.microservice.scheduler.entity.SchedulerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class SchedulerJdbcRepository {

    private final NamedParameterJdbcTemplate template;

    private final SchedulerRowMapper rowMapper;

    public List<SchedulerEntity> findAll() {
        return template.query(SchedulerJdbcQueries.FIND_ALL, rowMapper);
    }

    public Optional<SchedulerEntity> findByCode(UUID code) {
        final var parameters = Map.of(SchedulerJdbcQueries.CODE, code);
        final var scheduler = template.queryForObject(SchedulerJdbcQueries.FIND_BY_CODE, parameters, rowMapper);
        return Optional.ofNullable(scheduler);
    }

    public Optional<SchedulerEntity> findSchedulerByDateAndWorker(LocalDate date, UUID worker) {
        final var parameters = Map.of(
                SchedulerJdbcQueries.DATE, date,
                SchedulerJdbcQueries.WORKER, worker
        );
        final var scheduler = template
                .queryForObject(SchedulerJdbcQueries.FIND_SCHEDULER_BY_DATE_AND_WORKER, parameters, rowMapper);
        return Optional.ofNullable(scheduler);
    }

    public void insert(LocalDate date, UUID worker) {
        final var parameters = Map.of(
                SchedulerJdbcQueries.DATE, date,
                SchedulerJdbcQueries.WORKER, worker
        );
        template.update(SchedulerJdbcQueries.INSERT, parameters);
    }
}
