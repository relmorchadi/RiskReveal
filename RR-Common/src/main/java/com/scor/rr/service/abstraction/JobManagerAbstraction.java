package com.scor.rr.service.abstraction;

import com.google.gson.Gson;
import com.scor.rr.domain.JobEntity;
import com.scor.rr.domain.StepEntity;
import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.JobType;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.repository.JobEntityRepository;
import com.scor.rr.repository.StepEntityRepository;
import com.scor.rr.repository.TaskEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

@Service
public abstract class JobManagerAbstraction implements JobManager {


    @Autowired
    private JobEntityRepository jobEntityRepository;

    @Autowired
    private TaskEntityRepository taskEntityRepository;

    @Autowired
    private StepEntityRepository stepEntityRepository;

    @Override
    public JobEntity createJob(Map<String, Object> params, Integer priority, Long userId) {

        Gson gson = new Gson();
        String parameters = gson.toJson(params);

        JobEntity job = new JobEntity();
        job.setJobParams(parameters);
        job.setJobTypeCode(JobType.IMPORT);
        job.setJobTypeDesc("Batch Import Job");
        job.setSubmittedDate(new Timestamp((new Date()).getTime()));
        job.setStatus(JobStatus.PENDING);
        job.setPriority(priority);
        job.setSubmittedByUser(userId);

        return jobEntityRepository.save(job);
    }

    @Override
    public TaskEntity createTask(Map<String, Object> params, JobEntity job, Integer priority) {

        Gson gson = new Gson();
        String parameters = gson.toJson(params);

        TaskEntity task = new TaskEntity();
        task.setJobId(job);
        task.setTaskParams(parameters);
        task.setPriority(priority);
        task.setStatus(JobStatus.PENDING);
        task.setSubmittedDate(new Timestamp((new Date()).getTime()));
        task.setTaskCode(JobType.IMPORT);

        return taskEntityRepository.save(task);
    }

    @Override
    public StepEntity createStep(Long taskId, String stepCode, Integer stepOrder) {

        StepEntity step = new StepEntity();
        step.setSubmittedDate(new Timestamp((new Date()).getTime()));
        step.setStartedDate(new Timestamp((new Date()).getTime()));
        step.setStepName(stepCode);
        step.setStepOrder(stepOrder);
        step.setTaskId(taskId);
        step.setStatus(StepStatus.RUNNING);

        return stepEntityRepository.save(step);
    }

    @Override
    public abstract void submitJob(Long jobId);

    @Override
    public abstract void cancelJob(Long jobId);

    @Override
    public abstract void submitTask(Long taskId);

    @Override
    public abstract void cancelTask(Long taskId);

    @Override
    public abstract boolean isJobRunning(Long jobId);

    @Override
    public abstract boolean isTaskRunning(Long taskId);

    @Override
    public JobStatus getJobStatus(Long jobId) {
        return jobEntityRepository.findById(jobId).map(JobEntity::getStatus).orElse(null);
    }

    @Override
    public JobStatus getTaskStatus(Long taskId) {
        return taskEntityRepository.findById(taskId).map(TaskEntity::getStatus).orElse(null);
    }

    @Override
    public void logJob(Long jobId, JobStatus status) {
        jobEntityRepository.updateStatus(jobId, status);
    }

    @Override
    public void logTask(Long taskId, JobStatus status) {
        taskEntityRepository.updateStatus(taskId, status);
    }

    @Override
    public void logStep(Long stepId, StepStatus status) {
        stepEntityRepository.updateStatus(stepId, status);
    }
}
