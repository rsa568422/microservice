package com.example.microservice.application.port.in;

import lombok.NonNull;

import java.util.UUID;

public interface AddTasksToSchedulerUseCase {

    void addTasksToScheduler(@NonNull UUID taskCode, @NonNull UUID schedulerCode);
}
