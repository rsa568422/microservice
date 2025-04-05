package com.example.microservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Builder
public class DailyScheduler {

    @Getter
    private final LocalDate date;

    private final Set<Task> tasks = new HashSet<>();

    public Set<Task> getTasks() {
        return Set.copyOf(tasks);
    }

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

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public boolean isFull() {
        return Duration.ofMinutes(8).compareTo(total()) <= 0;
    }

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
