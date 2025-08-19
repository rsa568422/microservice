package com.example.microservice.domain.port.out;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenericDomainRepository<T> {

    List<T> findAll();

    Optional<T> findByCode(@NonNull UUID code);

    void save(@NonNull T model);
}
