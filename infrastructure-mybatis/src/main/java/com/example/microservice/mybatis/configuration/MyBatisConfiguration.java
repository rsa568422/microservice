package com.example.microservice.mybatis.configuration;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(
        basePackages = "com.example.microservice.mybatis.repository",
        sqlSessionFactoryRef = "mybatisSqlSessionFactory"
)
public class MyBatisConfiguration {

    @Bean(name = "mybatisDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.mybatis")
    public DataSource mybatisDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "mybatisSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(
            @Qualifier("mybatisDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage("com.example.microservice.mybatis.entity");
        factory.setConfiguration(mybatisConfiguration());
        return factory.getObject();
    }

    private org.apache.ibatis.session.Configuration mybatisConfiguration() {
        var configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(true);
        configuration.setLazyLoadingEnabled(true);
        configuration.setDefaultFetchSize(100);
        return configuration;
    }

    @Bean(name = "mybatisTransactionManager")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("mybatisDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
