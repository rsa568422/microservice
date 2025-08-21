package com.example.microservice.worker.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class WorkerEntity {

    private UUID code;

    private String name;

    private String surname;
}
