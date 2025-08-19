package com.example.microservice.application.service;

import com.example.microservice.application.dto.NewTaskDTO;
import com.example.microservice.application.mapper.TaskMapper;
import com.example.microservice.application.port.in.GetPendingTasksByPriorityUseCase;
import com.example.microservice.application.port.in.RegisterNewTaskUseCase;
import com.example.microservice.application.port.out.TaskRepository;
import com.example.microservice.domain.model.Status;
import com.example.microservice.domain.model.Task;
import com.example.microservice.domain.service.TaskDomainService;
import lombok.NonNull;

import java.util.List;

public class TaskService extends TaskDomainService<TaskRepository>
        implements GetPendingTasksByPriorityUseCase, RegisterNewTaskUseCase {

    private final TaskMapper taskMapper;

    public TaskService(@NonNull TaskRepository taskRepository,
                       @NonNull TaskMapper taskMapper) {
        super(taskRepository);
        this.taskMapper = taskMapper;
    }

    @Override
    public List<Task> getPendingTasksShortedByPriority() {
        return repository.findByStatusSortedByPriority(Status.PENDING);
    }

    @Override
    public void registerNewTask(@NonNull NewTaskDTO newTask) {
        save(taskMapper.toDomain(newTask));
    }
}
