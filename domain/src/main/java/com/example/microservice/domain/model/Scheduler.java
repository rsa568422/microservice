package com.example.microservice.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Builder
public class Scheduler {

    @Getter
    private final UUID code;

    @Getter
    private final LocalDate date;

    @Getter
    private final Worker worker;

    private final List<Task> tasks = new LinkedList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public List<Task> getTasks() {
        return List.copyOf(tasks);
    }
}
