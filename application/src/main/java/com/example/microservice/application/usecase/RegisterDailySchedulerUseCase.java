package com.example.microservice.application.usecase;

import com.example.microservice.application.dto.NewDailyScheduler;
import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.service.SchedulerService;
import com.example.microservice.application.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class RegisterDailySchedulerUseCase {

    private final TaskService taskService;

    private final SchedulerService schedulerService;

    private final SchedulerMapper schedulerMapper;

    /**
     * Se registra una nueva planificación diaria, dando error si alguno de los códigos de tarea no pertenece a una
     * tarea previamente registrada.
     * @param scheduler {@link NewDailyScheduler} Información de la nueva planificación.
     */
    public void execute(@NonNull NewDailyScheduler scheduler) {
        final var tasks = scheduler.taskCodes()
                .stream()
                .map(taskService::findByCode)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        if (tasks.size() < scheduler.taskCodes().size()) {
            throw new IllegalArgumentException("The daily scheduler contains not registered tasks");
        }
        schedulerService.save(schedulerMapper.toDailyScheduler(scheduler.date(), tasks));
        tasks.forEach(taskService::save);
    }
}
