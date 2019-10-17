package com.scor.rr.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by u004602 on 01/10/2019.
 */
@Configuration
public class RRConfig {
    @Bean(name = "dbRr")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource createProductServiceDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "jdbcRr")
    @Autowired
    public JdbcTemplate createJdbcTemplateRr(@Qualifier("dbRr") DataSource rrDS) {
        return new JdbcTemplate(rrDS);
    }
}
