package com.example.microservice.spring.configuration;

import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.mapper.TaskMapper;
import com.example.microservice.application.service.SchedulerService;
import com.example.microservice.application.service.TaskService;
import com.example.microservice.application.usecase.AddTaskToDailySchedulerUseCase;
import com.example.microservice.application.usecase.GetDailySchedulerUseCase;
import com.example.microservice.application.usecase.GetMonthlySchedulerUseCase;
import com.example.microservice.application.usecase.GetPendingTasksByPriorityUseCase;
import com.example.microservice.application.usecase.RegisterDailySchedulerUseCase;
import com.example.microservice.application.usecase.RegisterTaskUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfiguration {

    @Bean
    public AddTaskToDailySchedulerUseCase addTaskToDailySchedulerUseCase(TaskService taskService,
                                                                         SchedulerService schedulerService,
                                                                         SchedulerMapper schedulerMapper) {
        return new AddTaskToDailySchedulerUseCase(taskService, schedulerService, schedulerMapper);
    }

    @Bean
    public GetDailySchedulerUseCase getDailySchedulerUseCase(SchedulerService schedulerService) {
        return new GetDailySchedulerUseCase(schedulerService);
    }

    @Bean
    public GetMonthlySchedulerUseCase getMonthlySchedulerUseCase(SchedulerService schedulerService,
                                                                 SchedulerMapper schedulerMapper) {
        return new GetMonthlySchedulerUseCase(schedulerService, schedulerMapper);
    }

    @Bean
    public GetPendingTasksByPriorityUseCase getPendingTasksByPriorityUseCase(TaskService taskService,
                                                                             TaskMapper taskMapper) {
        return new GetPendingTasksByPriorityUseCase(taskService, taskMapper);
    }

    @Bean
    public RegisterDailySchedulerUseCase registerDailySchedulerUseCase(TaskService taskService,
                                                                       SchedulerService schedulerService,
                                                                       SchedulerMapper schedulerMapper) {
        return new RegisterDailySchedulerUseCase(taskService, schedulerService, schedulerMapper);
    }

    @Bean
    public RegisterTaskUseCase registerTaskUseCase(TaskService taskService, TaskMapper taskMapper) {
        return new RegisterTaskUseCase(taskService, taskMapper);
    }
}
