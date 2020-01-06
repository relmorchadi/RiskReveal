package com.scor.rr.rest;

import com.scor.rr.domain.dto.ImportLossDataParams;
import com.scor.rr.domain.dto.ImportParamsAndConfig;
import com.scor.rr.domain.dto.ImportParamsDto;
import com.scor.rr.domain.dto.ImportReferenceData;
import com.scor.rr.service.RefDataService;
import com.scor.rr.service.RmsService;
import com.scor.rr.service.batch.BatchExecution;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("refs")
    public ResponseEntity<ImportReferenceData> getRefData(@RequestParam(value = "carId",required = false) String carId){
        return ResponseEntity.ok(
                refDataService.getImportRefs(carId)
        );
    }

    @PostMapping("/rms")
    public ResponseEntity<?> doImport(@RequestBody ImportLossDataParams importLossDataParams){
        return new ResponseEntity<>(batchExecution.RunImportLossData(importLossDataParams), HttpStatus.OK);
    }
    @PostMapping("/trigger-import")
    public ResponseEntity<?> triggerImport(@RequestBody ImportParamsAndConfig config){
        List<Long> analysisIds= rmsService.saveAnalysisImportSelection(config.getAnalysisConfig());
        List<Long> portfolioIds= rmsService.savePortfolioImportSelection(config.getPortfolioConfig());
        ImportLossDataParams params= new ImportLossDataParams(config, analysisIds, portfolioIds);
        return new ResponseEntity<>(batchExecution.RunImportLossData(params), HttpStatus.OK);
    }
}
