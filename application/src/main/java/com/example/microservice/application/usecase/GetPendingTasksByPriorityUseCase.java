package com.example.microservice.application.usecase;

import com.example.microservice.application.dto.OutputTask;
import com.example.microservice.application.mapper.TaskMapper;
import com.example.microservice.application.service.TaskService;
import com.example.microservice.domain.model.Priority;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class GetPendingTasksByPriorityUseCase {

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    /**
     * Devuelve un mapa con las tareas pendientes agrupadas por prioridad.
     * @return {@code Map<Priority, Set<OutputTask>>} Tareas pendientes agrupadas por prioridad.
     */
    public Map<Priority, Set<OutputTask>> execute() {
        return taskMapper.toOutputMap(taskService.pendingTasksByPriority());
    }
}
