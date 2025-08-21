package com.example.microservice.spring.controller;

import com.example.microservice.application.service.WorkerService;
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

    private final WorkerService workerService;

    private final WorkerRestMapper workerRestMapper;

    @GetMapping
    ResponseEntity<List<WorkerResponse>> getAllWorkers() {
        final var workers = workerService.getAllWorkers();
        if (workers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(workerRestMapper.toResponse(workers));
    }

    @PostMapping("/register")
    ResponseEntity<Void> registerNewWorker(@RequestBody NewWorkerRequest newWorker) {
        workerService.registerNewWorker(workerRestMapper.toDTO(newWorker));
        return ResponseEntity.ok().build();
    }
}
