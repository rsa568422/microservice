package com.example.microservice.domain.service;

import com.example.microservice.domain.model.Scheduler;
import com.example.microservice.domain.port.out.SchedulerDomainRepository;

public class SchedulerDomainService<R extends SchedulerDomainRepository> extends GenericDomainService<Scheduler, R> {

    public SchedulerDomainService(R repository) {
        super(repository);
    }
}
