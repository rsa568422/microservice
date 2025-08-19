package com.example.microservice.task.adapter;

import com.example.microservice.application.port.out.TaskRepository;
import com.example.microservice.domain.model.Status;
import com.example.microservice.domain.model.Task;
import com.example.microservice.task.mapper.TaskJpaMapper;
import com.example.microservice.task.repository.TaskJpaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskJpaRepository taskJpaRepository;

    private final TaskJpaMapper taskJpaMapper;

    @Override
    public List<Task> findAll() {
        return taskJpaMapper.toModel(taskJpaRepository.findAll());
    }

    @Override
    public Optional<Task> findByCode(@NonNull UUID code) {
        return taskJpaRepository.findById(code).map(taskJpaMapper::toModel);
    }

    @Override
    public List<Task> findByStatusSortedByPriority(@NonNull Status status) {
        return taskJpaMapper.toModel(taskJpaRepository.findByStatusOrderByPriorityAsc(status));
    }

    @Override
    public void save(@NonNull Task model) {
        taskJpaRepository.save(taskJpaMapper.toEntity(model));
    }
}
