package com.example.microservice.application.port.out;

import com.example.microservice.domain.model.Status;
import com.example.microservice.domain.model.Task;
import com.example.microservice.domain.port.out.TaskDomainRepository;
import lombok.NonNull;

import java.util.List;

public interface TaskRepository extends TaskDomainRepository {

    List<Task> findByStatusSortedByPriority(@NonNull Status status);
}
