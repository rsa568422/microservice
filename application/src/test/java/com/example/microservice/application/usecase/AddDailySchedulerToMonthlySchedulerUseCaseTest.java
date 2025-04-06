package com.example.microservice.application.usecase;

import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.service.SchedulerService;
import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.model.MonthlyScheduler;
import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import com.example.microservice.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddDailySchedulerToMonthlySchedulerUseCaseTest {

    @InjectMocks
    private AddDailySchedulerToMonthlySchedulerUseCase addDailySchedulerToMonthlySchedulerUseCase;

    @Mock
    private SchedulerService schedulerService;

    @Mock
    private SchedulerMapper schedulerMapper;

    @Test
    void testExecute() {
        // give
        final var date = LocalDate.of(2025, Month.JANUARY, 1);
        final var task = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING)
                .build();
        final var daily = DailyScheduler.builder()
                .date(date)
                .build();
        daily.add(task);
        final var monthly = MonthlyScheduler.builder()
                .year(Year.from(date))
                .month(Month.from(date))
                .build();

        when(schedulerService.findByDate(date)).thenReturn(Optional.of(daily));
        when(schedulerService.findByYearAndMont(Year.from(date), Month.from(date))).thenReturn(Optional.of(monthly));
        when(schedulerMapper.toMonthlyScheduler(date)).thenReturn(monthly);

        // when
        assertDoesNotThrow(() -> addDailySchedulerToMonthlySchedulerUseCase.execute(date));

        // then
        verify(schedulerService, times(1)).findByDate(date);
        verify(schedulerService, times(1)).findByYearAndMont(Year.from(date), Month.from(date));
        verify(schedulerService, times(1)).save(monthly);
        verify(schedulerMapper, times(1)).toMonthlyScheduler(date);
        verifyNoMoreInteractions(schedulerService, schedulerMapper);
    }

    @Test
    void testExecuteNull() {
        // when
        final var actual = assertThrows(
                RuntimeException.class,
                () -> addDailySchedulerToMonthlySchedulerUseCase.execute(null)
        );

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("date is marked non-null but is null", actual.getMessage())
        );

        verifyNoInteractions(schedulerService, schedulerMapper);
    }
}