package com.example.microservice.application.usecase;

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
class AddTaskToDailySchedulerUseCaseTest {

    @InjectMocks
    private AddTaskToDailySchedulerUseCase addTaskToDailySchedulerUseCase;

    @Mock
    private TaskService taskService;

    @Mock
    private SchedulerService schedulerService;

    @Mock
    private SchedulerMapper schedulerMapper;

    @Test
    void testExecute() {
        // give
        final var date = LocalDate.of(2025, Month.JANUARY, 1);
        final var builder = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING);
        final var task = builder.build();
        final var scheduler = DailyScheduler.builder()
                .date(date)
                .build();

        when(taskService.findByCode(task.getCode())).thenReturn(Optional.of(task));
        when(schedulerService.findByDate(date)).thenReturn(Optional.of(scheduler));
        when(schedulerMapper.toDailyScheduler(date)).thenReturn(scheduler);

        // when
        assertDoesNotThrow(() -> addTaskToDailySchedulerUseCase.execute(date, task.getCode()));

        // then
        verify(taskService, times(1)).findByCode(task.getCode());
        verify(taskService, times(1)).save(task);
        verify(schedulerService, times(1)).findByDate(date);
        verify(schedulerService, times(1)).save(scheduler);
        verifyNoMoreInteractions(taskService, schedulerService, schedulerMapper);
    }

    @Test
    void testExecuteNull() {
        // when
        final var error1 = assertThrows(
                RuntimeException.class,
                () -> addTaskToDailySchedulerUseCase.execute(null, "CODE")
        );
        final var error2 = assertThrows(
                RuntimeException.class,
                () -> addTaskToDailySchedulerUseCase.execute(LocalDate.of(2025, Month.JANUARY, 1), null)
        );

        // then
        assertAll(
                () -> assertNotNull(error1),
                () -> assertNotNull(error2),
                () -> assertEquals("date is marked non-null but is null", error1.getMessage()),
                () -> assertEquals("taskCode is marked non-null but is null", error2.getMessage())
        );

        verifyNoInteractions(taskService, schedulerService, schedulerMapper);
    }
}