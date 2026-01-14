package com.example.microservice.domain.service;

import com.example.microservice.domain.model.Worker;
import com.example.microservice.domain.port.out.WorkerDomainRepository;

public class WorkerDomainService<R extends WorkerDomainRepository> extends GenericDomainService<Worker, R> {

    public WorkerDomainService(R repository) {
        super(repository);
    }
}
