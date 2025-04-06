package com.example.microservice.application.usecase;

import com.example.microservice.application.dto.NewTask;
import com.example.microservice.application.mapper.TaskMapper;
import com.example.microservice.application.service.TaskService;
import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import com.example.microservice.domain.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;

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
class RegisterTaskUseCaseTest {

    @InjectMocks
    private RegisterTaskUseCase registerTaskUseCase;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    void testExecute() {
        // given
        final var newTask = NewTask.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .build();

        final var task = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING)
                .build();

        when(taskMapper.toTask(newTask)).thenReturn(task);

        // when
        assertDoesNotThrow(() -> registerTaskUseCase.execute(newTask));

        // then
        verify(taskMapper, times(1)).toTask(newTask);
        verify(taskService, times(1)).save(task);
        verifyNoMoreInteractions(taskMapper, taskService);
    }

    @Test
    void testExecuteNull() {
        // when
        final var actual = assertThrows(RuntimeException.class, () -> registerTaskUseCase.execute(null));

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("task is marked non-null but is null", actual.getMessage())
        );

        verifyNoInteractions(taskService, taskMapper);
    }
}