package com.example.microservice.scheduler.repository;

import com.example.microservice.scheduler.entity.SchedulerEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class SchedulerRowMapper implements RowMapper<SchedulerEntity> {

    @Override
    public SchedulerEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return SchedulerEntity.builder()
                .code(rs.getObject(SchedulerJdbcQueries.CODE, UUID.class))
                .date(rs.getDate(SchedulerJdbcQueries.DATE).toLocalDate())
                .worker(rs.getObject(SchedulerJdbcQueries.WORKER, UUID.class))
                .build();
    }
}
