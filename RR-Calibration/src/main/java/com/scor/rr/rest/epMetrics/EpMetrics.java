package com.scor.rr.rest.epMetrics;

import com.scor.rr.domain.Calibration;
import com.scor.rr.domain.enums.CurveType;
import com.scor.rr.service.Plt.PltService;
import com.scor.rr.service.epMetrics.EpMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/epMetrics")
public class EpMetrics {

    @Autowired
    EpMetricsService epMetricsService;

    @GetMapping
    public ResponseEntity<?> getEpMetrics(@RequestParam String workspaceContextCode, @RequestParam Integer uwYear, @RequestParam CurveType curveType, @RequestParam Integer userId) { return this.epMetricsService.getEpMetrics(userId,workspaceContextCode, uwYear, curveType);}

    @GetMapping("single")
    public ResponseEntity<?> getSingleEpMetric(@RequestParam String workspaceContextCode, @RequestParam Integer uwYear, @RequestParam CurveType curveType, @RequestParam Integer rp) { return this.epMetricsService.getSingleEpMetric(workspaceContextCode, uwYear, curveType, rp);}

   @GetMapping("validate")
   public ResponseEntity<?> validateEpMetric(@RequestParam Integer rp) { return this.epMetricsService.validateEpMetric(rp);}
}
