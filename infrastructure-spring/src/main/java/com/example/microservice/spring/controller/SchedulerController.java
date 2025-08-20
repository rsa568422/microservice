package com.example.microservice.spring.controller;

import com.example.microservice.application.port.in.AddTasksToSchedulerUseCase;
import com.example.microservice.application.port.in.GetActualSchedulerByWorkerUseCase;
import com.example.microservice.application.port.in.RegisterNewSchedulerUseCase;
import com.example.microservice.spring.dto.AddTaskToSchedulerRequest;
import com.example.microservice.spring.dto.NewSchedulerRequest;
import com.example.microservice.spring.dto.SchedulerResponse;
import com.example.microservice.spring.mapper.SchedulerRequestMapper;
import com.example.microservice.spring.mapper.SchedulerResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/scheduler"))
public class SchedulerController {

    private final AddTasksToSchedulerUseCase addTasksToSchedulerUseCase;

    private final GetActualSchedulerByWorkerUseCase getActualSchedulerByWorkerUseCase;

    private final RegisterNewSchedulerUseCase registerNewSchedulerUseCase;

    private final SchedulerResponseMapper schedulerResponseMapper;

    private final SchedulerRequestMapper schedulerRequestMapper;

    @PostMapping("/add")
    ResponseEntity<Void> addTasksToScheduler(@RequestBody AddTaskToSchedulerRequest request) {
        final var task = UUID.fromString(request.task());
        final var scheduler = UUID.fromString(request.scheduler());
        addTasksToSchedulerUseCase.addTasksToScheduler(task, scheduler);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{worker}")
    ResponseEntity<SchedulerResponse> getActualSchedulerByWorker(@PathVariable("worker") String worker) {
        final var scheduler = getActualSchedulerByWorkerUseCase.getActualSchedulerByWorker(UUID.fromString(worker));
        return scheduler.map(schedulerResponseMapper::toResponse)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping("/register")
    ResponseEntity<Void> registerNewWorker(@RequestBody NewSchedulerRequest newScheduler) {
        registerNewSchedulerUseCase.registerNewScheduler(schedulerRequestMapper.toDTO(newScheduler));
        return ResponseEntity.ok().build();
    }
}
