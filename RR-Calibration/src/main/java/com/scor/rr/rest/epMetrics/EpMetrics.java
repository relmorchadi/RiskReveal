package com.scor.rr.rest.epMetrics;

import com.scor.rr.domain.dto.SaveOrDeleteListOfRPsRequest;
import com.scor.rr.domain.enums.CurveType;
import com.scor.rr.service.epMetrics.EpMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/epMetrics")
public class EpMetrics {

    @Autowired
    EpMetricsService epMetricsService;

    @GetMapping
    public ResponseEntity<?> getEpMetrics(@RequestParam String workspaceContextCode, @RequestParam Integer uwYear, @RequestParam CurveType curveType, @RequestParam String screen) { return this.epMetricsService.getEpMetrics( workspaceContextCode, uwYear, curveType, screen);}

    @GetMapping("singlePLT")
    public ResponseEntity<?> getSinglePLTEpMetrics(@RequestParam Long pltHeaderId, @RequestParam CurveType curveType, @RequestParam String screen) { return this.epMetricsService.getSinglePLTEpMetrics( pltHeaderId, curveType, screen);}

    @GetMapping("singlePLTSummaryStats")
    public ResponseEntity<?> getSinglePLTSummaryStats(@RequestParam Long pltHeaderId) { return this.epMetricsService.getSinglePLTSummaryStats(pltHeaderId);}

    @GetMapping("single")
    public ResponseEntity<?> getSingleEpMetric(@RequestParam String workspaceContextCode, @RequestParam Integer uwYear, @RequestParam CurveType curveType, @RequestParam Integer rp) { return this.epMetricsService.getSingleEpMetric(workspaceContextCode, uwYear, curveType, rp);}

    @GetMapping("rp/validate")
    public ResponseEntity<?> validateEpMetric(@RequestParam Integer rp) { return this.epMetricsService.validateEpMetric(rp);}

    @GetMapping("defaultReturnPeriods")
    public ResponseEntity<?> getDefaultReturnPeriods() { return this.epMetricsService.getDefaultReturnPeriods();}

    @PostMapping("rp/save")
    public ResponseEntity<?> saveListOfRPs(@RequestBody SaveOrDeleteListOfRPsRequest request) { return this.epMetricsService.saveListOfRPs(request);}

    @PostMapping("rp/delete")
    public ResponseEntity<?> deleteRP(@RequestBody SaveOrDeleteListOfRPsRequest request) { return this.epMetricsService.deleteRP(request);}
}
