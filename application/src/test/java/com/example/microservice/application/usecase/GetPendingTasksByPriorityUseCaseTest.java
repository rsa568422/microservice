package com.example.microservice.application.usecase;

import com.example.microservice.application.dto.OutputTask;
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
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPendingTasksByPriorityUseCaseTest {

    @InjectMocks
    private GetPendingTasksByPriorityUseCase getPendingTasksByPriorityUseCase;

    @Mock
    private TaskService taskService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    void testExecute() {
        // given
        final var task = Task.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .status(Status.PENDING)
                .build();
        final var output = OutputTask.builder()
                .code("CODE")
                .description("description")
                .duration("1H")
                .status(Status.PENDING)
                .build();
        final var tasks = Map.of(Priority.HIGH, Set.of(task));
        final var expected = Map.of(Priority.HIGH, Set.of(output));

        when(taskService.pendingTasksByPriority()).thenReturn(tasks);
        when(taskMapper.toOutputMap(tasks)).thenReturn(expected);

        // when
        final var actual = getPendingTasksByPriorityUseCase.execute();

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertTrue(actual.containsKey(Priority.HIGH)),
                () -> {
                    final var taskSet = actual.get(Priority.HIGH);
                    assertAll(
                            () -> assertNotNull(taskSet),
                            () -> assertEquals(1, taskSet.size()),
                            () -> assertIterableEquals(Set.of(output), taskSet)
                    );
                }
        );

        verify(taskService, times(1)).pendingTasksByPriority();
        verify(taskMapper, times(1)).toOutputMap(tasks);
        verifyNoMoreInteractions(taskService, taskMapper);
    }
}