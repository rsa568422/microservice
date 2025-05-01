package com.example.microservice.jpa.repository;

import com.example.microservice.jpa.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.example.microservice.jpa.configuration.Constants.JPA_TRANSACTION_MANAGER;

@Transactional(JPA_TRANSACTION_MANAGER)
public interface TaskJpaRepository extends JpaRepository<TaskEntity, UUID> {
}
