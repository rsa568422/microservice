package com.example.microservice.application.port.in;

import com.example.microservice.domain.model.Scheduler;
import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public interface GetActualSchedulerByWorkerUseCase {

    Optional<Scheduler> getActualSchedulerByWorker(@NonNull UUID workerCode);
}
