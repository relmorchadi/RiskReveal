package com.scor.rr.configuration;

import org.springframework.batch.core.scope.JobScope;
import org.springframework.batch.core.scope.StepScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ScopeConfiguration {

//    @Bean
//    public StepScope stepScope() {
//        final StepScope stepScope = new StepScope();
//        stepScope.setAutoProxy(true);
//        return stepScope;
//    }
//
//    @Bean
//    public JobScope jobScope() {
//        final JobScope jobScope = new JobScope();
//        jobScope.setAutoProxy(true);
//        return jobScope;
//    }
}
