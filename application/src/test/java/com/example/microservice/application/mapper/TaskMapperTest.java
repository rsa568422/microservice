package com.example.microservice.application.mapper;

import com.example.microservice.application.dto.NewTask;
import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskMapperTest {

    private final TaskMapper taskMapper = Mappers.getMapper(TaskMapper.class);

    @Test
    void testToTask() {
        // given
        final var newTask = NewTask.builder()
                .description("description")
                .priority(Priority.HIGH)
                .duration(1)
                .build();

        // when
        final var actual = taskMapper.toTask(newTask);

        // then
        assertAll(
                () -> assertNotNull(actual),
                () -> assertEquals(newTask.description(), actual.getDescription()),
                () -> assertEquals(newTask.priority(), actual.getPriority()),
                () -> assertEquals(newTask.duration().longValue(), actual.getDuration().toMinutes()),
                () -> assertEquals(Status.PENDING, actual.getStatus())
        );
    }
}