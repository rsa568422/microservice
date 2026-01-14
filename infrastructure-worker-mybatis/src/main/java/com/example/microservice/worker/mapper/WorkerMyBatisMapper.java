package com.example.microservice.worker.mapper;

import com.example.microservice.domain.model.Worker;
import com.example.microservice.worker.entity.WorkerEntity;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface WorkerMyBatisMapper {

    Worker toModel(WorkerEntity worker);

    List<Worker> toModel(List<WorkerEntity> workers);

    WorkerEntity toEntity(Worker model);
}
