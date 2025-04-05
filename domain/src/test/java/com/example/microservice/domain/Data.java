package com.example.microservice.domain;

import com.example.microservice.domain.model.DailyScheduler;
import com.example.microservice.domain.model.MonthlyScheduler;
import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import com.example.microservice.domain.model.Task;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Set;

public class Data {

    public static final Task.TaskBuilder TASK_1_1 = builder(1)
            .priority(Priority.CRITICAL)
            .duration(Duration.ofHours(4));

    public static final Task.TaskBuilder TASK_1_1B = Task.builder()
            .code(String.format("CODE#%d", 1))
            .description(String.format("description of task %d", 1))
            .status(Status.WORKING)
            .priority(Priority.LOW)
            .duration(Duration.ofHours(1));

    public static final Task.TaskBuilder TASK_1_2 = builder(2)
            .priority(Priority.MEDIUM)
            .duration(Duration.ofHours(2));

    public static final Task.TaskBuilder TASK_1_3 = builder(3)
            .priority(Priority.MEDIUM)
            .duration(Duration.ofHours(2));

    public static Set<Task> TASKS_1(){
        return Set.of( TASK_1_1.build(), TASK_1_2.build(), TASK_1_3.build() );
    }

    public static final DailyScheduler DAILY_SCHEDULER_1 = build(LocalDate.of(2025, Month.JANUARY, 1), TASKS_1());

    private static Task.TaskBuilder builder(int n) {
        return Task.builder()
                .code(String.format("CODE#%d", n))
                .description(String.format("description of task %d", n))
                .status(Status.PENDING);
    }

    public static MonthlyScheduler.MonthlySchedulerBuilder builder() {
        return MonthlyScheduler.builder()
                .year(Year.of(2025))
                .month(Month.JANUARY);
    }

    private static DailyScheduler build(LocalDate date, Set<Task> tasks) {
        final var scheduler = DailyScheduler.builder().date(date).build();
        tasks.forEach(scheduler::add);
        return scheduler;
    }
}
