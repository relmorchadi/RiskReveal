package com.scor.rr.configuration;

import com.scor.rr.service.batch.*;
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
public class ImportLossDataJob {

    @Autowired
    JobBuilderFactory jobBuilderFactory;

    @Autowired
    StepBuilderFactory stepBuilderFactory;

    @Autowired
    RegionPerilExtractor regionPerilExtractor;

    @Autowired
    EpCurveExtractor epCurveExtractor;

    @Autowired
    ELTExtractor eltExtractor;

    @Autowired
    ELTTruncator eltTruncator;

    @Autowired
    ELTToPLTConverter eltToPLTConverter;

    @Bean
    public Tasklet extractRegionPerilTasklet(){
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            regionPerilExtractor.loadRegionPerilAndCreateRRAnalysisAndRRLossTableHeader();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet extractEpCurveStatsTasklet(){
        return (StepContribution contribution, ChunkContext chunkContext) -> epCurveExtractor.extractEpCurve();
    }


    @Bean
    public Tasklet extractELTTasklet(){
        return (StepContribution contribution, ChunkContext chunkContext) -> eltExtractor.extractElts();
    }

    @Bean
    public Tasklet truncateELTTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltTruncator.truncateELTs();
    }

    @Bean
    public Tasklet EltToPLTTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltToPLTConverter.convertEltToPLT();
    }

    @Bean
    public Step getExtractRegionPerilStep() {
        return stepBuilderFactory.get("extractRegionPeril").tasklet(extractRegionPerilTasklet()).build();
    }

    @Bean
    public Step getExtractEpCurveStatsStep() {
        return stepBuilderFactory.get("extractEpCurve").tasklet(extractEpCurveStatsTasklet()).build();
    }

    @Bean
    public Step geExtractELTStep() {
        return stepBuilderFactory.get("extractELT").tasklet(extractELTTasklet()).build();
    }

    @Bean
    public Step getELTTruncatorStep() {
        return stepBuilderFactory.get("truncateELT").tasklet(truncateELTTasklet()).build();
    }

    @Bean
    public Step getEltToPLTStep() {
        return stepBuilderFactory.get("EltToPLT").tasklet(EltToPLTTasklet()).build();
    }

    @Bean
    public Job getImportLossData() {
        return jobBuilderFactory.get("importLossData")
                .start(getExtractRegionPerilStep())
                .next(getExtractEpCurveStatsStep())
                // @SkippedStep (ExtractRmsExchangeRates)
                .next(geExtractELTStep())
                .next(getELTTruncatorStep())
                .next(getEltToPLTStep())
                .build();
    }

}
