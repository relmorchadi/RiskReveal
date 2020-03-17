package com.scor.rr.domain.model;

import com.scor.rr.domain.enums.JobPriority;
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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RRJob implements Runnable, Comparable<RRJob> {

    private Job job;
    private JobPriority priority;
    private JobParameters params;
    private JobLauncher jobLauncher;
    private JobExecution execution;

    public RRJob(Job job, JobPriority priority, JobParameters params, JobLauncher jobLauncher) {
        this.job = job;
        this.priority = priority;
        this.params = params;
        this.jobLauncher = jobLauncher;
    }

    @Override
    public void run() {
        try {
            execution = jobLauncher.run(job, params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(RRJob o) {
        // enforces descending order (but this is up to you)
        return Integer.compare(o.priority.getCode(), this.priority.getCode());
    }

}
