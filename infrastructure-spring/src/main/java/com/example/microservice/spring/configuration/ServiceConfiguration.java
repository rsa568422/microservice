package com.example.microservice.spring.configuration;

import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.mapper.TaskMapper;
import com.example.microservice.application.mapper.WorkerMapper;
import com.example.microservice.application.port.out.SchedulerRepository;
import com.example.microservice.application.port.out.TaskRepository;
import com.example.microservice.application.port.out.WorkerRepository;
import com.example.microservice.application.service.SchedulerService;
import com.example.microservice.application.service.TaskService;
import com.example.microservice.application.service.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ServiceConfiguration {

    private final TaskRepository taskRepository;

    private final WorkerRepository workerRepository;

    @Bean
    SchedulerService schedulerService(SchedulerRepository schedulerRepository,
                                             SchedulerMapper schedulerMapper) {
        return new SchedulerService(schedulerRepository, taskRepository, workerRepository, schedulerMapper);
    }

    @Bean
    TaskService taskService(TaskMapper taskMapper) {
        return new TaskService(taskRepository, taskMapper);
    }

    @Bean
    WorkerService workerService(WorkerMapper workerMapper) {
        return new WorkerService(workerRepository, workerMapper);
    }
}
