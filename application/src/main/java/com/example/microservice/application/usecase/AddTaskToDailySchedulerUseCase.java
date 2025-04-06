package com.example.microservice.application.usecase;

import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.service.SchedulerService;
import com.example.microservice.application.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
public class AddTaskToDailySchedulerUseCase {

    private final TaskService taskService;

    private final SchedulerService schedulerService;

    private final SchedulerMapper schedulerMapper;

    /**
     * Agrega una tarea a la planificación diaria. La tarea debe haber sido registrada previamente. La planificación
     * diaria es actualizada o se genera una nueva si no existe.
     * @param date @{link LocalDate} Fecha de la planificación diaria.
     * @param taskCode @{@link String} Código de la tarea a agregar.
     */
    public void execute(@NonNull LocalDate date, @NonNull String taskCode) {
        final var task = taskService.findByCode(taskCode)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));
        final var scheduler = schedulerService.findByDate(date).orElse(schedulerMapper.toDailyScheduler(date));
        scheduler.add(task);
        taskService.save(task);
        schedulerService.save(scheduler);
    }
}
