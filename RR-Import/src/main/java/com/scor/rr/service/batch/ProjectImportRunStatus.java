package com.scor.rr.service.batch;

import com.scor.rr.domain.ProjectImportRunEntity;
import com.scor.rr.domain.enums.TrackingStatus;
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
public class ProjectImportRunStatus {


    @Autowired
    private ProjectImportRunRepository projectImportRunRepository;

    @Value("#{jobParameters['projectId']}")
    private String projectId;

    public RepeatStatus changeProjectImportRunStatus() {

        log.info("Changing CAR status");
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

        }
        return RepeatStatus.FINISHED;
    }
}
