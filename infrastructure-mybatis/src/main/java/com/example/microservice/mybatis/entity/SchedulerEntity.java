package com.example.microservice.mybatis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerEntity {

    private Integer year;

    private Integer month;

    private Integer day;

    private UUID task;
}
