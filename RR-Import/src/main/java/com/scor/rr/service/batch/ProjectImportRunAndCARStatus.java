package com.scor.rr.service.batch;

import com.scor.rr.configuration.security.UserPrincipal;
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
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@StepScope
@Service
@Slf4j
public class ProjectImportRunAndCARStatus {


    RestTemplate restTemplate = new RestTemplate();
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

    @PersistenceContext
    private EntityManager entityManager;

    @Value("#{jobParameters['taskId']}")
    private String taskId;

    @Value("#{jobParameters['projectId']}")
    private String projectId;

    @Value("#{jobParameters['marketChannel']}")
    private String marketChannel;

    @Value("#{jobParameters['userId']}")
    private String userId;

    @Value("#{stepExecution.jobExecution.jobId}")
    private Long jobId;

    @Value("${endpoint.scope.completness.generation}")
    private String generateScopeAndCompletenessURL;

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

                    if (projectConfigurationForeWriter != null)
                        this.generateScopeAndCompletenessData(projectConfigurationForeWriter.getCaRequestId());

                }
            }

            TaskEntity task = taskEntityRepository.findById(Long.valueOf(taskId)).orElse(null);
            if (task != null) {
                if (!task.getStatus().equalsIgnoreCase(JobStatus.FAILED.getCode()))
                    task.setStatus(JobStatus.SUCCEEDED.getCode());
                task.setFinishedDate(new Date());
                taskEntityRepository.saveAndFlush(task);
                JobEntity job = jobEntityRepository.findById(task.getJob().getJobId()).orElse(null);
                if (job != null && job.getTasks().indexOf(task) == job.getTasks().size() - 1) {
                    if (job.getTasks().stream()
                            .anyMatch(t -> t.getStatus().equalsIgnoreCase(JobStatus.FAILED.getCode()))) {
                        entityManager.refresh(job);
                        if (!(job.getStatus().equalsIgnoreCase(JobStatus.PAUSED.getCode())
                                && job.getStatus().equalsIgnoreCase(JobStatus.CANCELLED.getCode())))
                            job.setStatus(JobStatus.FAILED.getCode());
                    } else
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

    private void generateScopeAndCompletenessData(String carId) {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        requestHeaders.add("Authorization", "Bearer ".concat(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getJwtToken()));

        HttpEntity<String> request = new HttpEntity<>(requestHeaders);

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(generateScopeAndCompletenessURL)
                .queryParam("fileName", carId);

        try {
            ResponseEntity<?> response = restTemplate
                    .exchange(uriBuilder.toUriString(), HttpMethod.POST, request, String.class);

            if (response.getStatusCode().equals(HttpStatus.OK))
                log.info("Scope and Completeness data generation has ended successfully for the CAR with Id {}", carId);
            else
                log.info("Scope and Completeness data generation has failed for the CAR with Id {} for reason : {}", carId, response.toString());
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }
}
