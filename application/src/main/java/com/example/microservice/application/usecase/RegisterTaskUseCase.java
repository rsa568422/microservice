package com.example.microservice.application.usecase;

import com.example.microservice.application.dto.NewTask;
import com.example.microservice.application.mapper.TaskMapper;
import com.example.microservice.application.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterTaskUseCase {

    private final TaskService taskService;

    private final TaskMapper taskMapper;

    /**
     * Registra una nueva tarea con los valores indicados en el parámetro task y con estado
     * {@link com.example.microservice.domain.model.Status#PENDING}.
     * @param task {@link NewTask} Información de la nueva tarea.
     */
    public void execute(@NonNull NewTask task) {
        taskService.save(taskMapper.toTask(task));
    }
}
