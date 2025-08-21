package com.example.microservice.task.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
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

    @Min(1)
    @Max(4)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private Integer priority;

    @Min(0)
    private Double duration;

    @Pattern(regexp = "^[PW]$")
    private String status;
}
