package com.scor.rr.service.abstraction;

import com.google.gson.Gson;
import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.JobDto;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.JobType;
import com.scor.rr.domain.enums.StepStatus;
import com.scor.rr.repository.JobEntityRepository;
import com.scor.rr.repository.StepEntityRepository;
import com.scor.rr.repository.TaskEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public abstract class JobManagerAbstraction implements JobManager {


    @Autowired
    private JobEntityRepository jobEntityRepository;

    @Autowired
    private TaskEntityRepository taskEntityRepository;

    @Autowired
    private StepEntityRepository stepEntityRepository;

    @Override
    public JobEntity createJob(Map<String, String> params, Integer priority, Long userId) {

        Gson gson = new Gson();
        String parameters = gson.toJson(params);

        JobEntity job = new JobEntity();
        job.setJobParams(parameters);
        job.setJobTypeCode(JobType.IMPORT);
        job.setJobTypeDesc("Batch Import Job");
        job.setSubmittedDate(new Timestamp((new Date()).getTime()));
        job.setStatus(JobStatus.PENDING.getCode());
        job.setPriority(priority);
        job.setSubmittedByUser(userId);
        if (SecurityContextHolder.getContext().getAuthentication() != null)
            job.setUserId(((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId());

        return jobEntityRepository.saveAndFlush(job);
    }

    @Override
    public TaskEntity createTask(Map<String, String> params, JobEntity job, Integer priority, TaskType taskType) {

        Gson gson = new Gson();
        String parameters = gson.toJson(params);

        TaskEntity task = new TaskEntity();
        task.setJob(job);
        task.setTaskParams(parameters);
        task.setPriority(priority);
        task.setStatus(JobStatus.PENDING.getCode());
        task.setSubmittedDate(new Timestamp((new Date()).getTime()));
        task.setTaskType(taskType.getCode());

        return taskEntityRepository.saveAndFlush(task);
    }

    @Override
    public StepEntity createStep(Long taskId, String stepCode, Integer stepOrder) {

        Optional<TaskEntity> taskEntityOptional = taskEntityRepository.findById(taskId);
        if (!taskEntityOptional.isPresent()) {
            Optional<StepEntity> stepOp = taskEntityOptional.get().getSteps().stream().filter(s -> s.getStepName().equalsIgnoreCase(stepCode)).findFirst();
            if (!stepOp.isPresent()) {
                StepEntity step = new StepEntity();
                step.setSubmittedDate(new Timestamp((new Date()).getTime()));
                step.setStartedDate(new Timestamp((new Date()).getTime()));
                step.setStepName(stepCode);
                step.setStepOrder(stepOrder);
                TaskEntity task = taskEntityRepository.findById(taskId).orElse(null);
                step.setTask(task);
                step.setStatus(StepStatus.RUNNING.getCode());
                return stepEntityRepository.saveAndFlush(step);
            } else {
                return stepOp.get();
            }
        }
        return null;
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

    public abstract List<JobExecutionEntity> findRunningJobsForUser(String userId);

    public abstract List<JobDto> findRunningJobsForUserRR(Long userId);

    public abstract void onTaskError(Long taskId);

    @Override
    public String getJobStatus(Long jobId) {
        return jobEntityRepository.findById(jobId).map(JobEntity::getStatus).orElse(null);
    }

    @Override
    public String getTaskStatus(Long taskId) {
        return taskEntityRepository.findById(taskId).map(TaskEntity::getStatus).orElse(null);
    }

    @Override
    @Transactional(transactionManager = "theTransactionManager")
    public void logJob(Long jobId, JobStatus status) {
        jobEntityRepository.updateStatus(jobId, status);
    }

    @Override
    @Transactional(transactionManager = "theTransactionManager")
    public void logTask(Long taskId, JobStatus status) {
        taskEntityRepository.updateStatus(taskId, status);
    }

    @Override
    @Transactional(transactionManager = "theTransactionManager")
    public void logStep(Long stepId, StepStatus status) {
        stepEntityRepository.updateStatus(stepId, status.getCode(), new Date());
    }
}
