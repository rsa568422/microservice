package com.example.microservice.application.service;

import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import com.example.microservice.domain.model.Task;
import com.example.microservice.domain.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    void testFindByCode() {
        // given
        final var code = "CODE";
        final var task = Task.builder()
                .code(code)
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING)
                .build();

        when(taskRepository.findByCode(code)).thenReturn(Optional.of(task));

        // when
        final var actual = taskService.findByCode(code);

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(Optional.of(task), actual)
        );

        verify(taskRepository, times(1)).findByCode(code);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testFindByCodeNull() {
        // when
        final var actual = assertThrows(RuntimeException.class, () -> taskService.findByCode(null));

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("code is marked non-null but is null", actual.getMessage())
        );
    }

    @Test
    void testPendingTasksByPriority() {
        // given
        final var task = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING)
                .build();

        when(taskRepository.findAll()).thenReturn(Set.of(task));

        // when
        final var actual = taskService.pendingTasksByPriority();

        assertAll(
                () -> assertNotNull(actual),
                () -> assertTrue(actual.containsKey(Priority.HIGH)),
                () -> assertIterableEquals(Set.of(task), actual.get(Priority.HIGH))
        );

        verify(taskRepository, times(1)).findAll();
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testSave() {
        // given
        final var task = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING)
                .build();

        // when
        assertDoesNotThrow(() -> taskService.save(task));

        // then
        verify(taskRepository, times(1)).save(task);
        verifyNoMoreInteractions(taskRepository);
    }

    @Test
    void testSaveNull() {
        // when
        final var actual = assertThrows(RuntimeException.class, () -> taskService.save(null));

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals("task is marked non-null but is null", actual.getMessage())
        );
    }
}