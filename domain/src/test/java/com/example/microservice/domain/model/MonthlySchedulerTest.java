package com.example.microservice.domain.model;

import com.example.microservice.domain.Data;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonthlySchedulerTest {

    @Test
    void testAdd() {
        // given
        final var scheduler = Data.builder().build();
        final var badDaily1 = DailyScheduler.builder()
                .date(LocalDate.of(2025, Month.JANUARY, 1))
                .build();
        final var badDaily2 = DailyScheduler.builder()
                .date(LocalDate.of(2024, Month.JANUARY, 1))
                .build();
        badDaily2.add(Data.TASK_1_1.build());
        final var badDaily3 = DailyScheduler.builder()
                .date(LocalDate.of(2025, Month.FEBRUARY, 1))
                .build();
        badDaily3.add(Data.TASK_1_1.build());
        final var badDaily4 = DailyScheduler.builder()
                .date(LocalDate.of(2025, Month.JANUARY, 4))
                .build();
        badDaily4.add(Data.TASK_1_1.build());

        // when
        assertDoesNotThrow(() -> scheduler.add(Data.DAILY_SCHEDULER_1));
        final var error1 = assertThrows(IllegalArgumentException.class, () -> scheduler.add(badDaily1));
        final var error2 = assertThrows(IllegalArgumentException.class, () -> scheduler.add(badDaily2));
        final var error3 = assertThrows(IllegalArgumentException.class, () -> scheduler.add(badDaily3));
        final var error4 = assertThrows(IllegalArgumentException.class, () -> scheduler.add(Data.DAILY_SCHEDULER_1));
        final var error5 = assertThrows(IllegalArgumentException.class, () -> scheduler.add(badDaily4));
        final var error6 = assertThrows(NullPointerException.class, () -> scheduler.add(null));

        // then

        assertAll(
                () -> {
                    final var daily = scheduler.get(LocalDate.of(2025, Month.JANUARY, 1));
                    assertTrue(daily.isPresent());
                    assertEquals(Data.DAILY_SCHEDULER_1, daily.get());
                },
                () -> assertEquals("Can not add daily scheduler without tasks", error1.getMessage()),
                () -> assertEquals("Daily scheduler year not equals to monthly scheduler year", error2.getMessage()),
                () -> assertEquals("Daily scheduler month not equals to monthly scheduler month", error3.getMessage()),
                () -> assertEquals("A schedule is already available for that day", error4.getMessage()),
                () -> assertEquals("Weekends are non-working days", error5.getMessage()),
                () -> assertEquals("scheduler is marked non-null but is null", error6.getMessage())
        );
    }

    @Test
    void testGet() {
        // given
        final var scheduler = Data.builder().build();
        scheduler.add(Data.DAILY_SCHEDULER_1);

        // when
        final var daily = scheduler.get(LocalDate.of(2025, Month.JANUARY, 1));

        // then
        assertTrue(daily.isPresent());
        assertEquals(Data.DAILY_SCHEDULER_1, daily.get());
    }

    @Test
    void testEquals() {
        // given
        final var scheduler = Data.builder().build();
        scheduler.add(Data.DAILY_SCHEDULER_1);

        // then
        assertAll(
                () -> assertEquals(Data.builder().build(), scheduler),
                () -> assertNotEquals(
                        MonthlyScheduler.builder().year(Year.of(2025)).month(Month.FEBRUARY).build(), scheduler
                ),
                () -> assertNotEquals(
                        MonthlyScheduler.builder().year(Year.of(2024)).month(Month.JANUARY).build(), scheduler
                ),
                () -> assertNotEquals(scheduler, 1)
        );
    }

    @Test
    void testHashCode() {
        // given
        final var scheduler = Data.builder().build();
        scheduler.add(Data.DAILY_SCHEDULER_1);

        // then
        assertAll(
                () -> assertEquals(Data.builder().build().hashCode(), scheduler.hashCode()),
                () -> assertNotEquals(
                        MonthlyScheduler.builder().year(Year.of(2025)).month(Month.FEBRUARY).build().hashCode(),
                        scheduler.hashCode()
                ),
                () -> assertNotEquals(
                        MonthlyScheduler.builder().year(Year.of(2024)).month(Month.JANUARY).build().hashCode(),
                        scheduler.hashCode()
                )
        );
    }

    @Test
    void testGetters() {
        // given
        final var scheduler = Data.builder().build();

        // then
        assertAll(
                () -> assertEquals(Year.of(2025), scheduler.getYear()),
                () -> assertEquals(Month.JANUARY, scheduler.getMonth())
        );
    }
}