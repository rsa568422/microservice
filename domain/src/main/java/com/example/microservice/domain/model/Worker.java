package com.example.microservice.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class Worker {

    private final UUID code;

    private final String name;

    private final String surname;
}
