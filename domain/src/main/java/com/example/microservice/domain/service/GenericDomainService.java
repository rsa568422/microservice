package com.example.microservice.domain.service;

import com.example.microservice.domain.port.out.GenericDomainRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class GenericDomainService<T, R extends GenericDomainRepository<T>> {

    protected final R repository;

    public List<T> findAll() {
        return repository.findAll();
    }

    public Optional<T> findByCode(@NonNull UUID code) {
        return repository.findByCode(code);
    }

    public void save(@NonNull T model) {
        repository.save(model);
    }
}
