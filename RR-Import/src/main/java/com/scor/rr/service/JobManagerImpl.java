package com.scor.rr.service;

import com.scor.rr.domain.JobExecutionEntity;
import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.enums.JobPriority;
import com.scor.rr.domain.model.RRJob;
import com.scor.rr.repository.JobEntityRepository;
import com.scor.rr.repository.JobExecutionRepository;
import com.scor.rr.repository.TaskRepository;
import com.scor.rr.service.abstraction.JobManagerAbstraction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

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

    public void submitJob(Job importLossData, JobPriority priority, JobParameters params) {
        executor.execute(new RRJob(importLossData, priority, params, jobLauncher));
    }

    @Override
    public void cancelJob(Long jobId) {
        try {
            if (jobOperator.getRunningExecutions("importLossData").contains(jobId))
                jobOperator.stop(jobId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void submitTask(Long taskId) {

    }

    public void submitTask(Job importLossData, JobPriority priority, JobParameters params, TaskEntity taskEntity) {
        executor.execute(new RRJob(importLossData, priority, params, jobLauncher, taskEntity, taskRepository, jobRepository));
    }

    @Override
    public void cancelTask(Long taskId) {

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

    @Override
    public List<JobExecutionEntity> findRunningJobsForUser(String userId) {
        return jobExecutionRepository.findRunningJobsForUser(userId);
    }
}
