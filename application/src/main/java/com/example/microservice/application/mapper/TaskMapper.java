package com.example.microservice.application.mapper;

import com.example.microservice.application.dto.NewTask;
import com.example.microservice.application.dto.OutputTask;
import com.example.microservice.domain.model.Priority;
import com.example.microservice.domain.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Duration;
import java.util.Map;
import java.util.Set;

@Mapper
public interface TaskMapper {

    @Mapping(target = "code", expression = "java(null)")
    @Mapping(target = "status", constant = "PENDING")
    @Mapping(target = "duration", source = "duration")
    Task toTask(NewTask newTask);

    @Mapping(target = "duration", source = "duration")
    OutputTask toOutputTask(Task task);

    Set<OutputTask> toOutputSet(Set<Task> tasks);

    Map<Priority, Set<OutputTask>> toOutputMap(Map<Priority, Set<Task>> map);

    default Duration toDuration(Integer duration) {
        return Duration.ofMinutes(duration);
    }

    default String toString(Duration duration) {
        return duration.toString().substring(2);
    }
}
