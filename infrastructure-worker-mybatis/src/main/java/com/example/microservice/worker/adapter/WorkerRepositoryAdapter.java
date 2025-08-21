package com.example.microservice.worker.adapter;

import com.example.microservice.application.port.out.WorkerRepository;
import com.example.microservice.domain.model.Worker;
import com.example.microservice.worker.mapper.WorkerMyBatisMapper;
import com.example.microservice.worker.repository.WorkerMyBatisRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WorkerRepositoryAdapter implements WorkerRepository {

    private final WorkerMyBatisRepository workerMyBatisRepository;

    private final WorkerMyBatisMapper workerMyBatisMapper;
    @Override
    public List<Worker> findAll() {
        return workerMyBatisMapper.toModel(workerMyBatisRepository.findAll());
    }

    @Override
    public Optional<Worker> findByCode(@NonNull UUID code) {
        return Optional.ofNullable(workerMyBatisRepository.findByCode(code)).map(workerMyBatisMapper::toModel);
    }

    @Override
    public void save(@NonNull Worker model) {
        final var entity = workerMyBatisMapper.toEntity(model);
        if (Objects.nonNull(model.getCode())) {
            workerMyBatisRepository.update(entity);
        } else {
            workerMyBatisRepository.insert(entity);
        }
    }
}
