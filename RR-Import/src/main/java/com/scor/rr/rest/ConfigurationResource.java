package com.scor.rr.rest;


import com.scor.rr.domain.DataSource;
import com.scor.rr.domain.dto.AnalysisHeader;
import com.scor.rr.domain.dto.SourceResultDto;
import com.scor.rr.service.RmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/import/config")
public class ConfigurationResource {

    @Autowired
    RmsService rmsService;

    @PostMapping("add-edm-rdm")
    public ResponseEntity<?> addEmdRdm(@RequestBody List<DataSource> dataSources, @RequestParam Long projectId, @RequestParam String instanceId, @RequestParam String instanceName) {
        rmsService.addEdmRdms(dataSources, projectId, instanceId, instanceName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("analysis-detail-scan")
    public ResponseEntity<?> analysisDetailScan(@RequestBody List<AnalysisHeader> rlAnalysisList, @RequestParam Long projectId) {
        rmsService.scanAnalysisDetail(rlAnalysisList, projectId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("saveSourceResults")
    public ResponseEntity<?> saveSourceResults(@RequestBody List<SourceResultDto> sourceResultDtoList) {
        try {
            return new ResponseEntity<>(rmsService.saveSourceResults(sourceResultDtoList), HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();

            return new ResponseEntity<>("Operation failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
