package com.example.microservice.application.port.in;

import com.example.microservice.domain.model.Task;

import java.util.List;

public interface GetPendingTasksByPriorityUseCase {

    List<Task> getPendingTasksShortedByPriority();
}
