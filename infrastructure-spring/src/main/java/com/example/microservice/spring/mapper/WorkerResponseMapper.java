package com.example.microservice.spring.mapper;

import com.example.microservice.domain.model.Worker;
import com.example.microservice.spring.dto.WorkerResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = CommonMapper.class)
public interface WorkerResponseMapper {

    WorkerResponse toResponse(Worker worker);
}
