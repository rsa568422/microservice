package com.example.microservice.spring.controller;

import com.example.microservice.application.dto.NewTask;
import com.example.microservice.application.dto.OutputTask;
import com.example.microservice.application.usecase.GetPendingTasksByPriorityUseCase;
import com.example.microservice.application.usecase.RegisterTaskUseCase;
import com.example.microservice.domain.model.Priority;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/task")
public class TaskController {

    private final GetPendingTasksByPriorityUseCase getPendingTasksByPriorityUseCase;

    private final RegisterTaskUseCase registerTaskUseCase;

    @GetMapping
    public ResponseEntity<Map<Priority, Set<OutputTask>>> get() {
        return ResponseEntity.ok(getPendingTasksByPriorityUseCase.execute());
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody NewTask task) {
        registerTaskUseCase.execute(task);
        return ResponseEntity.ok().build();
    }
}
