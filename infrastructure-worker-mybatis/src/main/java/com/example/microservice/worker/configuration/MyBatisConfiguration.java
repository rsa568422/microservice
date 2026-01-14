package com.example.microservice.worker.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.UUID;

@Configuration
@MapperScan(
        basePackages = "com.example.microservice.worker.repository",
        sqlSessionFactoryRef = "mybatisSqlSessionFactory"
)
public class MyBatisConfiguration {

    @Bean(name = "mybatisDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.worker")
    DataSource mybatisDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mybatisSqlSessionFactory")
    SqlSessionFactory sqlSessionFactory(
            @Qualifier("mybatisDataSource") DataSource dataSource,
            @Qualifier("myBatisPathMatchingResourcePatternResolver") Resource[] mappers
    ) throws Exception {
        final var configuration = mybatisConfiguration();
        configuration.getTypeHandlerRegistry().register(UUID.class, new UUIDTypeHandler());
        final var factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("com.example.microservice.worker.entity");
        factory.setMapperLocations(mappers);
        factory.setConfiguration(configuration);
        return factory.getObject();
    }

    @Bean(name = "mybatisTransactionManager")
    DataSourceTransactionManager transactionManager(
            @Qualifier("mybatisDataSource") DataSource dataSource
    ) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "myBatisPathMatchingResourcePatternResolver")
    Resource[] myBatisPathMatchingResourcePatternResolver() throws IOException {
        return new PathMatchingResourcePatternResolver()
                .getResources("classpath:com/example/microservice/worker/repository/*.xml");
    }

    private static org.apache.ibatis.session.Configuration mybatisConfiguration() {
        final var configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLazyLoadingEnabled(true);
        configuration.setDefaultFetchSize(100);
        return configuration;
    }
}
