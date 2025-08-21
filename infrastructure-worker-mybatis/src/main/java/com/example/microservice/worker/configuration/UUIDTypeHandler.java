package com.example.microservice.worker.configuration;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.*;
import java.util.UUID;

public class UUIDTypeHandler extends BaseTypeHandler<UUID> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, UUID parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter, Types.OTHER);
    }

    @Override
    public UUID getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Object object = rs.getObject(columnName);
        if (object instanceof UUID) {
            return (UUID) object;
        } else if (object instanceof String) {
            return UUID.fromString((String) object);
        }
        return null;
    }

    @Override
    public UUID getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Object object = rs.getObject(columnIndex);
        if (object instanceof UUID) {
            return (UUID) object;
        } else if (object instanceof String) {
            return UUID.fromString((String) object);
        }
        return null;
    }

    @Override
    public UUID getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Object object = cs.getObject(columnIndex);
        if (object instanceof UUID) {
            return (UUID) object;
        } else if (object instanceof String) {
            return UUID.fromString((String) object);
        }
        return null;
    }
}
