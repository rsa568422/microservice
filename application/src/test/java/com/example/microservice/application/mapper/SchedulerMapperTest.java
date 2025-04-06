package com.example.microservice.application.mapper;

import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import com.example.microservice.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SchedulerMapperTest {

    private final SchedulerMapper schedulerMapper = Mappers.getMapper(SchedulerMapper.class);

    @Test
    void testToDailyScheduler() {
        // given
        final var date = LocalDate.of(2025, Month.JANUARY, 1);

        // when
        final var actual = schedulerMapper.toDailyScheduler(date);

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(date, actual.getDate()),
                () -> assertTrue(actual.isEmpty()),
                () -> assertTrue(actual.getTasks().isEmpty())
        );
    }

    @Test
    void testToDailySchedulerWithTasks() {
        // given
        final var date = LocalDate.of(2025, Month.JANUARY, 1);
        final var task = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING)
                .build();

        // when
        final var actual = schedulerMapper.toDailyScheduler(date, Set.of(task));

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(date, actual.getDate()),
                () -> assertFalse(actual.isEmpty()),
                () -> assertFalse(actual.isFull()),
                () -> assertEquals(1, actual.getTasks().size()),
                () -> assertEquals(Status.WORKING, task.getStatus())
        );
    }

    @Test
    void testToMonthlyScheduler() {
        // given
        final var date = LocalDate.of(2025, Month.JANUARY, 1);

        // when
        final var actual = schedulerMapper.toMonthlyScheduler(date);

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(Year.of(date.getYear()), actual.getYear()),
                () -> assertEquals(date.getMonth(), actual.getMonth())
        );
    }
}