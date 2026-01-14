package com.example.microservice.task.mapper;

import com.example.microservice.domain.model.Task;
import com.example.microservice.task.entity.TaskEntity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {DurationJpaMapper.class, PriorityJpaMapper.class, StatusJpaMapper.class})
public interface TaskJpaMapper {

    Task toModel(TaskEntity entity);

    List<Task> toModel(List<TaskEntity> tasks);

    @InheritInverseConfiguration
    TaskEntity toEntity(Task task);
}
