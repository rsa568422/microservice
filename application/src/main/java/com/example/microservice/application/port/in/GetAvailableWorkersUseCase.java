package com.example.microservice.application.port.in;

import com.example.microservice.domain.model.Worker;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

public interface GetAvailableWorkersUseCase {

    List<Worker> getAvailableWorkers(@NonNull LocalDate date);
}
