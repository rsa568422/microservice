package com.example.microservice.application.port.in;

import com.example.microservice.domain.model.Worker;

import java.util.List;

public interface GetAllWorkersUseCase {

    List<Worker> getAllWorkers();
}
