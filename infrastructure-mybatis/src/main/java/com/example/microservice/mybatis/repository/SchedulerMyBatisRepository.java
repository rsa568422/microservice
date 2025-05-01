package com.example.microservice.mybatis.repository;

import com.example.microservice.mybatis.entity.SchedulerEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchedulerMyBatisRepository {

    //@Select("SELECT year, month, day, task FROM schedulers WHERE year = #{year} AND month = #{month} AND day = #{day}")
    List<SchedulerEntity> findByDate(@Param("year") int year, @Param("month") int month, @Param("day") int day);

    //@Insert("INSERT INTO schedulers (year, month, day, task) VALUES (#{year}, #{month}, #{day}, #{task})")
    void save(SchedulerEntity scheduler);
}
