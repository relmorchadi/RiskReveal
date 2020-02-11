package com.scor.rr.service.batch;

import com.scor.rr.domain.ProjectConfigurationForeWriter;
import com.scor.rr.domain.ProjectImportRunEntity;
import com.scor.rr.domain.enums.CARStatus;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.repository.ProjectConfigurationForeWriterRepository;
import com.scor.rr.repository.ProjectImportRunRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@StepScope
@Service
@Slf4j
public class ProjectImportRunAndCARStatus {


    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Autowired
    private ProjectConfigurationForeWriterRepository projectConfigurationForeWriterRepository;

    @Value("#{jobParameters['projectId']}")
    private String projectId;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    public RepeatStatus changeProjectImportRunStatus() {

        log.info("Changing CAR and Project import run status");
        if (projectId != null) {
            ProjectImportRunEntity projectImportRunEntity = projectImportRunRepository.findFirstByProjectIdOrderByRunId(Long.valueOf(projectId));

            if (projectImportRunEntity != null) {
                log.info("Changing import run status to COMPLETED");
                projectImportRunEntity.setEndDate(new Date());
                projectImportRunEntity.setStatus(TrackingStatus.DONE.toString());
                projectImportRunEntity.setEntity(1);
                projectImportRunEntity.setLossImportEndDate(new Date());
                projectImportRunRepository.save(projectImportRunEntity);
            } else {
                log.info("No import run was found");
            }

            if (marketChannel.equalsIgnoreCase("FAC")) {
                ProjectConfigurationForeWriter projectConfigurationForeWriter = projectConfigurationForeWriterRepository.findByProjectId(Long.valueOf(projectId));

                if (projectConfigurationForeWriter != null) {
                    log.info("Changing CAR status to IN PROGRESS");
                    projectConfigurationForeWriter.setCarStatus(CARStatus.ASGN);
                    projectConfigurationForeWriterRepository.save(projectConfigurationForeWriter);
                } else {
                    log.info("No car configuration was found");
                }
            }
        }
        return RepeatStatus.FINISHED;
    }
}
