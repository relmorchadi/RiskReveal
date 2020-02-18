package com.scor.rr.configuration;

import com.scor.rr.service.LocationLevelExposure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
public class RmsConfig {

    @Value(value = "${rms.ds.dbname}")
    private String dbName;

    @Primary
    @Bean(name = "dataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dbRms")
    @ConfigurationProperties(prefix = "rms.datasource")
    public DataSource createRmsDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name="rmsTransactionManager")
    public DataSourceTransactionManager getRmsTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(createRmsDataSource());
        dataSourceTransactionManager.setEnforceReadOnly(true);
        return dataSourceTransactionManager;
    }

    @Bean
    public LocationLevelExposure getLocationLevelExposure(){
        return new LocationLevelExposure(dbName);
    }
}
