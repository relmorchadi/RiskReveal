package com.scor.rr.configuration;

import com.scor.rr.service.batch.ExchangeRateExtractor;
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
public class ImportLossDataJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RegionPerilExtractor regionPerilExtractor;

    @Autowired
    private ExchangeRateExtractor exchangeRateExtractor;

    @Bean
    public Tasklet extractRegionPerilTasklet(){
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            regionPerilExtractor.loadRegionPerilAndCreateRRAnalysisAndRRLossTableHeader();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet extractExchangeRatesTasklet(){
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            exchangeRateExtractor.runExchangeRateExtraction();
            return RepeatStatus.FINISHED;
        };
    }

    /**
     * @implNote Step 1 of the main job : used to extract region perils
     * @return
     */
    @Bean
    public Step getExtractRegionPeril() {
        return stepBuilderFactory.get("extractRegionPeril").tasklet(extractRegionPerilTasklet()).build();
    }

    /**
     * @implNote Step 2 of the main job : used to extract exchange rates for the currencies used
     * @return
     */
    @Bean
    public Step getExtractExchangeRates() {
        return stepBuilderFactory.get("exchangeRatesExtraction").tasklet(extractExchangeRatesTasklet()).build();
    }

    @Bean(value = "importLossData")
    public Job getImportLossData() {
        return jobBuilderFactory.get("importLossData")
                .start(getExtractRegionPeril())
                .on("COMPLETE").to(getExtractExchangeRates())
                .end()
                .build();
    }

}
