package com.example.microservice.spring.dto;

import org.apache.commons.lang3.ObjectUtils;

public record WorkerResponse(String code, String name, String surname) {

    public WorkerResponse {
        if (ObjectUtils.anyNull(code, name, surname)) {
            throw new IllegalArgumentException("All fields must be filled");
        }
    }
}
