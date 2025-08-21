package com.example.microservice.application.port.out;

import com.example.microservice.domain.model.Scheduler;
import com.example.microservice.domain.port.out.SchedulerDomainRepository;
import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface SchedulerRepository extends SchedulerDomainRepository {

    Optional<Scheduler> findActualSchedulerByWorker(@NonNull UUID workerCode);
}
