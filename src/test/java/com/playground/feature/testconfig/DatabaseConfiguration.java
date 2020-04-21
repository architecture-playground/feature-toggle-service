package com.playground.feature.testconfig;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.Duration;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public PostgreSQLContainer<?> postgreSQLContainer() {
        PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:9.6.2")
                .withUsername("postgres")
                .withPassword("system")
                .withStartupTimeout(Duration.ofSeconds(30));

        container.start();
        return container;
    }

    @Bean
    @ConditionalOnBean(name = "postgreSQLContainer")
    public DataSource dataSource(PostgreSQLContainer<?> postgreSQLContainer) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(postgreSQLContainer.getJdbcUrl());
        dataSource.setUsername(postgreSQLContainer.getUsername());
        dataSource.setPassword(postgreSQLContainer.getPassword());
        return dataSource;
    }
}
