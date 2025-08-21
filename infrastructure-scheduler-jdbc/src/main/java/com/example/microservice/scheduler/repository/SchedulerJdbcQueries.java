package com.example.microservice.scheduler.repository;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class SchedulerJdbcQueries {

    static final String CODE = "code";

    static final String DATE = "date";

    static final String WORKER = "worker";

    static final String FIND_ALL = "SELECT code, date, worker FROM schedulers";

    static final String FIND_BY_CODE = "SELECT code, date, worker FROM schedulers WHERE code = :%s".formatted(CODE);

    static final String FIND_SCHEDULER_BY_DATE_AND_WORKER =
            "SELECT code, date, worker FROM schedulers WHERE date = :%s AND worker = :%s".formatted(DATE, WORKER);

    static final String INSERT = "INSERT INTO schedulers (date, worker) VALUES (:%s, :%s)".formatted(DATE, WORKER);
}
