package com.scor.rr.service;

import com.scor.rr.domain.JobEntity;
import com.scor.rr.domain.JobExecutionEntity;
import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.TaskEntity;
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

import java.util.ArrayList;
import java.util.List;
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
            if (task != null && task.getJobExecutionId() != null && jobOperator.getRunningExecutions("importLossData").contains(task.getJobExecutionId())) {
//                jobOperator.stop(task.getJobExecutionId());
//                cancelTaskSteps(task);
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

    private void cancelTaskSteps(TaskEntity task) {
        task.setStatus(JobStatus.CANCELLED.getCode());
        for (StepEntity step : task.getSteps().stream().filter(s -> s.getStatus().equalsIgnoreCase(StepStatus.RUNNING.getCode())).collect(Collectors.toList())) {
            this.logStep(step.getStepId(), StepStatus.CANCELLED);
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
        List<JobEntity> myJobs = jobRepository.findAllByUserIdAndSubmittedDate(userId);
        List<JobDto> jobs = new ArrayList<>();
//        List<JobDto> jobs = myJobs.stream().map(j -> modelMapper.map(j, JobDto.class)).collect(Collectors.toList());
        for (JobEntity j : myJobs) {
            List<TaskDto> tasks = new ArrayList<>();
            for (TaskEntity t : j.getTasks()) {
                TaskDto task = new TaskDto(t.getTaskId(), t.getJobExecutionId(), t.getStatus(), t.getPriority(),
                        t.getSubmittedDate(), t.getStartedDate(), t.getFinishedDate(), 0);
                tasks.add(task);
            }
            JobDto jobDto = new JobDto(j.getJobId(), j.getUserId(), j.getSubmittedDate(),
                    j.getPriority(), j.getStatus(), j.getStartedDate(), j.getFinishedDate(), j.getJobTypeCode(), tasks);
            jobs.add(jobDto);
        }
        return jobs;
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
