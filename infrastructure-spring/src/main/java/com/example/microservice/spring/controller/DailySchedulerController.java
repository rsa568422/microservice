package com.example.microservice.spring.controller;

import com.example.microservice.application.dto.NewDailyScheduler;
import com.example.microservice.application.dto.TaskToAdd;
import com.example.microservice.application.usecase.AddTaskToDailySchedulerUseCase;
import com.example.microservice.application.usecase.GetDailySchedulerUseCase;
import com.example.microservice.application.usecase.RegisterDailySchedulerUseCase;
import com.example.microservice.domain.model.DailyScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/daily")
public class DailySchedulerController {

    private final AddTaskToDailySchedulerUseCase addTaskToDailySchedulerUseCase;

    private final GetDailySchedulerUseCase getDailySchedulerUseCase;

    private final RegisterDailySchedulerUseCase registerDailySchedulerUseCase;

    @PostMapping("/add")
    public ResponseEntity<Void> add(@RequestBody TaskToAdd task) {
        addTaskToDailySchedulerUseCase.execute(task.date(), task.code());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{date}")
    public ResponseEntity<DailyScheduler> get(@PathVariable("date") @DateTimeFormat(iso = DATE) LocalDate date) {
        return ResponseEntity.of(getDailySchedulerUseCase.execute(date));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody NewDailyScheduler scheduler) {
        registerDailySchedulerUseCase.execute(scheduler);
        return ResponseEntity.ok().build();
    }
}
