package com.scor.rr;

import com.scor.rr.service.batch.BatchExecution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("com.scor.rr.repository")
@EnableBatchProcessing
@EnableTransactionManagement
public class ImportBackend extends SpringBootServletInitializer implements CommandLineRunner {


    @Autowired
    private BatchExecution batchExecution;

    public static void main(String[] args) {
        SpringApplication.run(ImportBackend.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        batchExecution.submitPendingAndRunningTasksToTheQueueAtStartUp();
    }
}
