package com.example.microservice.spring.dto;

import org.apache.commons.lang3.ObjectUtils;

public record NewWorkerRequest(String name, String surname) {

    public NewWorkerRequest {
        if (ObjectUtils.anyNull(name, surname)) {
            throw new IllegalArgumentException("All fields must be filled");
        }
    }
}
