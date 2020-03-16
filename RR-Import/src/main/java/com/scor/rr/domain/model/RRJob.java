package com.scor.rr.domain.model;

import com.scor.rr.domain.TaskEntity;
import com.scor.rr.domain.enums.JobPriority;
import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.repository.JobEntityRepository;
import com.scor.rr.repository.TaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RRJob implements Runnable, Comparable<RRJob> {

    private Job job;
    private JobPriority priority;
    private JobParameters params;
    private JobLauncher jobLauncher;
    private JobExecution execution;
    private Date creationDate = new Date();
    private TaskEntity task;
    private TaskRepository taskRepository;
    private JobEntityRepository jobRepository;

    public RRJob(Job job, JobPriority priority, JobParameters params, JobLauncher jobLauncher) {
        this.job = job;
        this.priority = priority;
        this.params = params;
        this.jobLauncher = jobLauncher;
    }

    public RRJob(Job job, JobPriority priority, JobParameters params, JobLauncher jobLauncher, TaskEntity task, TaskRepository taskRepository, JobEntityRepository jobRepository) {
        this.job = job;
        this.priority = priority;
        this.params = params;
        this.jobLauncher = jobLauncher;
        this.task = task;
        this.taskRepository = taskRepository;
        this.jobRepository = jobRepository;
    }

    @Override
    public void run() {
        try {
            execution = jobLauncher.run(job, params);
            task.setJobExecutionId(execution.getJobId());
            if (task.getJob().getStartedDate() == null && task.getJob().getStatus().equals(JobStatus.PENDING.getCode())) {
                task.getJob().setStartedDate(new Date());
                task.getJob().setStatus(JobStatus.RUNNING.getCode());
            }
            task.setStartedDate(new Date());
            task.setStatus(JobStatus.RUNNING.getCode());
            jobRepository.save(task.getJob());
            taskRepository.save(task);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(RRJob o) {
        // enforces descending order (but this is up to you)
        int priorityComp = Integer.compare(o.priority.getCode(), this.priority.getCode());
        if (priorityComp == 0)
            return this.creationDate.compareTo(o.getCreationDate());
        return priorityComp;
    }

}
