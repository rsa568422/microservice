package com.example.microservice.application.port.in;

import com.example.microservice.application.dto.NewWorkerDTO;
import lombok.NonNull;

public interface RegisterNewWorkerUseCase {

    void registerNewWorker(@NonNull NewWorkerDTO newWorker);
}
