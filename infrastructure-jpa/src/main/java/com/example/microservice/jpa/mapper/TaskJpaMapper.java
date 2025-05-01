package com.example.microservice.jpa.mapper;

import com.example.microservice.domain.model.Task;
import com.example.microservice.jpa.entity.TaskEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.time.Duration;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface TaskJpaMapper {

    Task toModel(TaskEntity entity);

    @InheritInverseConfiguration
    TaskEntity toEntity(Task task);

    default Duration toDuration(Double value) {
        return Duration.ofMinutes(value.longValue());
    }

    default Double toDouble(Duration duration) {
        return (double) duration.toMinutes();
    }
}
