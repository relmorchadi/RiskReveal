package com.scor.rr.configuration;

import com.google.gson.Gson;
import com.scor.rr.domain.model.RRJob;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.MapJobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {


    @Autowired
    @Qualifier(value = "dbRms")
    private DataSource dataSource;

    @Autowired
    @Qualifier(value = "dataSource")
    private DataSource rrDataSource;

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
        factory.setDataSource(rrDataSource);
        return factory.getObject();
    }


    @Bean(name = "MyJobLauncher")
    public SimpleJobLauncher getJobLauncher() throws Exception {

        SimpleJobLauncher simpleJobLauncher = new SimpleJobLauncher();
        simpleJobLauncher.setJobRepository(getJobRepository());
        simpleJobLauncher.setTaskExecutor(threadPoolTaskExecutor());
        return simpleJobLauncher;
    }

    @Bean(name = "MyJobExplorer")
    public JobExplorer getJobExplorer() throws Exception {
        JobExplorerFactoryBean factoryBean = new JobExplorerFactoryBean();
        factoryBean.setDataSource(this.rrDataSource);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean(name = "MyJobRegistry")
    public JobRegistry jobRegistry() throws Exception {
        return new MapJobRegistry();
    }

    @Bean
    @ConditionalOnMissingBean(JobOperator.class)
    public SimpleJobOperator jobOperator(@Qualifier(value = "MyJobExplorer") JobExplorer jobExplorer,
                                         @Qualifier(value = "MyJobLauncher") JobLauncher jobLauncher,
                                         @Qualifier(value = "MyJobRegistry") ListableJobLocator jobRegistry,
                                         @Qualifier(value = "rrJobRepository") JobRepository jobRepository)
            throws Exception {

        SimpleJobOperator factory = new SimpleJobOperator();
        factory.setJobExplorer(jobExplorer);
        factory.setJobLauncher(jobLauncher);
        factory.setJobRegistry(jobRegistry);
        factory.setJobRepository(jobRepository);
        return factory;
    }

    @Bean
    public Gson getGson() {
        return new Gson();
    }

    @Bean
    public TaskExecutor threadPoolTaskExecutor() {

        SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor();
        executor.setConcurrencyLimit(1);
        executor.setThreadNamePrefix("jm_thread");
        return executor;
    }

    @Bean(name = "RRThreadPoolWithQueue")
    public ThreadPoolExecutor RRThreadPoolWithQueue() {
        return new ThreadPoolExecutor(1, 1, 1, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>(500, new Comparator<Runnable>() {
            @Override
            public int compare(Runnable o1, Runnable o2) {
                return ((RRJob) o1).compareTo((RRJob) o2);
            }
        }));
    }
}
