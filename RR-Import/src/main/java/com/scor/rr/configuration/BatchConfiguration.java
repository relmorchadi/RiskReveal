package com.scor.rr.configuration;

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
public class BatchConfiguration {


    @Autowired
    @Qualifier(value = "dbRms")
    private DataSource dataSource;

    @Bean
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
        //simpleJobLauncher.setTaskExecutor(getTaskExecutor());

        return simpleJobLauncher;
    }

//    @Bean(value = "myJobService")
//    public SimpleJobServiceFactoryBean getJobServiceFactory() throws Exception{
//        SimpleJobServiceFactoryBean simpleJobServiceFactoryBean = new SimpleJobServiceFactoryBean();
//
//        simpleJobServiceFactoryBean.setDataSource(getDataSource());
//        simpleJobServiceFactoryBean.setJobLauncher(getJobLauncher());
//        simpleJobServiceFactoryBean.setJobLocator(getJobRegistry());
//        simpleJobServiceFactoryBean.setJobRepository(getJobRepository());
//
//        return simpleJobServiceFactoryBean;
//    }
}
