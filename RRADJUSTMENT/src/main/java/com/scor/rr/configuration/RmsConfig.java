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


@Configuration
public class RmsConfig {

    @Bean(name = "dbRr")
    @ConfigurationProperties(prefix = "spring.datasource")
    @Primary
    public DataSource createProductServiceDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "dbRms")
    @ConfigurationProperties(prefix = "rms.datasource")
    public DataSource createRmsDataSource() {
        return DataSourceBuilder.create().build();
    }



    @Bean(name = "jdbcRms")
    @Autowired
    public JdbcTemplate createJdbcTemplateRms(@Qualifier("dbRms") DataSource rmsDS) {
        return new JdbcTemplate(rmsDS);
    }

    @Bean(name = "jdbcRr")
    @Autowired
    public JdbcTemplate createJdbcTemplateRr(@Qualifier("dbRr") DataSource rrDS) {
        return new JdbcTemplate(rrDS);
    }


}
