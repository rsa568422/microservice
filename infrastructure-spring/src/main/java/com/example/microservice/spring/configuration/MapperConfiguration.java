package com.example.microservice.spring.configuration;

import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.mapper.TaskMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public TaskMapper taskMapper() {
        return Mappers.getMapper(TaskMapper.class);
    }

    @Bean
    public SchedulerMapper schedulerMapper() {
        return Mappers.getMapper(SchedulerMapper.class);
    }
}
