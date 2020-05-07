package com.scor.rr.service;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.JobDto;
import com.scor.rr.domain.dto.TaskDto;
import com.scor.rr.domain.enums.JobPriority;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.domain.model.RRJob;
import com.scor.rr.repository.JobEntityRepository;
import com.scor.rr.repository.JobExecutionRepository;
import com.scor.rr.repository.TaskRepository;
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

import java.math.BigInteger;
import java.util.ArrayList;
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


    @Override
    public void submitJob(Long jobId) {
    }

    @Override
    public void submitJob(Job importLossData, JobPriority priority, JobParameters params) {
        executor.execute(new RRJob(importLossData, priority, params, jobLauncher));
    }

    @Override
    public void cancelJob(Long jobId) {
        try {
            JobEntity job = jobRepository.findById(jobId).orElse(null);
            if (job != null) {
                for (TaskEntity task : job.getTasks()) {
                    this.cancelTask(task.getTaskId());
                }
                job.setStatus(JobStatus.CANCELLED.getCode());
                jobRepository.saveAndFlush(job);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void pauseJob(Long jobId) {
        try {
            JobEntity job = jobRepository.findById(jobId).orElse(null);
            if (job != null) {
                for (TaskEntity task : job.getTasks()) {
                    this.pauseTask(task.getTaskId());
                }
                job.setStatus(JobStatus.PAUSED.getCode());
                jobRepository.saveAndFlush(job);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void resumeJob(Long jobId) {
        try {
            JobEntity job = jobRepository.findById(jobId).orElse(null);
            if (job != null) {
                for (TaskEntity task : job.getTasks().stream().filter(t -> t.getStatus().equalsIgnoreCase(JobStatus.PAUSED.getCode())).collect(Collectors.toList())) {
                    task.setStatus(JobStatus.PENDING.getCode());
                    taskRepository.saveAndFlush(task);
                }
                job.setStatus(JobStatus.PENDING.getCode());
                jobRepository.saveAndFlush(job);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
//            if (task != null && task.getJobExecutionId() != null && jobOperator.getRunningExecutions("importLossData").contains(task.getJobExecutionId())) {
//                jobOperator.stop(task.getJobExecutionId());
//                cancelTaskSteps(task);
//                // If the task is still in queue
//            } else
            if (task != null && task.getJobExecutionId() == null) {
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
            jobDto.setPriority(JobPriority.getStringValue((Integer) job.get("Priority")));
            jobDto.setStatus((String) job.get("Status"));
            jobDto.setClientName((String) job.get("clientName"));
            jobDto.setUwYear(Integer.valueOf((String) job.get("uwYear")));
            jobDto.setWorkspaceName((String) job.get("workspaceName"));
            jobDto.setContractCode((String) job.get("contractCode"));
            jobDto.setProjectId(Long.valueOf((String) job.get("projectId")));

            List<Map<String, Object>> tasks = taskRepository.getTasksByJobId(jobDto.getJobId());

            for (Map<String, Object> task : tasks) {
                TaskDto taskdto = new TaskDto();

                taskdto.setTaskId(((BigInteger) task.get("TaskId")).longValue());
                taskdto.setJobExecutionId(((BigInteger) task.get("JobExecutionId")).longValue());
                taskdto.setPercent((Integer) task.get("Percentage"));
                taskdto.setTaskType((String) task.get("TaskType"));
                taskdto.setStatus((String) task.get("Status"));

                if (taskdto.getTaskType().equalsIgnoreCase(TaskType.IMPORT_ANALYSIS.getCode())) {
                    if (task.get("AnalysisDivision") != null)
                        taskdto.setDivision(Integer.valueOf((String) task.get("AnalysisDivision")));
                    if (task.get("AnalysisProject") != null)
                        taskdto.setProjectId(Long.valueOf((String) task.get("AnalysisProject")));
                    taskdto.setName((String) task.get("AnalysisName"));
                    taskdto.setFinancialPerspective((String) task.get("AnalysisFinancialPerspective"));
                    taskdto.setTargetRapCode((String) task.get("TargetRapCode"));
                }

                if (taskdto.getTaskType().equalsIgnoreCase(TaskType.IMPORT_PORTFOLIO.getCode())) {
                    if (task.get("PortfolioDivision") != null)
                        taskdto.setDivision(Integer.valueOf((String) task.get("PortfolioDivision")));
                    if (task.get("PortfolioProject") != null)
                        taskdto.setProjectId(Long.valueOf((String) task.get("PortfolioProject")));
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
    @Transactional(transactionManager = "theTransactionManager")
    public void onTaskError(Long taskId) {
        TaskEntity task = taskRepository.findById(taskId).orElse(null);

        if (task != null) {
            task.getJob().setStatus(JobStatus.FAILED.getCode());
            task.setStatus(JobStatus.FAILED.getCode());
            jobRepository.saveAndFlush(task.getJob());
            taskRepository.saveAndFlush(task);
        }
    }
}
