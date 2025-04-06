package com.example.microservice.application.mapper;

import com.example.microservice.application.dto.NewTask;
import com.example.microservice.domain.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TaskMapper {

    @Mapping(target = "status", constant = "PENDING")
    Task toTask(NewTask newTask);
}
