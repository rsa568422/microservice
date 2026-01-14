package com.example.microservice.task.mapper;

import org.mapstruct.Mapper;

import java.time.Duration;
import java.util.Objects;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface DurationJpaMapper {

    default Duration doubleToDuration(Double value) {
        return Objects.nonNull(value) ? Duration.ofMinutes(value.longValue()) : null;
    }

    default Double durationToDouble(Duration duration) {
        return Objects.nonNull(duration) ? (double) duration.toMinutes() : null;
    }
}
