package com.example.microservice.domain.model;

import com.example.microservice.domain.Data;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TaskTest {

    @Test
    void testEquals() {
        assertAll(
                () -> assertEquals(Data.TASK_1_1.build(), Data.TASK_1_1B.build()),
                () -> assertEquals(Data.TASK_1_1B.build(), Data.TASK_1_1.build()),
                () -> assertNotEquals(Data.TASK_1_1.build(), Data.TASK_1_2.build()),
                () -> assertNotEquals(Data.TASK_1_2.build(), Data.TASK_1_1.build()),
                () -> assertNotEquals(Data.TASK_1_1.build(), 1)
        );
    }

    @Test
    void testHashCode() {
        assertAll(
                () -> assertEquals(Data.TASK_1_1.build().hashCode(), Data.TASK_1_1B.build().hashCode()),
                () -> assertEquals(Data.TASK_1_1B.build().hashCode(), Data.TASK_1_1.build().hashCode()),
                () -> assertNotEquals(Data.TASK_1_1.build().hashCode(), Data.TASK_1_2.build().hashCode()),
                () -> assertNotEquals(Data.TASK_1_2.build().hashCode(), Data.TASK_1_1.build().hashCode())
        );
    }

    @Test
    void testGetters() {
        // given
        final var task = Data.TASK_1_1.build();

        // then
        assertAll(
                () -> assertEquals("CODE#1", task.getCode()),
                () -> assertEquals("description of task 1", task.getDescription()),
                () -> assertEquals(Priority.CRITICAL, task.getPriority()),
                () -> assertEquals(Duration.ofHours(4), task.getDuration()),
                () -> assertEquals(Status.PENDING, task.getStatus())
        );
    }
}