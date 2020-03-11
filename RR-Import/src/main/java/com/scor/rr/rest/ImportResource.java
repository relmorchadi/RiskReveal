package com.scor.rr.rest;

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
        return new ResponseEntity<>(batchExecution.RunImportLossData(params), HttpStatus.OK);
    }

    @GetMapping("/is-job-running")
    public ResponseEntity<?> isJobRunning(@RequestParam Long jobId) {
        return new ResponseEntity<>(jobManager.isJobRunning(jobId), HttpStatus.OK);
    }

    @GetMapping("/cancel-job")
    public ResponseEntity<?> cancelJob(@RequestParam Long jobId) {
        jobManager.cancelJob(jobId);
        return new ResponseEntity<>("Operation done", HttpStatus.OK);
    }

    @GetMapping("/running-jobs")
    public ResponseEntity<?> getRunningJobs(@RequestParam Long userId) {
        try {
            return new ResponseEntity<>(jobManager.findRunningJobsForUser(String.valueOf(userId)), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
