package com.example.microservice.spring.mapper;

import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Status;
import org.mapstruct.Mapper;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

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

    default Duration toDuration(String duration) {
        if (Objects.isNull(duration)) {
            return null;
        }
        if (Pattern.matches("^\\d h \\d min$", duration)) {
            final var parts = duration.split(" h ");
            final var hours = Integer.parseInt(parts[0]);
            final var minutes = Integer.parseInt(parts[1].replace(" min", ""));
            return Duration.of((hours * 60L) + minutes, ChronoUnit.MINUTES);
        } else if (Pattern.matches("^\\d h$", duration)) {
            final var hours = Integer.parseInt(duration.replace(" h", ""));
            return Duration.of(hours, ChronoUnit.HOURS);
        } else if (Pattern.matches("^\\d min$", duration)) {
            final var minutes = Integer.parseInt(duration.replace(" min", ""));
            return Duration.of(minutes, ChronoUnit.MINUTES);
        } else {
            throw new IllegalArgumentException("Invalid duration format");
        }
    }

    default String toString(Priority priority) {
        if (Objects.isNull(priority)) {
            return null;
        }
        return switch (priority) {
            case LOW -> "baja";
            case MEDIUM -> "media";
            case HIGH -> "alta";
            case CRITICAL -> "crítica";
        };
    }

    default Priority toPriority(String priority) {
        if (Objects.isNull(priority)) {
            return null;
        }
        return switch (priority) {
            case "baja" -> Priority.LOW;
            case "media" -> Priority.MEDIUM;
            case "alta" -> Priority.HIGH;
            case "crítica" -> Priority.CRITICAL;
            default -> throw new IllegalArgumentException("Invalid priority");
        };
    }

    default String toString(Status status) {
        if (Objects.isNull(status)) {
            return null;
        }
        return switch (status) {
            case PENDING -> "pendiente";
            case WORKING -> "en progreso";
        };
    }

    default Status toStatus(String status) {
        if (Objects.isNull(status)) {
            return null;
        }
        return switch (status) {
            case "pendiente" -> Status.PENDING;
            case "en progreso" -> Status.WORKING;
            default -> throw new IllegalArgumentException("Invalid status");
        };
    }

    default String toString(UUID uuid) {
        if (Objects.isNull(uuid)) {
            return null;
        }
        return uuid.toString();
    }

    default UUID toUUID(String uuid) {
        if (Objects.isNull(uuid)) {
            return null;
        }
        return UUID.fromString(uuid);
    }
}
