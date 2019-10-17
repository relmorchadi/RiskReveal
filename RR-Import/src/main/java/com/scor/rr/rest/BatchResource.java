package com.scor.rr.rest;

import com.scor.rr.importBatch.processing.batch.RequestCache;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

@RestController
public class BatchResource {

    @Autowired
    @Qualifier(value = "myJobLauncher")
    JobLauncher jobLauncher;

    @Autowired
    @Qualifier(value = "myJobRegistry")
    JobRegistry jobRegistry;

    @Autowired
    @Qualifier(value = "myJobService")
    private JobService jobService;

    @Qualifier("requestCache")
    @Autowired
    private RequestCache cache;

    @GetMapping("trigger")
    public ResponseEntity runBatchJob(
            @RequestParam("batchJobName") String batchJobName,
            @RequestParam("catReqId") String catReqId,
            @RequestParam("edm") String edm,
            @RequestParam("edmId") long edmId,
            @RequestParam("rdm") String rdm,
            @RequestParam("rdmId") long rdmId,
            @RequestParam("portfolio") String portfolio,
            @RequestParam("division") String division,
            @RequestParam("periodBasis") String periodBasis,
            @RequestParam("version") String version,
            @RequestParam("fp1") String fp1,
            @RequestParam("fp2") String fp2,
            @RequestParam("instanceId") String instanceId
    ) throws JobExecutionException {
        Job job = jobRegistry.getJob(batchJobName);
        JobParametersBuilder builder = new JobParametersBuilder(job.getJobParametersIncrementer().getNext(jobService.getLastJobParameters(batchJobName)))
                .addString("catReqId", catReqId)
                .addString("edm", edm)
                .addLong("edmId", edmId)
                .addString("rdm", rdm)
                .addLong("rdmId", rdmId)
                .addString("portfolio", portfolio)
                .addString("division", division)
                .addString("periodBasis", periodBasis)
                .addLong("version", version == null ? 0l : Long.parseLong(version))
                .addString("fpELT", fp1)
                .addString("fpStats", fp2)
                .addString("instanceId", instanceId)
                .addString("correlationId", UUID.randomUUID().toString())
                .addDate("runDate", new Date());
        JobExecution execution = jobLauncher.run(job, builder.toJobParameters());
        Long jobExecutionId = execution.getId();
        cache.queueRequest(catReqId, division, periodBasis);
        return new ResponseEntity(jobExecutionId, HttpStatus.OK);
    }
}
