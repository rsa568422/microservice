package com.example.microservice.task.mapper;

import com.example.microservice.domain.model.Priority;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface PriorityJpaMapper {

    default Priority integerToPriority(Integer value) {
        return Priority.fromValue(value);
    }

    default Integer priorityToInteger(Priority priority) {
        return priority.getValue();
    }
}
