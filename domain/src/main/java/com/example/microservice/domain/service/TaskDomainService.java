package com.example.microservice.domain.service;

import com.example.microservice.domain.model.Task;
import com.example.microservice.domain.port.out.TaskDomainRepository;

public class TaskDomainService<R extends  TaskDomainRepository> extends GenericDomainService<Task, R> {

    public TaskDomainService(R repository) {
        super(repository);
    }
}
