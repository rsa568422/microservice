package com.example.microservice.domain.repository;

import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Task;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface TaskRepository {

    Optional<Task> findByCode(@NonNull String code);

    Map<Priority, Set<Task>> findAll();

    void save(@NonNull Task task);
}
