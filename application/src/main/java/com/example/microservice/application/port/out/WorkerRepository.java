package com.example.microservice.application.port.out;

import com.example.microservice.domain.model.Worker;
import com.example.microservice.domain.port.out.WorkerDomainRepository;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.List;

public interface WorkerRepository extends WorkerDomainRepository {

    List<Worker> findAvailableWorkers(@NonNull LocalDate date);
}
