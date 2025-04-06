package com.example.microservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Planificación diaria
 */
@Builder
public class DailyScheduler {

    @Getter
    private final LocalDate date;

    private final Set<Task> tasks = new HashSet<>();

    public Set<Task> getTasks() {
        return Set.copyOf(tasks);
    }

    /**
     * Agrega una tarea a la planificación diaria. Dará un error si la tarea no se encuentra en el estado
     * {@link com.example.microservice.domain.model.Status#PENDING}, si la duración de la tarea sobrepasa el tiempo
     * restante de la jornada o si la tarea ya está planificada para ese día.
     * @param task {@link Task} Tarea a agregar a la planificación.
     */
    public void add(@NonNull Task task) {
        if (!Status.PENDING.equals(task.getStatus())) {
            throw new IllegalArgumentException("Only pending tasks can be added to the scheduler");
        }
        final var estimation = Duration.ofMinutes(total().toMinutes() + task.getDuration().toMinutes());
        if (Duration.ofHours(8).compareTo(estimation) < 0) {
            throw new IllegalArgumentException("Total planning exceeds eight hours");
        }
        if (tasks.stream().map(Task::getCode).toList().contains(task.getCode())) {
            throw new IllegalArgumentException("The task is already added to de scheduler");
        }
        task.setStatus(Status.WORKING);
        tasks.add(task);
    }


    /**
     * Consulta si la planificación no tiene tareas asignadas.
     * @return {@link true} si la planificación no tiene tareas asignadas, {@link false} en caso contrario.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Consulta si la planificación tiene tareas para completar la jornada.
     * @return {@link true} si la planificación tiene tareas con una duración total de ocho horas o más, {@link false}
     * en caso contrario.
     */
    public boolean isFull() {
        return Duration.ofHours(8).compareTo(total()) <= 0;
    }

    /**
     * Compara una planificación diaria con el objeto recibido como parámetro, si este es un objeto del tipo
     * {@link DailyScheduler} y tiene la misma fecha se considerará que son iguales.
     * @param o {@link Object} Objeto a comparar.
     * @return Devuelve {@link true } si tienen la misma fecha, {@link false} en otro caso
     */
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof DailyScheduler that)) return false;
        return getDate().equals(that.getDate());
    }

    @Override
    public int hashCode() {
        return getDate().hashCode();
    }

    private Duration total() {
        final var minutes = tasks.stream()
                .map(Task::getDuration)
                .map(Duration::toMinutes)
                .reduce(0L, Long::sum);
        return Duration.ofMinutes(minutes);
    }
}
