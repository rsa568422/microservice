package com.example.microservice.application.usecase;

import com.example.microservice.application.service.TaskService;
import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Task;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class GetPendingTasksByPriorityUseCase {

    private final TaskService taskService;

    /**
     * Devuelve un mapa con las tareas pendientes agrupadas por prioridad.
     * @return {@code Map<Priority, Set<Task>>} Tareas pendientes agrupadas por prioridad.
     */
    public Map<Priority, Set<Task>> execute() {
        return taskService.pendingTasksByPriority();
    }
}
