package com.example.microservice.task.mapper;

import com.example.microservice.domain.model.Status;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface StatusJpaMapper {

    default Status integerToPriority(String value) {
        return Status.fromValue(value);
    }

    default String priorityToInteger(Status status) {
        return status.getValue();
    }
}
