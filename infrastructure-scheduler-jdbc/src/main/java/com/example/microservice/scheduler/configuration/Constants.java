package com.example.microservice.scheduler.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String HIBERNATE_DIALECT = "hibernate.dialect";

    public static final String HIBERNATE_HBM_2_DDL_AUTO = "hibernate.hbm2ddl.auto";

    public static final String JDBC = "scheduler";

    public static final String JDBC_DATASOURCE = "jdbcDataSource";

    public static final String JDBC_DATASOURCE_PROPERTIES = "spring.datasource.scheduler";

    public static final String JDBC_ENTITY_MANAGER_FACTORY = "jdbcEntityManagerFactory";

    public static final String JDBC_ENTITY_PACKAGE = "com.example.microservice.scheduler.entity";

    public static final String JDBC_REPOSITORY_PACKAGE = "com.example.microservice.scheduler.repository";

    public static final String JDBC_TRANSACTION_MANAGER = "jdbcTransactionManager";

    public static final Object POSTGRES_SQL_DIALECT = "org.postgresql.Driver";

    public static final Object UPDATE = "update";
}
