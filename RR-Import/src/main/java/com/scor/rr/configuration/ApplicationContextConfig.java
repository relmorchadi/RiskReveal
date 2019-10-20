package com.scor.rr.configuration;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.batch.admin.service.SimpleJobServiceFactoryBean;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ApplicationContextConfig {


    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext context;

    @Bean()
    public GuavaCacheManager getCacheManager() {
        return new GuavaCacheManager();
    }

    @Bean(value = "jobLauncherTaskExecutor")
    public TaskExecutor getTaskExecutor(){

        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(Integer.parseInt(env.getProperty("batch.job.executor.pool.size")));
        return taskExecutor;
    }

    @Bean
    public DataSourceTransactionManager getDataSourceTransactionManager(){
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(getDataSource());
        return dataSourceTransactionManager;
    }

    @Bean(value = "myDataSource")
    public BasicDataSource getDataSource(){
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(env.getProperty("batch.jdbc.driver"));
        basicDataSource.setUrl(env.getProperty("batch.jdbc.url"));
        basicDataSource.setUsername(env.getProperty("batch.jdbc.user"));
        basicDataSource.setPassword(env.getProperty("batch.jdbc.password"));
        basicDataSource.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("batch.jdbc.testWhileIdle")));
        basicDataSource.setValidationQuery(env.getProperty("validationQuery"));
        return basicDataSource;
    }

    @Bean(value = "myJobRepository")
    public JobRepository getJobRepository() throws Exception{
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.setTransactionManager(getDataSourceTransactionManager());
        return factory.getObject();
    }

    @Bean(value = "myJobRegistry")
    public JobRegistry getJobRegistry() throws Exception{
        MapJobRegistry jobRegistry = new MapJobRegistry();
        Job importLossData = context.getBean("importLossData", Job.class);
        jobRegistry.register(new ReferenceJobFactory(importLossData));
        return jobRegistry;
    }

//    @Bean
//    public JobExplorer getJobExplorer() throws Exception {
//        JobExplorerFactoryBean fac = new JobExplorerFactoryBean();
//        fac.setDataSource(getDataSource());
//        return fac.getObject();
//    }

    @Bean(value = "myJobLauncher")
    public SimpleJobLauncher getJobLauncher() throws Exception{

        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(getJobRepository());
        simpleJobLauncher.setTaskExecutor(getTaskExecutor());

        return simpleJobLauncher;
    }

    @Bean(value = "myJobService")
    public SimpleJobServiceFactoryBean getJobServiceFactory() throws Exception{
        SimpleJobServiceFactoryBean simpleJobServiceFactoryBean = new SimpleJobServiceFactoryBean();

        simpleJobServiceFactoryBean.setDataSource(getDataSource());
        simpleJobServiceFactoryBean.setJobLauncher(getJobLauncher());
        simpleJobServiceFactoryBean.setJobLocator(getJobRegistry());
        simpleJobServiceFactoryBean.setJobRepository(getJobRepository());

        return simpleJobServiceFactoryBean;
    }
}
