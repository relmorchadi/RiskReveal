package com.scor.rr.service;

import com.scor.rr.service.abstraction.JobManagerAbstraction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.stereotype.Service;

import java.util.PriorityQueue;

@Service("jobManagerImpl")
@Slf4j
public class JobManagerImpl extends JobManagerAbstraction {

    private PriorityQueue<Job> importQueue = new PriorityQueue<>();

    @Override
    public void submitJob(Long jobId) {

    }

    @Override
    public void cancelJob(Long jobId) {

    }

    @Override
    public void submitTask(Long taskId) {

    }

    @Override
    public void cancelTask(Long taskId) {

    }

    @Override
    public boolean isJobRunning(Long jobId) {
        return false;
    }

    @Override
    public boolean isTaskRunning(Long taskId) {
        return false;
    }
}
