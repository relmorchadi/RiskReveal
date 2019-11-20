package com.scor.rr.configuration;

import com.google.gson.Gson;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Autowired
    @Qualifier(value = "dbRms")
    private DataSource dataSource;

    @Bean(name = "rrTransactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }


    @Bean
    public JobRepository getJobRepository() throws Exception{
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setTransactionManager(getDataSourceTransactionManager());
        factory.setDataSource(dataSource);
        return factory.getObject();
    }


    @Bean
    public SimpleJobLauncher getJobLauncher() throws Exception{

        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(getJobRepository());
        return simpleJobLauncher;
    }

    @Bean
    public Gson getGson(){
        return new Gson();
    }
}
