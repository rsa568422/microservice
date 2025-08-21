package com.example.microservice.application.mapper;

import com.example.microservice.application.dto.NewTaskDTO;
import com.example.microservice.domain.model.Task;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TaskMapper {

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "priority", source = "priority")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "status", expression = "java(com.example.microservice.domain.model.Status.PENDING)")
    @NonNull Task toDomain(@NonNull NewTaskDTO newTask);
}
