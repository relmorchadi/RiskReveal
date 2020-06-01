package com.scor.rr.rest;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.dto.ImportLossDataParams;
import com.scor.rr.domain.dto.ImportParamsAndConfig;
import com.scor.rr.domain.dto.ImportReferenceData;
import com.scor.rr.service.RefDataService;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.abstraction.JobManager;
import com.scor.rr.service.batch.BatchExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/import")
public class ImportResource {


    @Autowired
    private BatchExecution batchExecution;

    @Autowired
    private RefDataService refDataService;

    @Autowired
    private RmsService rmsService;

    @Autowired
    @Qualifier(value = "jobManagerImpl")
    private JobManager jobManager;

    @GetMapping("refs")
    public ResponseEntity<ImportReferenceData> getRefData(@RequestParam(value = "carId", required = false) String carId) {
        return ResponseEntity.ok(
                refDataService.getImportRefs(carId)
        );
    }

    @PostMapping("/rms")
    public ResponseEntity<?> doImport(@RequestBody ImportLossDataParams importLossDataParams) {
        return new ResponseEntity<>(batchExecution.RunImportLossData(importLossDataParams), HttpStatus.OK);
    }

    @PostMapping("/trigger-import")
    public ResponseEntity<?> triggerImport(@RequestBody ImportParamsAndConfig config) {
        List<Long> analysisIds = rmsService.saveAnalysisImportSelection(config.getAnalysisConfig());
        List<Long> portfolioIds = rmsService.savePortfolioImportSelection(config.getPortfolioConfig());
        ImportLossDataParams params = new ImportLossDataParams(config, analysisIds, portfolioIds);
        return new ResponseEntity<>(batchExecution.queueImportLossData(params.getInstanceId(), Long.valueOf(params.getProjectId()), Long.valueOf(params.getUserId())), HttpStatus.OK);
    }

    @GetMapping("/is-job-running")
    public ResponseEntity<?> isJobRunning(@RequestParam Long jobId) {
        return new ResponseEntity<>(jobManager.isJobRunning(jobId), HttpStatus.OK);
    }

    @GetMapping("/cancel-job-or-task")
    public ResponseEntity<?> cancel(@RequestParam Long id, @RequestParam String type) {
        try {
            if (type.equalsIgnoreCase("task")) {
                jobManager.cancelTask(id);
                return new ResponseEntity<>("Operation done", HttpStatus.OK);
            } else if (type.equalsIgnoreCase("job"))
                return new ResponseEntity<>(jobManager.cancelJob(id), HttpStatus.OK);
            else
                return new ResponseEntity<>("Not a supported type", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/resume-job")
    public ResponseEntity<?> resume(@RequestParam Long jobId) {
        try {
            return new ResponseEntity<>(batchExecution.resumeJobAfterPausing(jobId), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Operation Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user-jobs")
    public ResponseEntity<?> getRunningJobs() {
        try {
            if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null)
                return new ResponseEntity<>(jobManager.findRunningJobsForUserRR(
                        ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getUserId()), HttpStatus.OK);
            else
                return new ResponseEntity<>("Unidentified user", HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/pause-job")
    public ResponseEntity<?> pauseJob(@RequestParam Long jobId) {
        try {
            return new ResponseEntity<>(jobManager.pauseJob(jobId), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/view-task-details")
    public ResponseEntity<?> taskDetails(@RequestParam Long jobId) {
        try {
            jobManager.pauseJob(jobId);
            return new ResponseEntity<>("Operation done", HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
