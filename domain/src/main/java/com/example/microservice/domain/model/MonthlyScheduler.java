package com.example.microservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.time.DayOfWeek;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Builder
public class MonthlyScheduler {

    @Getter
    private final Year year;

    @Getter
    private final Month month;

    private final Map<Integer, DailyScheduler> days = new HashMap<>();

    public void add(@NonNull DailyScheduler scheduler) {
        if (scheduler.isEmpty()) {
            throw new IllegalArgumentException("Can not add daily scheduler without tasks");
        }
        if (year.getValue() != scheduler.getDate().getYear()) {
            throw new IllegalArgumentException("Daily scheduler year not equals to monthly scheduler year");
        }
        if (!month.equals(scheduler.getDate().getMonth())) {
            throw new IllegalArgumentException("Daily scheduler month not equals to monthly scheduler month");
        }
        if (days.containsKey(scheduler.getDate().getDayOfMonth())) {
            throw new IllegalArgumentException("A schedule is already available for that day");
        }
        final var day = scheduler.getDate().getDayOfWeek();
        if (WEEKEND.contains(day)) {
            throw new IllegalArgumentException("Weekends are non-working days");
        }
        days.put(scheduler.getDate().getDayOfMonth(), scheduler);
    }

    public Optional<DailyScheduler> get(int day) {
        return Optional.ofNullable(days.get(day));
    }

    @Override
    public final boolean equals(Object o) {
        if (!(o instanceof MonthlyScheduler that)) return false;
        return year.equals(that.year) && month == that.month;
    }

    @Override
    public int hashCode() {
        int result = year.hashCode();
        result = 31 * result + month.hashCode();
        return result;
    }

    private static final Set<DayOfWeek> WEEKEND = Set.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);
}
