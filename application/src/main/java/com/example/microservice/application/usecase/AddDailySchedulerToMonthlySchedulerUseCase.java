package com.example.microservice.application.usecase;

import com.example.microservice.application.mapper.SchedulerMapper;
import com.example.microservice.application.service.SchedulerService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;

@RequiredArgsConstructor
public class AddDailySchedulerToMonthlySchedulerUseCase {

    private final SchedulerService schedulerService;

    private final SchedulerMapper schedulerMapper;

    /**
     * Se agrega la planificación diaria de la fecha indicada a la planificación mensual. La planificación diaria
     * debe haber sido registrada previamente. La planificación mensual es actualizada si existe o se generará una
     * nueva planificación mensual si no existe.
     * @param date Fecha de la planificación diaria a agregar a la planificación mensual.
     */
    public void execute(@NonNull LocalDate date) {
        final var daily = schedulerService.findByDate(date)
                .orElseThrow(() -> new IllegalArgumentException("Daily scheduler not found for the given date"));
        final var monthly = schedulerService.findByYearAndMont(Year.from(date), Month.from(date))
                .orElse(schedulerMapper.toMonthlyScheduler(date));
        monthly.add(daily);
        schedulerService.save(monthly);
    }
}
