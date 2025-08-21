package com.example.microservice.worker.repository;

import com.example.microservice.worker.entity.WorkerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper
public interface WorkerMyBatisRepository {

    WorkerEntity findByCode(UUID code);

    List<WorkerEntity> findAll();

    void insert(WorkerEntity worker);

    void update(WorkerEntity worker);
}
