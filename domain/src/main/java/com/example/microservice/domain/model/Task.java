package com.example.microservice.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.Duration;

/**
 * Información de la tarea a realizar
 */
@Getter
@Builder
public class Task {

    private final String code;

    private final String description;

    private final Priority priority;

    private final Duration duration;

    private Status status;

    /**
     * Compara una tarea con el objeto recibido como parámetro, si este es un objeto del tipo {@link Task} y tiene el
     * mismo código de tarea se considerará que son iguales.
     * @param o {@link Object} Objeto a comparar.
     * @return Devuelve {@link true } si tienen el mísmo código de tarea, {@link false} en otro caso
     */
    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof Task task)) return false;
        return code.equals(task.code);
    }

    @Override
    public int hashCode() {
        return code.hashCode();
    }

    protected void setStatus(Status status) {
        this.status = status;
    }
}
