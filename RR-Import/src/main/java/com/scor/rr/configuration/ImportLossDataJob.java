package com.scor.rr.configuration;

import com.scor.rr.service.batch.*;
import com.scor.rr.service.batch.writer.AbstractWriter;
import com.scor.rr.service.batch.writer.ELTWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    EpCurveExtractor epCurveExtractor;

    @Autowired
    ELTExtractor eltExtractor;

    @Autowired
    ELTTruncator eltTruncator;

    @Autowired
    @Qualifier(value = "eltWriter")
    private ELTWriter eltWriter;

    @Bean
    public Tasklet extractRegionPerilTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            regionPerilExtractor.loadRegionPerilAndCreateRRAnalysisAndRRLossTableHeader();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet extractEpCurveStatsTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> epCurveExtractor.extractEpCurve();
    }

    @Bean
    public Tasklet extractExchangeRatesTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            exchangeRateExtractor.runExchangeRateExtraction();
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Tasklet extractELTTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltExtractor.extractElts();
    }

    @Bean
    public Tasklet truncateELTTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltTruncator.truncateELTs();
    }

    @Bean
    public Tasklet eltBinaryWritingTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltWriter.writeBinary();
    }

    @Bean
    public Tasklet eltHeaderWritingTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltWriter.writeHeader();
    }

    /**
     * @return
     * @implNote Step 1 of the main job : used to extract region perils
     */
    @Bean
    public Step getExtractRegionPerilStep() {
        return stepBuilderFactory.get("extractRegionPeril").tasklet(extractRegionPerilTasklet()).build();
    }

    @Bean
    public Step getExtractEpCurveStatsStep() {
        return stepBuilderFactory.get("extractEpCurve").tasklet(extractEpCurveStatsTasklet()).build();
    }

    /**
     * @return
     * @implNote Step 3 of the main job : used to extract exchange rates for the currencies used
     */
    @Bean
    public Step getExtractExchangeRatesStep() {
        return stepBuilderFactory.get("exchangeRatesExtraction").tasklet(extractExchangeRatesTasklet()).build();
    }

    @Bean
    public Step geExtractELTStep() {
        return stepBuilderFactory.get("extractELT").tasklet(extractELTTasklet()).build();
    }

    @Bean
    public Step getELTTruncateELTStep() {
        return stepBuilderFactory.get("truncateELT").tasklet(truncateELTTasklet()).build();
    }

    @Bean
    public Step getEltBinaryWritingStep(){
        return stepBuilderFactory.get("EltBinaryWriting").tasklet(eltBinaryWritingTasklet()).build();
    }

    @Bean
    public Step getEltHeaderWritingStep(){
        return stepBuilderFactory.get("EltHeaderWriting").tasklet(eltHeaderWritingTasklet()).build();
    }

    @Bean(value = "importLossData")
    public Job getImportLossData() {
        return jobBuilderFactory.get("importLossData")
                .start(getExtractRegionPerilStep())
                .on("COMPLETE").to(getExtractExchangeRatesStep())
                .next(getExtractEpCurveStatsStep())
                .next(geExtractELTStep())
                .next(getELTTruncateELTStep())
                //.next("conformer")
                //.next("conformEpCurves")
                //.next("extractModelingOptions")
                .next(getEltBinaryWritingStep())
                .next(getEltHeaderWritingStep())
                .end()
                .build();
    }


}
