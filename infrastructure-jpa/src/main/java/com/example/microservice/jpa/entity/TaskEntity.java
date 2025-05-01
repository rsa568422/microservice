package com.example.microservice.jpa.entity;

import com.example.microservice.domain.model.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tasks")
public class TaskEntity {

    @Id
    @GeneratedValue
    private UUID code;

    private String description;

    private String priority;

    private Double duration;

    private Status status;
}
