package com.example.microservice.spring.dto;

import org.apache.commons.lang3.ObjectUtils;

public record AddTaskToSchedulerRequest(String scheduler, String task) {

    public AddTaskToSchedulerRequest {
        if (ObjectUtils.anyNull(scheduler, task)) {
            throw new IllegalArgumentException("All fields must be filled");
        }
    }
}
