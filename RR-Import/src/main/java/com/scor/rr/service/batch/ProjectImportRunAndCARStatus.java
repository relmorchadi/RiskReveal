package com.scor.rr.service.batch;

import com.scor.rr.domain.ProjectConfigurationForeWriter;
import com.scor.rr.domain.ProjectImportRunEntity;
import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.enums.CARStatus;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.repository.JobEntityRepository;
import com.scor.rr.repository.ProjectConfigurationForeWriterRepository;
import com.scor.rr.repository.ProjectImportRunRepository;
import com.scor.rr.repository.TaskEntityRepository;
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

    @Autowired
    private TaskEntityRepository taskEntityRepository;

    @Autowired
    private JobEntityRepository jobEntityRepository;

    @Value("#{jobParameters['projectId']}")
    private String projectId;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{stepExecution.jobExecution.jobId}")
    private Long jobId;

    @Value("#{jobParameters['taskId']}")
    private String taskId;

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

        TaskEntity task = taskEntityRepository.findById(Long.valueOf(taskId)).orElse(null);
        if (task != null) {
            task.setStatus(JobStatus.SUCCEEDED.getCode());
            task.setFinishedDate(new Date());
            taskEntityRepository.save(task);
            if (task.getJob().getTasks().stream()
                    .noneMatch(t -> t.getStatus().equalsIgnoreCase(JobStatus.RUNNING.getCode()) && !t.getTaskId().equals(task.getTaskId()))) {
                task.getJob().setStatus(JobStatus.SUCCEEDED.getCode());
                task.getJob().setFinishedDate(new Date());
                jobEntityRepository.save(task.getJob());
            }
        }
        return RepeatStatus.FINISHED;
    }
}
