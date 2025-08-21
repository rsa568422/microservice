package com.example.microservice.spring.mapper;

import com.example.microservice.application.dto.NewWorkerDTO;
import com.example.microservice.domain.model.Worker;
import com.example.microservice.spring.dto.NewWorkerRequest;
import com.example.microservice.spring.dto.WorkerResponse;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, uses = CommonRestMapper.class)
public interface WorkerRestMapper {

    WorkerResponse toResponse(Worker worker);

    List<WorkerResponse> toResponse(List<Worker> workers);

    NewWorkerDTO toDTO(NewWorkerRequest newWorker);
}
