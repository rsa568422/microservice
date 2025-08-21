package com.example.microservice.spring.controller;

import com.example.microservice.application.port.in.GetAllWorkersUseCase;
import com.example.microservice.application.port.in.RegisterNewWorkerUseCase;
import com.example.microservice.spring.dto.NewWorkerRequest;
import com.example.microservice.spring.dto.WorkerResponse;
import com.example.microservice.spring.mapper.WorkerRestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/worker"))
public class WorkerController {

    private final GetAllWorkersUseCase getAllWorkersUseCase;

    private final RegisterNewWorkerUseCase registerNewWorkerUseCase;

    private final WorkerRestMapper workerRestMapper;

    @GetMapping
    ResponseEntity<List<WorkerResponse>> getAllWorkers() {
        final var workers = getAllWorkersUseCase.getAllWorkers();
        if (workers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(workerRestMapper.toResponse(workers));
    }

    @PostMapping("/register")
    ResponseEntity<Void> registerNewWorker(@RequestBody NewWorkerRequest newWorker) {
        registerNewWorkerUseCase.registerNewWorker(workerRestMapper.toDTO(newWorker));
        return ResponseEntity.ok().build();
    }
}
