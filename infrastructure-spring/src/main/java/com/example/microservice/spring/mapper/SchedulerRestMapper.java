package com.example.microservice.spring.mapper;

import com.example.microservice.application.dto.NewSchedulerDTO;
import com.example.microservice.domain.model.Scheduler;
import com.example.microservice.spring.dto.NewSchedulerRequest;
import com.example.microservice.spring.dto.SchedulerResponse;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = {CommonRestMapper.class, TaskRestMapper.class, WorkerRestMapper.class})
public interface SchedulerRestMapper {

    SchedulerResponse toResponse(Scheduler scheduler);

    NewSchedulerDTO toDTO(NewSchedulerRequest newScheduler);
}
