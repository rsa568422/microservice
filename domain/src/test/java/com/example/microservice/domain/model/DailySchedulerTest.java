package com.example.microservice.domain.model;

import com.example.microservice.domain.Data;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DailySchedulerTest {

    @Test
    void testGetTasks() {
        assertIterableEquals(Data.TASKS_1(), Data.DAILY_SCHEDULER_1.getTasks());
    }

    @Test
    void testAdd() {
        // given
        final var scheduler = DailyScheduler.builder()
                .date(LocalDate.of(2025, Month.JANUARY, 1))
                .build();
        final var task = Data.TASK_1_1.build();
        final var taskB = Data.TASK_1_1.build();
        final var badTask1 = Task.builder()
                .code("CODE#BAD1")
                .description("description of bad task 1")
                .priority(Priority.CRITICAL)
                .duration(Duration.ofHours(4))
                .status(Status.WORKING)
                .build();
        final var badTask2 = Task.builder()
                .code("CODE#BAD2")
                .description("description of bad task 2")
                .priority(Priority.CRITICAL)
                .duration(Duration.ofHours(9))
                .status(Status.PENDING)
                .build();

        // when
        assertDoesNotThrow(() -> scheduler.add(task));
        final var error1 = assertThrows(IllegalArgumentException.class, () -> scheduler.add(badTask1));
        final var error2 = assertThrows(IllegalArgumentException.class, () -> scheduler.add(badTask2));
        final var error3 = assertThrows(IllegalArgumentException.class, () -> {
            scheduler.add(taskB);
            taskB.setStatus(Status.PENDING);
            scheduler.add(taskB);
        });
        final var error4 = assertThrows(RuntimeException.class, () -> scheduler.add(null));

        // then
        assertAll(
                () -> assertEquals(1, scheduler.getTasks().size()),
                () -> assertTrue(scheduler.getTasks().contains(Data.TASK_1_1.build())),
                () -> assertEquals(Status.WORKING, task.getStatus()),
                () -> assertEquals("Only pending tasks can be added to the scheduler", error1.getMessage()),
                () -> assertEquals("Total planning exceeds eight hours", error2.getMessage()),
                () -> assertEquals("The task is already added to de scheduler", error3.getMessage()),
                () -> assertEquals("task is marked non-null but is null", error4.getMessage())
        );
    }

    @Test
    void testIsEmpty() {
        assertAll(
                () -> assertTrue(
                        DailyScheduler.builder().date(LocalDate.of(2025, Month.JANUARY, 1)).build().isEmpty()
                ),
                () -> assertFalse(Data.DAILY_SCHEDULER_1.isEmpty())
        );
    }

    @Test
    void testIsFull() {
        assertAll(
                () -> assertFalse(
                        DailyScheduler.builder().date(LocalDate.of(2025, Month.JANUARY, 1)).build().isFull()
                ),
                () -> assertTrue(Data.DAILY_SCHEDULER_1.isFull())
        );
        assertTrue(Data.DAILY_SCHEDULER_1.isFull());
    }

    @Test
    void testEquals() {
        assertAll(
                () -> assertEquals(
                        Data.DAILY_SCHEDULER_1,
                        DailyScheduler.builder().date(LocalDate.of(2025, Month.JANUARY, 1)).build()
                ),
                () -> assertNotEquals(
                        Data.DAILY_SCHEDULER_1,
                        DailyScheduler.builder().date(LocalDate.of(2025, Month.FEBRUARY, 1)).build()
                ),
                () -> assertNotEquals(Data.DAILY_SCHEDULER_1, 1)
        );
    }

    @Test
    void testHashCode() {
        assertAll(
                () -> assertEquals(
                        Data.DAILY_SCHEDULER_1.hashCode(),
                        DailyScheduler.builder().date(LocalDate.of(2025, Month.JANUARY, 1)).build().hashCode()
                ),
                () -> assertNotEquals(
                        Data.DAILY_SCHEDULER_1.hashCode(),
                        DailyScheduler.builder().date(LocalDate.of(2025, Month.FEBRUARY, 1)).build().hashCode()
                )
        );
    }

    @Test
    void testGetDay() {
        assertEquals(LocalDate.of(2025, Month.JANUARY, 1), Data.DAILY_SCHEDULER_1.getDate());
    }
}