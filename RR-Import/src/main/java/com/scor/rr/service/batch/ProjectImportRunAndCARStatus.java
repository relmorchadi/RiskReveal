package com.scor.rr.service.batch;

import com.scor.rr.domain.*;
import com.scor.rr.domain.enums.CARStatus;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.domain.enums.TrackingStatus;
import com.scor.rr.repository.JobEntityRepository;
import com.scor.rr.repository.ProjectConfigurationForeWriterRepository;
import com.scor.rr.repository.ProjectImportRunRepository;
import com.scor.rr.repository.TaskEntityRepository;
import com.scor.rr.service.abstraction.JobManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Autowired
    @Qualifier("jobManagerImpl")
    private JobManager jobManager;

    @Value("#{jobParameters['taskId']}")
    private String taskId;

    @Value("#{jobParameters['projectId']}")
    private String projectId;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{stepExecution.jobExecution.jobId}")
    private Long jobId;

    public RepeatStatus changeProjectImportRunStatus() {

        StepEntity step = jobManager.createStep(Long.valueOf(taskId), "ChangeProjectImportRunStatus", 18);
        try {
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
                taskEntityRepository.saveAndFlush(task);
                JobEntity job = jobEntityRepository.findById(task.getJob().getJobId()).orElse(null);
                if (job != null && job.getTasks().indexOf(task) == job.getTasks().size() - 1) {
                    if (job.getTasks().stream()
                            .noneMatch(t -> t.getStatus().equalsIgnoreCase(JobStatus.FAILED.getCode())))
                        job.setStatus(JobStatus.FAILED.getCode());
                    else
                        job.setStatus(JobStatus.SUCCEEDED.getCode());
                    job.setFinishedDate(new Date());
                    jobEntityRepository.saveAndFlush(job);
                }
            }
            jobManager.logStep(step.getStepId(), StepStatus.SUCCEEDED);
            return RepeatStatus.FINISHED;
        } catch (Exception ex) {
            jobManager.onTaskError(Long.valueOf(taskId));
            jobManager.logStep(step.getStepId(), StepStatus.FAILED);
            ex.printStackTrace();
            return RepeatStatus.FINISHED;
        }
    }
}
