package com.example.microservice.application.mapper;

import com.example.microservice.domain.model.Scheduler;
import com.example.microservice.domain.model.Worker;
import lombok.NonNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper
public interface SchedulerMapper {

    @Mapping(target = "code", ignore = true)
    @Mapping(target = "date", source = "date")
    @Mapping(target = "worker", source = "worker")
    @NonNull Scheduler toDomain(@NonNull LocalDate date, @NonNull Worker worker);
}
