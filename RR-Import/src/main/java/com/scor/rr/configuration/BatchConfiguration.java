package com.scor.rr.configuration;

import com.google.gson.Gson;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.scope.JobScope;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Autowired
    @Qualifier(value = "dbRms")
    private DataSource dataSource;

    @Bean(name = "rrTransactionManager")
    public DataSourceTransactionManager getDataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }


    @Bean(name = "rrJobRepository")
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setTransactionManager(getDataSourceTransactionManager());
        factory.setDataSource(dataSource);
        return factory.getObject();
    }


    @Bean
    public SimpleJobLauncher getJobLauncher() throws Exception {

        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(getJobRepository());
//        simpleJobLauncher.setTaskExecutor(threadPoolTaskExecutor());
        return simpleJobLauncher;
    }

    @Bean
    public Gson getGson() {
        return new Gson();
    }

//    @Bean
//    public TaskExecutor threadPoolTaskExecutor() {
//        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
//        executor.setConcurrencyLimit(4);
//        executor.setThreadNamePrefix("default_task_executor_thread");
//        return executor;
//    }
}
