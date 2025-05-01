package com.example.microservice.mybatis.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SchedulerEntity {

    private Integer year;

    private Integer month;

    private Integer day;

    private String task;
}
