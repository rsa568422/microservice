package com.example.microservice.spring.controller;

import com.example.microservice.application.dto.NewTaskDTO;
import com.example.microservice.application.port.in.GetPendingTasksByPriorityUseCase;
import com.example.microservice.application.port.in.RegisterNewTaskUseCase;
import com.example.microservice.spring.dto.TaskResponse;
import com.example.microservice.spring.mapper.TaskResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(("/task"))
public class TaskController {

    private final GetPendingTasksByPriorityUseCase getPendingTasksByPriorityUseCase;

    private final RegisterNewTaskUseCase registerNewTaskUseCase;

    private final TaskResponseMapper taskResponseMapper;

    @GetMapping("/pending")
    ResponseEntity<List<TaskResponse>> getPendingTasksByPriority() {
        final var tasks = getPendingTasksByPriorityUseCase.getPendingTasksShortedByPriority();
        if (tasks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(taskResponseMapper.toResponse(tasks));
    }

    @PostMapping("/register")
    ResponseEntity<Void> registerNewWorker(@RequestBody NewTaskDTO newTask) {
        registerNewTaskUseCase.registerNewTask(newTask);
        return ResponseEntity.ok().build();
    }
}
