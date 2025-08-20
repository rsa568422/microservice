package com.example.microservice.spring.mapper;

import com.example.microservice.application.dto.NewSchedulerDTO;
import com.example.microservice.spring.dto.NewSchedulerRequest;

import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = CommonMapper.class)
public interface SchedulerRequestMapper {

    NewSchedulerDTO toDTO(NewSchedulerRequest newScheduler);
}
