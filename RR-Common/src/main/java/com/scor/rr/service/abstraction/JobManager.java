package com.scor.rr.service.abstraction;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.JobDto;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.StepStatus;

import java.util.List;
import java.util.Map;

public interface JobManager {

    JobEntity createJob(Map<String, String> params, Integer priority, Long userId);

    TaskEntity createTask(Map<String, String> params, JobEntity job, Integer priority, TaskType taskType);

    StepEntity createStep(Long taskId, String stepCode, Integer stepOrder);

    void submitJob(Long jobId);

    void cancelJob(Long jobId);

    void pauseJob(Long jobId);

    void resumeJob(Long jobId);

    void submitTask(Long taskId);

    void cancelTask(Long taskId);

    void pauseTask(Long taskId);

    boolean isJobRunning(Long jobId);

    List<JobExecutionEntity> findRunningJobsForUser(String userId);

    List<JobDto> findRunningJobsForUserRR(Long userId);

    void onTaskError(Long taskId);

    boolean isTaskRunning(Long taskId);

    String getJobStatus(Long jobId);

    String getTaskStatus(Long taskId);

    void logJob(Long jobId, JobStatus status);

    void logTask(Long taskId, JobStatus status);

    void logStep(Long stepId, StepStatus status);
}
