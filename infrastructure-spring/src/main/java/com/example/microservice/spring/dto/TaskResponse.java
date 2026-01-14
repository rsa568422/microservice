package com.example.microservice.spring.dto;

import org.apache.commons.lang3.ObjectUtils;

public record TaskResponse(String code, String description, String priority, String duration, String status) {

    public TaskResponse {
        if (ObjectUtils.anyNull(code, description, priority, duration, status)) {
            throw new IllegalArgumentException("All fields must be filled");
        }
    }
}
