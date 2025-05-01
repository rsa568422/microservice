package com.example.microservice.jpa.adapter;

import com.example.microservice.domain.model.Task;
import com.example.microservice.domain.repository.TaskRepository;
import com.example.microservice.jpa.mapper.TaskJpaMapper;
import com.example.microservice.jpa.repository.TaskJpaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryAdapter implements TaskRepository {

    private final TaskJpaRepository repository;

    private final TaskJpaMapper mapper;

    @Override
    public Optional<Task> findByCode(@NonNull String code) {
        return repository.findById(UUID.fromString(code)).map(mapper::toModel);
    }

    @Override
    public Set<Task> findAll() {
        return repository.findAll().stream().map(mapper::toModel).collect(Collectors.toSet());
    }

    @Override
    public void save(@NonNull Task task) {
        repository.save(mapper.toEntity(task));
    }
}
