package com.example.microservice.application.service;

import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Task;
import com.example.microservice.domain.repository.TaskRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public Optional<Task> findByCode(@NonNull String code) {
        return taskRepository.findByCode(code);
    }

    public Map<Priority, Set<Task>> pendingTasksByPriority() {
        return taskRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Task::getPriority, Collectors.toSet()));
    }

    public void save(@NonNull Task task) {
        taskRepository.save(task);
    }
}
