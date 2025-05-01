package com.example.microservice.application.service;

import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.repository.DailySchedulerRepository;
import com.example.microservice.domain.repository.MonthlySchedulerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SchedulerServiceTest {

    @InjectMocks
    private SchedulerService schedulerService;

    @Mock
    private DailySchedulerRepository dailySchedulerRepository;

    @Mock
    private MonthlySchedulerRepository monthlySchedulerRepository;

    @Test
    void testFindByDate() {
        // given
        final var date = LocalDate.of(2025, Month.JANUARY, 1);
        final var daily = DailyScheduler.builder().date(date).build();

        when(dailySchedulerRepository.findByDate(date)).thenReturn(Optional.of(daily));

        // when
        final var actual = schedulerService.findByDate(date);

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(Optional.of(daily), actual)
        );

        verify(dailySchedulerRepository, times(1)).findByDate(date);
        verifyNoMoreInteractions(dailySchedulerRepository, monthlySchedulerRepository);
    }

    @Test
    void testFindByDateNull() {
        // when
        final var actual = assertThrows(RuntimeException.class, () -> schedulerService.findByDate(null));

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("date is marked non-null but is null", actual.getMessage())
        );
    }

    @Test
    void testFindByYearAndMonthNull() {
        // when
        final var error1 = assertThrows(
                RuntimeException.class,
                () -> schedulerService.findByYearAndMonth(null, Month.JANUARY)
        );
        final var error2 = assertThrows(
                RuntimeException.class,
                () -> schedulerService.findByYearAndMonth(Year.of(2025), null)
        );

        // then
        assertAll(
                () -> assertNotNull(error1),
                () -> assertNotNull(error2),
                () -> assertEquals("year is marked non-null but is null", error1.getMessage()),
                () -> assertEquals("month is marked non-null but is null", error2.getMessage())
        );
    }

    @Test
    void testSaveDaily() {
        // given
        final var date = LocalDate.of(2025, Month.JANUARY, 1);
        final var daily = DailyScheduler.builder().date(date).build();

        // when
        assertDoesNotThrow(() -> schedulerService.save(daily));

        // then
        verify(dailySchedulerRepository, times(1)).save(daily);
        verifyNoMoreInteractions(dailySchedulerRepository, monthlySchedulerRepository);
    }

    @Test
    void testSaveDailyNull() {
        // when
        final var actual = assertThrows(RuntimeException.class, () -> schedulerService.save((DailyScheduler) null));

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("scheduler is marked non-null but is null", actual.getMessage())
        );
    }
}