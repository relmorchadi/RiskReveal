package com.scor.rr.configuration;

import com.scor.rr.service.batch.*;
import com.scor.rr.service.batch.processor.AccItemProcessor;
import com.scor.rr.service.batch.processor.LocItemProcessor;
import com.scor.rr.service.batch.processor.rows.RLAccRow;
import com.scor.rr.service.batch.processor.rows.RLLocRow;
import com.scor.rr.service.batch.reader.RLAccCursorItemReader;
import com.scor.rr.service.batch.reader.RLLocCursorItemReader;
import com.scor.rr.service.batch.writer.AccLocFilesHandler;
import com.scor.rr.service.batch.writer.ELTWriter;
import com.scor.rr.service.batch.writer.PLTWriter;
import org.beanio.spring.BeanIOFlatFileItemWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.SimpleJobBuilder;
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
    ELTConformer eltConformer;

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private RegionPerilExtractor regionPerilExtractor;

    @Autowired
    private ExchangeRateExtractor exchangeRateExtractor;

    @Autowired
    private EpCurveExtractor epCurveExtractor;

    @Autowired
    private ELTExtractor eltExtractor;

    @Autowired
    private ELTTruncator eltTruncator;

    @Autowired
    private ELTToPLTConverter eltToPLTConverter;

    @Autowired
    @Qualifier(value = "eltWriter")
    private ELTWriter eltWriter;

    @Autowired
    private PLTWriter pltWriter;

    @Autowired
    private DefaultAdjustment defaultAdjustment;

    @Autowired
    private ModellingOptionsExtractor modellingOptionsExtractor;

    @Autowired
    private ExposureSummaryExtractor exposureSummaryExtractor;

    @Autowired
    private TivExtractor tivExtractor;

    @Autowired
    private AccLocFilesHandler accLocFilesHandler;

    @Autowired
    private ProjectImportRunAndCARStatus carStatus;

    @Autowired
    @Qualifier(value = "AccReader")
    private RLAccCursorItemReader accReader;

    @Autowired
    @Qualifier(value = "LocReader")
    private RLLocCursorItemReader locReader;

    @Autowired
    @Qualifier(value = "AccProcessor")
    private AccItemProcessor accProcessor;

    @Autowired
    @Qualifier(value = "LocProcessor")
    private LocItemProcessor locProcessor;

    @Autowired
    @Qualifier(value = "AccWriter")
    private BeanIOFlatFileItemWriter accWriter;

    @Autowired
    @Qualifier(value = "LocWriter")
    private BeanIOFlatFileItemWriter locWriter;

    @Autowired
    @Qualifier(value = "LocWriterFW")
    private BeanIOFlatFileItemWriter locWriterFW;

    /**
     * Tasklet
     */
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
    public Tasklet conformeEltTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltConformer.conformeELT();
    }

    @Bean
    public Tasklet truncateELTTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltTruncator.truncateELTs();
    }

    @Bean
    public Tasklet extractModellingOptionsTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> modellingOptionsExtractor.extractModellingOptions();
    }

    @Bean
    public Tasklet eltBinaryWritingTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltWriter.writeBinary();
    }

    @Bean
    public Tasklet eltHeaderWritingTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltWriter.writeHeader();
    }

    @Bean
    public Tasklet EltToPLTTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> eltToPLTConverter.convertEltToPLT();
    }

    @Bean
    public Tasklet pltWriterTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> pltWriter.writeHeader();
    }

    @Bean
    public Tasklet conformEPCurvesTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> epCurveExtractor.extractConformedEpCurves();
    }

    @Bean
    public Tasklet defaultAdjustmentTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> defaultAdjustment.defaultAdjustment();
    }

    @Bean
    public Tasklet extractExposureSummaryTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> exposureSummaryExtractor.extract();
    }

    @Bean
    public Tasklet extractTIVTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> tivExtractor.tivExtraction();
    }

    @Bean
    public Tasklet copyAccAndLocToIHubTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> accLocFilesHandler.copyFilesToIHub();
    }

    @Bean
    public Tasklet projectImportRunStatusTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> carStatus.changeProjectImportRunStatus();
    }

    /** Steps */

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
    public Step conformeEltStep() {
        return stepBuilderFactory.get("conformeELT").tasklet(conformeEltTasklet()).build();
    }

    @Bean
    public Step getELTTruncateELTStep() {
        return stepBuilderFactory.get("truncateELT").tasklet(truncateELTTasklet()).build();
    }

    @Bean
    public Step getExtractModellingOptionsStep() {
        return stepBuilderFactory.get("extractModellingOptions").tasklet(extractModellingOptionsTasklet()).build();
    }

    @Bean
    public Step getEltBinaryWritingStep() {
        return stepBuilderFactory.get("EltBinaryWriting").tasklet(eltBinaryWritingTasklet()).build();
    }

    @Bean
    public Step getEltHeaderWritingStep() {
        return stepBuilderFactory.get("EltHeaderWriting").tasklet(eltHeaderWritingTasklet()).build();
    }

    @Bean
    public Step getEltToPLTStep() {
        return stepBuilderFactory.get("EltToPLT").tasklet(EltToPLTTasklet()).build();
    }

    @Bean
    public Step getPltWriterStep() {
        return stepBuilderFactory.get("pltWriter").tasklet(pltWriterTasklet()).build();
    }

    @Bean
    public Step conformEPCurvesStep() {
        return stepBuilderFactory.get("conformEpCurves").tasklet(conformEPCurvesTasklet()).build();
    }

    @Bean
    public Step defaultAdjustmentStep() {
        return stepBuilderFactory.get("defaultAdjustment").tasklet(defaultAdjustmentTasklet()).build();
    }

    @Bean
    public Step extractExposureSummaryStep() {
        return stepBuilderFactory.get("extractExposureSummary").tasklet(extractExposureSummaryTasklet()).build();
    }

    @Bean
    public Step extractTIVStep() {
        return stepBuilderFactory.get("extractTiv").tasklet(extractTIVTasklet()).build();
    }

    @Bean
    public Step extractAccStep() {
        return this.stepBuilderFactory.get("extractAcc")
                .<RLAccRow, RLAccRow>chunk(1000)
                .reader(accReader)
                .processor(accProcessor)
                .writer(accWriter)
                .build();
    }

    @Bean
    public Step extractLocStep() {
        return this.stepBuilderFactory.get("extractLoc")
                .<RLLocRow, RLLocRow>chunk(1000)
                .reader(locReader)
                .processor(locProcessor)
                .writer(locWriter)
                .build();
    }

    @Bean
    public Step extractLocFWStep() {
        return this.stepBuilderFactory.get("extractLocFW")
                .<RLLocRow, RLLocRow>chunk(50000)
                .reader(locReader)
                .processor(locProcessor)
                .writer(locWriterFW)
                .build();
    }

    @Bean
    public Step copyAccAndLocFilesStep() {
        return stepBuilderFactory.get("copyAccAndLocFiles").tasklet(copyAccAndLocToIHubTasklet()).build();
    }

    @Bean
    public Step projectImportRunStatusChangeStep() {
        return stepBuilderFactory.get("projectImportRunStatus").tasklet(projectImportRunStatusTasklet()).build();
    }

    /**
     * Job
     */

    @Bean(value = "importLossDataFac")
    public Job getImportLossDataFac(@Qualifier(value = "jobBuilder") SimpleJobBuilder simpleJobBuilder) {
        return simpleJobBuilder
                .next(extractAccStep())
                .next(extractLocStep())
                .next(extractLocFWStep())
                .next(copyAccAndLocFilesStep())
                .next(projectImportRunStatusChangeStep())
                .build();
    }

    @Bean(value = "importLossData")
    public Job getImportLossData(@Qualifier(value = "jobBuilder") SimpleJobBuilder simpleJobBuilder) {
        return simpleJobBuilder
                .next(projectImportRunStatusChangeStep())
                .build();
    }

    @Bean(value = "jobBuilder")
    public SimpleJobBuilder getJobBuilder() {
        return jobBuilderFactory.get("importLossData")
                .start(getExtractRegionPerilStep())
                .next(getExtractEpCurveStatsStep())
                .next(getExtractExchangeRatesStep())
                .next(geExtractELTStep())
                .next(getELTTruncateELTStep())
                .next(conformeEltStep())
                .next(conformEPCurvesStep())
                .next(getEltBinaryWritingStep())
                .next(getEltHeaderWritingStep())
                .next(getExtractModellingOptionsStep())
                .next(getEltToPLTStep())
                .next(getPltWriterStep())
                .next(defaultAdjustmentStep())
                .next(extractExposureSummaryStep());
    }
}
