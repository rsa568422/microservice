package com.example.microservice.spring.configuration;

import com.example.microservice.application.service.SchedulerService;
import com.example.microservice.application.service.TaskService;
import com.example.microservice.domain.repository.DailySchedulerRepository;
import com.example.microservice.domain.repository.MonthlySchedulerRepository;
import com.example.microservice.domain.repository.TaskRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {

    @Bean
    public TaskService taskService(TaskRepository taskRepository) {
        return new TaskService(taskRepository);
    }

    @Bean
    public SchedulerService schedulerService(DailySchedulerRepository dailySchedulerRepository,
                                             MonthlySchedulerRepository monthlySchedulerRepository) {
        return new SchedulerService(dailySchedulerRepository, monthlySchedulerRepository);
    }
}
