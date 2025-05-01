package com.example.microservice.jpa.configuration;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constants {

    public static final String HIBERNATE_DIALECT = "hibernate.dialect";

    public static final String HIBERNATE_HBM_2_DDL_AUTO = "hibernate.hbm2ddl.auto";

    public static final String JPA = "jpa";

    public static final String JPA_DATASOURCE = "jpaDataSource";

    public static final String JPA_DATASOURCE_PROPERTIES = "spring.datasource.jpa";

    public static final String JPA_ENTITY_MANAGER_FACTORY = "jpaEntityManagerFactory";

    public static final String JPA_ENTITY_PACKAGE = "com.example.microservice.jpa.entity";

    public static final String JPA_REPOSITORY_PACKAGE = "com.example.microservice.jpa.repository";

    public static final String JPA_TRANSACTION_MANAGER = "jpaTransactionManager";

    public static final Object POSTGRES_SQL_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";

    public static final Object UPDATE = "update";
}
