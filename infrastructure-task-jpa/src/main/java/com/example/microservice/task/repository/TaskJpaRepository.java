package com.example.microservice.task.repository;

import com.example.microservice.task.entity.TaskEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static com.example.microservice.task.configuration.Constants.JPA_TRANSACTION_MANAGER;

@Transactional(JPA_TRANSACTION_MANAGER)
public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {

    List<TaskEntity> findByStatusOrderByPriorityAsc(@NonNull String status);
}
