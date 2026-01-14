package com.example.microservice.scheduler.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Function;

import static com.example.microservice.scheduler.configuration.Constants.JDBC;
import static com.example.microservice.scheduler.configuration.Constants.JDBC_DATASOURCE;
import static com.example.microservice.scheduler.configuration.Constants.JDBC_DATASOURCE_PROPERTIES;
import static com.example.microservice.scheduler.configuration.Constants.JDBC_ENTITY_MANAGER_FACTORY;
import static com.example.microservice.scheduler.configuration.Constants.JDBC_ENTITY_PACKAGE;
import static com.example.microservice.scheduler.configuration.Constants.JDBC_REPOSITORY_PACKAGE;
import static com.example.microservice.scheduler.configuration.Constants.JDBC_TRANSACTION_MANAGER;

@Configuration
@EnableJpaRepositories(
        basePackages = JDBC_REPOSITORY_PACKAGE,
        entityManagerFactoryRef = JDBC_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = JDBC_TRANSACTION_MANAGER
)
public class JdbcConfiguration {

    @Bean(name = JDBC_DATASOURCE)
    @ConfigurationProperties(prefix = JDBC_DATASOURCE_PROPERTIES)
    DataSource jdbcDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = JDBC_ENTITY_MANAGER_FACTORY)
    LocalContainerEntityManagerFactoryBean jdbcEntityManagerFactory(
            @Qualifier(JDBC_DATASOURCE) DataSource dataSource,
            @Qualifier("jdbcEntityManagerFactoryBuilder") EntityManagerFactoryBuilder builder
    ) {
        return builder
                .dataSource(dataSource)
                .packages(JDBC_ENTITY_PACKAGE)
                .persistenceUnit(JDBC)
                .build();
    }

    @Bean(name = JDBC_TRANSACTION_MANAGER)
    PlatformTransactionManager jdbcTransactionManager(
            @Qualifier(JDBC_ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory
    ) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    EntityManagerFactoryBuilder jdbcEntityManagerFactoryBuilder(
            @Qualifier("jdbcVendorAdapter") JpaVendorAdapter jpaVendorAdapter,
            ObjectProvider<PersistenceUnitManager> persistenceUnitManager
    ) {
        final Function<DataSource, Map<String, ?>> function = dataSource -> Map.of("scheduler", dataSource);
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, function, persistenceUnitManager.getIfAvailable());
    }

    @Bean
    JpaVendorAdapter jdbcVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier(JDBC_DATASOURCE) DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}