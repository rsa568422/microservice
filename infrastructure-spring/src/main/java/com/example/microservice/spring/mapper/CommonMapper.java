package com.example.microservice.spring.mapper;

import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import org.mapstruct.Mapper;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface CommonMapper {

    default String toString(Duration duration) {
        if (Objects.isNull(duration)) {
            return null;
        }
        final var hours = duration.toHours();
        if (hours > 0) {
            final var minutes = duration.minus(Duration.of(hours, ChronoUnit.HOURS)).toMinutes();
            if (minutes > 0) {
                return "%d h %d min".formatted(hours, minutes);
            }
            return "%d h".formatted(hours);
        }
        return "%d min".formatted(duration.toMinutes());
    }

    default String toString(Priority priority) {
        return switch (priority) {
            case LOW -> "baja";
            case MEDIUM -> "media";
            case HIGH -> "alta";
            case CRITICAL -> "crítica";
        };
    }

    default String toString(Status status) {
        return switch (status) {
            case PENDING -> "pendiente";
            case WORKING -> "en progreso";
        };
    }

    default String toString(UUID uuid) {
        return uuid.toString();
    }
}
