package com.scor.rr.configuration.fileBasedImport;

import com.scor.rr.service.batch.*;
import com.scor.rr.service.fileBasedImport.batch.*;
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
public class FileBasedImportJob {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private LoadLossDataFileService loadLossDataFileService;

    @Autowired
    private ConvertToSCORFormatService convertToSCORFormatService;

    @Autowired
    private CalculateEPCEPSForSourcePLTService calculateEPCEPSForSourcePLTService;

    @Autowired
    private ConformPLTService conformPLTService;

    @Autowired
    private AdjustDefaultService adjustDefaultService;

    /**
     * Tasklet
     */

    // step 1
    @Bean
    public Tasklet loadLossDataFileTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            loadLossDataFileService.loadLossDataFile();
            return RepeatStatus.FINISHED;
        };
    }

    // step 2
    @Bean
    public Tasklet convertToSCORFormatTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            convertToSCORFormatService.convertToSCORFormat();
            return RepeatStatus.FINISHED;
        };
    }

    // step 3
    @Bean
    public Tasklet calculateEPCEPSForSourcePLTTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            calculateEPCEPSForSourcePLTService.calculateEPCEPSForSourcePLT();
            return RepeatStatus.FINISHED;
        };
    }

    // step 4
    @Bean
    public Tasklet conformPLTTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            conformPLTService.conformPLT();
            return RepeatStatus.FINISHED;
        };
    }

    // step 5
    @Bean
    public Tasklet adjustDefaultTasklet() {
        return (StepContribution contribution, ChunkContext chunkContext) -> {
            adjustDefaultService.adjustDefault();
            return RepeatStatus.FINISHED;
        };
    }

    // step
    @Bean
    public Step getLoadLossDataFileStep() {
        return stepBuilderFactory.get("loadLossDataFile").tasklet(loadLossDataFileTasklet()).build();
    }

    @Bean
    public Step getConvertToSCORFormatStep() {
        return stepBuilderFactory.get("convertToSCORFormat").tasklet(convertToSCORFormatTasklet()).build();
    }

    @Bean
    public Step getCalculateEPCEPSForSourcePLTStep() {
        return stepBuilderFactory.get("calculateEPCEPSForSourcePLT").tasklet(calculateEPCEPSForSourcePLTTasklet()).build();
    }

    @Bean
    public Step getConformPLTStep() {
        return stepBuilderFactory.get("conformPLT").tasklet(conformPLTTasklet()).build();
    }

    @Bean
    public Step getAdjustDefaultStep() {
        return stepBuilderFactory.get("adjustDefault").tasklet(adjustDefaultTasklet()).build();
    }

    /**
     * Job
     */

    @Bean(value = "fileBasedImport")
    public Job getFileBasedImport(@Qualifier(value = "jobBuilderFileBasedImport") SimpleJobBuilder simpleJobBuilder) {
        return simpleJobBuilder.build();
    }

    @Bean(value = "jobBuilderFileBasedImport")
    public SimpleJobBuilder getJobBuilder() {
        return jobBuilderFactory.get("fileBasedImport")
                .start(getLoadLossDataFileStep())
                .next(getConvertToSCORFormatStep())
                .next(getCalculateEPCEPSForSourcePLTStep())
                .next(getConformPLTStep())
                .next(getAdjustDefaultStep());
    }


//
//     <!--step 1-->
//        <batch:step id="loadLossDataFile" next="convertToSCORFormat">
//            <batch:tasklet ref="loadLossDataFileTasklet"/>
//        </batch:step>
//
//        <!--step 2-->
//        <batch:step id="convertToSCORFormat" next="calculateEPCEPSForSourcePLT">
//            <batch:tasklet ref="convertToScorFormatTasklet"/>
//        </batch:step>
//
//        <!--step 3-->
//        <batch:step id="calculateEPCEPSForSourcePLT" next="conformPLT">
//            <batch:tasklet ref="calculateEPCEPSForSourcePLTTasklet"/>
//        </batch:step>
//
//        <!--step 4-->
//        <batch:step id="conformPLT" next="adjustDefault">
//            <batch:tasklet ref="conformPLTTasklet"/>
//        </batch:step>
//
//        <!--&lt;!&ndash;step 5&ndash;&gt;-->
//        <!--<batch:step id="adjustDefault" next="createThreadAndPersistPLT">-->
//        <!--<batch:tasklet ref="adjustDefaultTasklet"/>-->
//        <!--<batch:end on="*" />-->
//        <!--<batch:fail on="FAILED" />-->
//        <!--</batch:step>-->
//
//        <!--step 5-->
//        <batch:step id="adjustDefault">
//            <batch:tasklet ref="adjustDefaultTasklet"/>
//            <batch:end on="*" />
//            <batch:fail on="FAILED" />
//        </batch:step>

}
