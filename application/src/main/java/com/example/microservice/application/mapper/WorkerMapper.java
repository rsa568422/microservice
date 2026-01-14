package com.example.microservice.application.mapper;

import com.example.microservice.application.dto.NewWorkerDTO;
import com.example.microservice.domain.model.Worker;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface WorkerMapper {

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "surname", source = "surname")
    @NonNull Worker toDomain(@NonNull NewWorkerDTO newWorker);
}
