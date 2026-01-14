package com.example.microservice.spring.dto;

import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public record SchedulerResponse(String code, String date, WorkerResponse worker, List<TaskResponse> tasks) {

    public SchedulerResponse {
        if (ObjectUtils.anyNull(tasks)) {
            throw new IllegalArgumentException("All fields must be filled");
        }
    }
}
