package com.example.microservice.application.usecase;

import com.example.microservice.application.dto.NewDailyScheduler;
import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.service.SchedulerService;
import com.example.microservice.application.service.TaskService;
import com.example.microservice.domain.model.DailyScheduler;
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
import java.util.Optional;
import java.util.Set;

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
class RegisterDailySchedulerUseCaseTest {

    @InjectMocks
    private RegisterDailySchedulerUseCase registerDailySchedulerUseCase;

    @Mock
    private TaskService taskService;

    @Mock
    private SchedulerService schedulerService;

    @Mock
    private SchedulerMapper schedulerMapper;

    @Test
    void testExecute() {
        // given
        final var builder = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING);
        final var task = builder.build();
        final var taskBefore = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.WORKING)
                .build();
        final var newScheduler = NewDailyScheduler.builder()
                .date(LocalDate.of(2025, Month.JANUARY, 1))
                .taskCodes(Set.of("CODE"))
                .build();
        final var scheduler = DailyScheduler.builder()
                .date(newScheduler.date())
                .build();
        scheduler.add(builder.build());

        when(taskService.findByCode(task.getCode())).thenReturn(Optional.of(task));
        when(schedulerMapper.toDailyScheduler(newScheduler.date(), Set.of(task))).thenReturn(scheduler);

        // when
        assertDoesNotThrow(() -> registerDailySchedulerUseCase.execute(newScheduler));

        // then
        verify(taskService, times(1)).findByCode(task.getCode());
        verify(taskService, times(1)).save(taskBefore);
        verify(schedulerService, times(1)).save(scheduler);
        verify(schedulerMapper, times(1)).toDailyScheduler(newScheduler.date(), Set.of(task));
        verifyNoMoreInteractions(taskService, schedulerService, schedulerMapper);
    }

    @Test
    void testExecuteError() {
        // given
        final var task = Task.builder()
                .code("CODE1")
                .description("description 1")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING)
                .build();
        final var code2 = "CODE2";
        final var newScheduler = NewDailyScheduler.builder()
                .date(LocalDate.of(2025, Month.JANUARY, 1))
                .taskCodes(Set.of(task.getCode(), code2))
                .build();

        when(taskService.findByCode(task.getCode())).thenReturn(Optional.of(task));
        when(taskService.findByCode(code2)).thenReturn(Optional.empty());

        // when
        final var actual = assertThrows(
                IllegalArgumentException.class,
                () -> registerDailySchedulerUseCase.execute(newScheduler)
        );

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("The daily scheduler contains not registered tasks", actual.getMessage())
        );

        verify(taskService, times(1)).findByCode(task.getCode());
        verify(taskService, times(1)).findByCode(code2);
        verifyNoMoreInteractions(taskService, schedulerService, schedulerMapper);
    }

    @Test
    void testExecuteNull() {
        // when
        final var actual = assertThrows(RuntimeException.class, () -> registerDailySchedulerUseCase.execute(null));

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("scheduler is marked non-null but is null", actual.getMessage())
        );

        verifyNoInteractions(taskService, schedulerService, schedulerMapper);
    }
}