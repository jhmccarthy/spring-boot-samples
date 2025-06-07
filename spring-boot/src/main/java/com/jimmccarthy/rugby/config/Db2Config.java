package com.jimmccarthy.rugby.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariDataSource;

/**
 * This is an example of a DB2 configuration.
 */
//@Configuration
public class Db2Config {
    @Bean
    @ConfigurationProperties(prefix = "spring.db2-datasource")
    public DataSourceProperties db2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "db2DataSource")
    @ConfigurationProperties("spring.db2-datasource.configuration")
    public DataSource db2DataSource() {
        return db2DataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}
