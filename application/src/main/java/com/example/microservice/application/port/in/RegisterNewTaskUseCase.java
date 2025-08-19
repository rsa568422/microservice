package com.example.microservice.application.port.in;

import com.example.microservice.application.dto.NewTaskDTO;
import lombok.NonNull;

public interface RegisterNewTaskUseCase {

    void registerNewTask(@NonNull NewTaskDTO newTask);
}
