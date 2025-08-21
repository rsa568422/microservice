package com.example.microservice.spring.mapper;

import com.example.microservice.application.dto.NewTaskDTO;
import com.example.microservice.domain.model.Task;
import com.example.microservice.spring.dto.NewTaskRequest;
import com.example.microservice.spring.dto.TaskResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = CommonRestMapper.class)
public interface TaskRestMapper {

    @Mapping(target = "code", source = "code")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "status", source = "status")
    TaskResponse toResponse(Task task);

    List<TaskResponse> toResponse(List<Task> tasks);

    NewTaskDTO toDTO(NewTaskRequest newTask);
}
