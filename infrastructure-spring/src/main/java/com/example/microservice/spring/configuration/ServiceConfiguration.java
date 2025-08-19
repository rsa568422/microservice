package com.example.microservice.spring.configuration;

import com.example.microservice.application.mapper.TaskMapper;
import com.example.microservice.application.mapper.WorkerMapper;
import com.example.microservice.application.port.out.TaskRepository;
import com.example.microservice.application.port.out.WorkerRepository;
import com.example.microservice.application.service.TaskService;
import com.example.microservice.application.service.WorkerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public TaskService taskService(TaskRepository taskRepository, TaskMapper taskMapper) {
        return new TaskService(taskRepository, taskMapper);
    }

    @Bean
    public WorkerService workerService(WorkerRepository workerRepository, WorkerMapper workerMapper) {
        return new WorkerService(workerRepository, workerMapper);
    }
}
