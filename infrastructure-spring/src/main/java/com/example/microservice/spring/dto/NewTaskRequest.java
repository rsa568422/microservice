package com.example.microservice.spring.dto;

import org.apache.commons.lang3.ObjectUtils;

public record NewTaskRequest(String description, String priority, String duration) {

    public NewTaskRequest {
        if (ObjectUtils.anyNull(description, priority, duration)) {
            throw new IllegalArgumentException("All fields must be filled");
        }
    }
}
