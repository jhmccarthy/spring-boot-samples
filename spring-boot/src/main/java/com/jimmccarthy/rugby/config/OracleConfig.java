package com.jimmccarthy.rugby.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.zaxxer.hikari.HikariDataSource;

/**
 * This is an example of an Oracle configuration.
 */
//@Configuration
public class OracleConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.oracle-datasource")
    public DataSourceProperties oracleDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "oracleDataSource")
    @ConfigurationProperties("spring.oracle-datasource.configuration")
    public DataSource oracleDataSource() {
        return oracleDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class)
            .build();
    }
}
