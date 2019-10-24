package com.scor.rr.configuration;

import com.scor.rr.service.batch.RegionPerilExtractor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class importLossDataJob {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    RegionPerilExtractor regionPerilExtractor;

    @Bean
    public Tasklet extractRegionPeril(){
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            regionPerilExtractor.loadRegionPeril();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step getExtractRegionPeril() {
        return stepBuilderFactory.get("extractRegionPeril").tasklet(extractRegionPeril()).build();
    }

    @Bean
    public Job getImportLossData() {
        return jobBuilderFactory.get("importLossData")
                .start(getExtractRegionPeril())
                .build();
    }

}
