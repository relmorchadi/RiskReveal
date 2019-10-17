package com.scor.rr.importBatch.processing.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.scope.context.JobSynchronizationManager;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import java.util.concurrent.ThreadFactory;

/**
 * Created by U002629 on 10/06/2015.
 */
public class RRTaskExecutor extends SimpleAsyncTaskExecutor {
    public RRTaskExecutor() {
        super();
    }

    public RRTaskExecutor(String threadNamePrefix) {
        super(threadNamePrefix);
    }

    public RRTaskExecutor(ThreadFactory threadFactory) {
        super(threadFactory);
    }

    @Override
    protected void doExecute(final Runnable task) {
        //gets the jobExecution of the configuration thread
        final JobExecution jobExecution = JobSynchronizationManager.getContext().getJobExecution();
        super.doExecute(new Runnable() {
            @Override
            public void run() {
                JobSynchronizationManager.register(jobExecution);
                try {
                    task.run();
                } finally {
                    JobSynchronizationManager.release();
                }
            }
        });
    }
}
