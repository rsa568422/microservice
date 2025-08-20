package com.example.microservice.spring.mapper;

import com.example.microservice.domain.model.Scheduler;
import com.example.microservice.spring.dto.SchedulerResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {CommonMapper.class, TaskResponseMapper.class, WorkerResponseMapper.class})
public interface SchedulerResponseMapper {

    SchedulerResponse toResponse(Scheduler scheduler);
}
