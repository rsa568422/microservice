package com.example.microservice.jpa.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Function;

import static com.example.microservice.jpa.configuration.Constants.HIBERNATE_DIALECT;
import static com.example.microservice.jpa.configuration.Constants.HIBERNATE_HBM_2_DDL_AUTO;
import static com.example.microservice.jpa.configuration.Constants.JPA;
import static com.example.microservice.jpa.configuration.Constants.JPA_DATASOURCE;
import static com.example.microservice.jpa.configuration.Constants.JPA_DATASOURCE_PROPERTIES;
import static com.example.microservice.jpa.configuration.Constants.JPA_ENTITY_MANAGER_FACTORY;
import static com.example.microservice.jpa.configuration.Constants.JPA_ENTITY_PACKAGE;
import static com.example.microservice.jpa.configuration.Constants.JPA_REPOSITORY_PACKAGE;
import static com.example.microservice.jpa.configuration.Constants.JPA_TRANSACTION_MANAGER;
import static com.example.microservice.jpa.configuration.Constants.MYSQL_SQL_DIALECT;
import static com.example.microservice.jpa.configuration.Constants.UPDATE;

@Configuration
@EnableJpaRepositories(
        basePackages = JPA_REPOSITORY_PACKAGE,
        entityManagerFactoryRef = JPA_ENTITY_MANAGER_FACTORY,
        transactionManagerRef = JPA_TRANSACTION_MANAGER
)
public class JpaConfiguration {

    @Bean(name = JPA_DATASOURCE)
    @ConfigurationProperties(prefix = JPA_DATASOURCE_PROPERTIES)
    public DataSource jpaDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = JPA_ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(@Qualifier(JPA_DATASOURCE)
                                                                          DataSource dataSource,
                                                                          EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource)
                .packages(JPA_ENTITY_PACKAGE)
                .persistenceUnit(JPA)
                .properties(Map.of(HIBERNATE_HBM_2_DDL_AUTO, UPDATE, HIBERNATE_DIALECT, MYSQL_SQL_DIALECT))
                .build();
    }

    @Bean(name = JPA_TRANSACTION_MANAGER)
    public PlatformTransactionManager jpaTransactionManager(@Qualifier(JPA_ENTITY_MANAGER_FACTORY)
                                                            EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(
            JpaVendorAdapter jpaVendorAdapter,
            ObjectProvider<PersistenceUnitManager> persistenceUnitManager) {
        final Function<DataSource, Map<String, ?>> function = dataSource -> Map.of("jpa", dataSource);
        return new EntityManagerFactoryBuilder(jpaVendorAdapter, function, persistenceUnitManager.getIfAvailable());
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
}