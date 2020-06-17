package com.scor.rr.service;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.JobDto;
import com.scor.rr.domain.dto.TaskDto;
import com.scor.rr.domain.enums.JobPriority;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.domain.model.RRJob;
import com.scor.rr.repository.*;
import com.scor.rr.service.batch.abstraction.JobManagerAbstraction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

@Service("jobManagerImpl")
@Slf4j
public class JobManagerImpl extends JobManagerAbstraction {

    @Autowired
    @Qualifier(value = "RRThreadPoolWithQueue")
    private ThreadPoolExecutor executor;

    @Autowired
    private JobOperator jobOperator;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobExecutionRepository jobExecutionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private JobEntityRepository jobRepository;

    @Autowired
    private StepEntityRepository stepRepository;

    @Autowired
    private UserRrRepository userRrRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private WorkspaceEntityRepository workspaceEntityRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void submitJob(Long jobId) {
    }

    @Override
    public void submitJob(Job importLossData, JobPriority priority, JobParameters params) {
        executor.execute(new RRJob(importLossData, priority, params, jobLauncher));
    }

    @Override
    public JobEntity cancelJob(Long jobId) {
        try {
            JobEntity job = jobRepository.findById(jobId).orElse(null);
            if (job != null) {
                for (TaskEntity task : job.getTasks()) {
                    this.cancelTask(task.getTaskId());
                }
                job.setStatus(JobStatus.CANCELLED.getCode());
                return jobRepository.saveAndFlush(job);
            } else return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JobEntity pauseJob(Long jobId) {
        try {
            JobEntity job = jobRepository.findById(jobId).orElse(null);
            if (job != null) {
                for (TaskEntity task : job.getTasks()) {
                    if (task.getStatus().equalsIgnoreCase(JobStatus.RUNNING.getCode()))
                        this.cancelTask(task.getTaskId());
                    else if (task.getStatus().equalsIgnoreCase(JobStatus.PENDING.getCode()))
                        this.pauseTask(task.getTaskId());
                }
                job.setStatus(JobStatus.PAUSED.getCode());
                return jobRepository.saveAndFlush(job);
            } else
                return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public JobEntity resumeJob(Long jobId) {
        try {
            JobEntity job = jobRepository.findById(jobId).orElse(null);
            if (job != null) {
                for (TaskEntity task : job.getTasks().stream().filter(t -> t.getStatus().equalsIgnoreCase(JobStatus.PAUSED.getCode())).collect(Collectors.toList())) {
                    task.setStatus(JobStatus.PENDING.getCode());
                    taskRepository.saveAndFlush(task);
                }
                job.setStatus(JobStatus.PENDING.getCode());
                return jobRepository.saveAndFlush(job);
            } else return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public void submitTask(Long taskId) {

    }

    @Override
    public void submitTask(Job importLossData, JobPriority priority, JobParameters params, TaskEntity taskEntity) {
        executor.execute(new RRJob(importLossData, priority, params, jobLauncher, taskEntity, taskRepository, jobRepository));
    }

    @Override
    public void cancelTask(Long taskId) {
        try {
            TaskEntity task = taskRepository.findById(taskId).orElse(null);
            // If the task was already running
            if (task != null && task.getJobExecutionId() != null) {
//                if(&& jobOperator.getRunningExecutions("importLossData").contains(task.getJobExecutionId()))
                try {
                    jobOperator.stop(task.getJobExecutionId());
                    cancelTaskSteps(task);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
//                // If the task is still in queue
            } else if (task != null && task.getJobExecutionId() == null) {
                RRJob runnable = (RRJob) executor.getQueue().stream().filter(e -> ((RRJob) e).getTask().getTaskId().equals(task.getTaskId())).findFirst().orElse(null);
                if (runnable != null)
                    executor.getQueue().remove(runnable);
                cancelTaskSteps(task);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void pauseTask(Long taskId) {
        try {
            TaskEntity task = taskRepository.findById(taskId).orElse(null);
            if (task != null && task.getJobExecutionId() == null) {
                RRJob runnable = (RRJob) executor.getQueue().stream().filter(e -> ((RRJob) e).getTask().getTaskId().equals(task.getTaskId())).findFirst().orElse(null);
                if (runnable != null)
                    executor.getQueue().remove(runnable);
                pauseTaskSteps(task);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cancelTaskSteps(TaskEntity task) {
        task.setStatus(JobStatus.CANCELLED.getCode());
        for (StepEntity step : task.getSteps().stream().filter(s -> s.getStatus().equalsIgnoreCase(StepStatus.RUNNING.getCode())).collect(Collectors.toList())) {
            this.logStep(step.getStepId(), StepStatus.CANCELLED);
        }
        taskRepository.saveAndFlush(task);
    }

    private void pauseTaskSteps(TaskEntity task) {
        task.setStatus(JobStatus.PAUSED.getCode());
        for (StepEntity step : task.getSteps().stream().filter(s -> s.getStatus().equalsIgnoreCase(StepStatus.RUNNING.getCode())).collect(Collectors.toList())) {
            this.logStep(step.getStepId(), StepStatus.PAUSED);
        }
        taskRepository.saveAndFlush(task);
    }

    @Override
    public boolean isJobRunning(Long jobId) {
        try {
            return jobOperator.getRunningExecutions("importLossData").contains(jobId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean isTaskRunning(Long taskId) {
        return false;
    }

    // Find Jobs from spring batch default schema
    @Override
    @Transactional(transactionManager = "theTransactionManager")
    public List<JobExecutionEntity> findRunningJobsForUser(String userId) {
        return jobExecutionRepository.findRunningJobsForUser(userId);
    }

    // Find Jobs from RR custom schema
    @Override
    public List<JobDto> findRunningJobsForUserRR(Long userId) {

        List<JobDto> jobDtoList = new ArrayList<>();
        List<Map<String, Object>> jobs = jobRepository.getJobRunningOrPendingForUser(userId);

        for (Map<String, Object> job : jobs) {
            JobDto jobDto = new JobDto();

            jobDto.setJobId(((BigInteger) job.get("JobId")).longValue());
            jobDto.setJobTypeCode("IMPORT");
            userRrRepository.findById(((BigInteger) job.get("UserId")).longValue())
                    .ifPresent(user -> jobDto.setSubmittedByUser(user.getFirstName() + " " + user.getLastName()));
            jobDto.setPriority(JobPriority.getStringValue((Integer) job.get("Priority")));
            jobDto.setStatus((String) job.get("Status"));
            jobDto.setClientName((String) job.get("clientName"));
            jobDto.setUwYear(Integer.valueOf((String) job.get("uwYear")));
            jobDto.setWorkspaceName((String) job.get("workspaceName"));
            jobDto.setContractCode((String) job.get("contractCode"));
            jobDto.setProjectId(Long.valueOf((String) job.get("projectId")));
            jobDto.setSubmittedDate((Date) job.get("submittedDate"));
            jobDto.setStartedDate((Date) job.get("startedDate"));
            jobDto.setFinishedDate((Date) job.get("finishedDate"));

            projectRepository.findById(jobDto.getProjectId()).ifPresent(p -> {
                WorkspaceEntity workspace = workspaceEntityRepository.findByWorkspaceId(p.getWorkspaceId());
                if (workspace != null)
                    jobDto.setWorkspaceContextCode(workspace.getWorkspaceContextCode());
                else
                    jobDto.setWorkspaceContextCode("NOT FOUND");
            });

            List<Map<String, Object>> tasks = taskRepository.getTasksByJobId(jobDto.getJobId());

            for (Map<String, Object> task : tasks) {
                TaskDto taskdto = new TaskDto();

                taskdto.setTaskId(((BigInteger) task.get("TaskId")).longValue());
                taskdto.setJobExecutionId(task.get("JobExecutionId") != null ? ((BigInteger) task.get("JobExecutionId")).longValue() : null);
                taskdto.setPercent((Integer) task.get("Percentage"));
                taskdto.setTaskType((String) task.get("TaskType"));
                taskdto.setStatus((String) task.get("Status"));
                taskdto.setSubmittedDate((Date) task.get("submittedDate"));
                taskdto.setStartedDate((Date) task.get("startedDate"));
                taskdto.setFinishedDate((Date) task.get("finishedDate"));

                if (taskdto.getTaskType().equalsIgnoreCase(TaskType.IMPORT_ANALYSIS.getCode())) {
                    if (task.get("AnalysisDivision") != null)
                        taskdto.setDivision((Integer) task.get("AnalysisDivision"));
                    if (task.get("AnalysisProject") != null)
                        taskdto.setProjectId(task.get("AnalysisProject") != null ? ((BigInteger) task.get("AnalysisProject")).longValue() : null);
                    taskdto.setName((String) task.get("AnalysisName"));
                    taskdto.setFinancialPerspective((String) task.get("AnalysisFinancialPerspective"));
                    taskdto.setTargetRapCode((String) task.get("TargetRapCode"));
                }

                if (taskdto.getTaskType().equalsIgnoreCase(TaskType.IMPORT_PORTFOLIO.getCode())) {
                    if (task.get("PortfolioDivision") != null)
                        taskdto.setDivision((Integer) task.get("PortfolioDivision"));
                    if (task.get("PortfolioProject") != null)
                        taskdto.setProjectId(task.get("PortfolioProject") != null ?
                                ((BigInteger) task.get("PortfolioProject")).longValue() : null);
                    taskdto.setName((String) task.get("PortfolioNumber"));
                }

                jobDto.addTask(taskdto);
            }

            jobDto.calculatePercentage();
            jobDtoList.add(jobDto);
        }

        return jobDtoList;
    }

    @Override
    public void onTaskError(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElse(null);

        if (task != null) {
            entityManager.refresh(task.getJob());
            if (!(task.getJob().getStatus().equalsIgnoreCase(JobStatus.PAUSED.getCode())
                    && task.getJob().getStatus().equalsIgnoreCase(JobStatus.CANCELLED.getCode())))
                task.getJob().setStatus(JobStatus.FAILED.getCode());
            task.setStatus(JobStatus.FAILED.getCode());
            jobRepository.saveAndFlush(task.getJob());
            taskRepository.saveAndFlush(task);
        }
    }

    @Override
    public List<StepEntity> getStepsForATask(Long taskId) {
        return stepRepository.findByTaskId(taskId);
    }
}
