package com.scor.rr.service.abstraction;

import com.scor.rr.domain.JobEntity;
import com.scor.rr.domain.JobExecutionEntity;
import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.StepStatus;

import java.util.List;
import java.util.Map;

public interface JobManager {

    JobEntity createJob(Map<String, Object> params, Integer priority, Long userId);

    TaskEntity createTask(Map<String, Object> params, JobEntity job, Integer priority);

    StepEntity createStep(Long taskId, String stepCode, Integer stepOrder);

    void submitJob(Long jobId);

    void cancelJob(Long jobId);

    void submitTask(Long taskId);

    void cancelTask(Long taskId);

    boolean isJobRunning(Long jobId);

    List<JobExecutionEntity> findRunningJobsForUser(String userId);

    boolean isTaskRunning(Long taskId);

    JobStatus getJobStatus(Long jobId);

    JobStatus getTaskStatus(Long taskId);

    void logJob(Long jobId, JobStatus status);

    void logTask(Long taskId, JobStatus status);

    void logStep(Long stepId, StepStatus status);
}
