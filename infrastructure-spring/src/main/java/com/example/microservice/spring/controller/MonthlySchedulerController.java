package com.example.microservice.spring.controller;

import com.example.microservice.application.dto.OutputMonthlyScheduler;
import com.example.microservice.application.usecase.GetMonthlySchedulerUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monthly")
public class MonthlySchedulerController {

    private final GetMonthlySchedulerUseCase getMonthlySchedulerUseCase;

    @GetMapping("/{year}-{month}")
    public ResponseEntity<OutputMonthlyScheduler> get(@PathVariable("year") int year,
                                                      @PathVariable("month") int month) {
        return ResponseEntity.of(getMonthlySchedulerUseCase.execute(year, month));
    }
}
