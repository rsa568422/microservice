package com.example.microservice.spring.controller;

import com.example.microservice.application.dto.NewWorkerDTO;
import com.example.microservice.application.port.in.GetAvailableWorkersUseCase;
import com.example.microservice.application.port.in.RegisterNewWorkerUseCase;
import com.example.microservice.domain.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/worker"))
public class WorkerController {

    private final GetAvailableWorkersUseCase getAvailableWorkersUseCase;

    private final RegisterNewWorkerUseCase registerNewWorkerUseCase;

    @GetMapping("/available/{date}")
    ResponseEntity<List<Worker>> getAvailableWorkers(@PathVariable("date") LocalDate date) {
        final var workers = getAvailableWorkersUseCase.getAvailableWorkers(date);
        if (workers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(workers);
    }

    @PostMapping("/register")
    ResponseEntity<Void> registerNewWorker(@RequestBody NewWorkerDTO newWorker) {
        registerNewWorkerUseCase.registerNewWorker(newWorker);
        return ResponseEntity.ok().build();
    }
}
