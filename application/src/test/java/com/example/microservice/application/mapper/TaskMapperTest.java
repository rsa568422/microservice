package com.example.microservice.application.mapper;

import com.example.microservice.application.dto.NewTask;
import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    void testToTask() {
        // given
        final var newTask = NewTask.builder()
                .code("CODE")
                .description("description")
                .priority(Priority.HIGH)
                .duration(Duration.ofHours(1))
                .build();

        // when
        final var actual = taskMapper.toTask(newTask);

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(newTask.getCode(), actual.getCode()),
                () -> assertEquals(newTask.getDescription(), actual.getDescription()),
                () -> assertEquals(newTask.getPriority(), actual.getPriority()),
                () -> assertEquals(newTask.getDuration(), actual.getDuration()),
                () -> assertEquals(Status.PENDING, actual.getStatus())
        );
    }
}