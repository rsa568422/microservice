package com.example.microservice.scheduler.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class SchedulerTaskJdbcQueries {

    static final String TASK = "task";

    static final String SCHEDULER = "scheduler";

    static final String FIND_TASK_CODES_BY_SCHEDULER =
            "SELECT task_code AS task FROM scheduler_tasks WHERE scheduler_code = :%s".formatted(SCHEDULER);

    static  final String INSERT =
            "INSERT INTO scheduler_tasks (scheduler_code, task_code) VALUES (:%s, :%s)".formatted(SCHEDULER, TASK);

    static final String DELETE =
            "DELETE FROM scheduler_tasks WHERE scheduler_code = :%s AND task_code = :%s".formatted(SCHEDULER, TASK);
}
