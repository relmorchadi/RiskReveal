package com.scor.rr.service.epMetrics;

import com.scor.rr.domain.dto.ValidateEpMetricResponse;
import com.scor.rr.domain.enums.CurveType;
import com.scor.rr.repository.DefaultReturnPeriodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class EpMetricsService {

    @Autowired
    DefaultReturnPeriodRepository defaultReturnPeriodRepository;

    public ResponseEntity<?> validateEpMetric(Integer rp) {
        return ResponseEntity.ok(
                this.getValidateEpMetricResponse(
                        this.defaultReturnPeriodRepository.validateRP(rp)
                )
        );
    }

    private ValidateEpMetricResponse getValidateEpMetricResponse(List<Map<String, Object>> input) {
        if( input.size() > 0 ) {
            Map<String, Object> tmp = input.get(0);
            return new ValidateEpMetricResponse(
                    (Integer) tmp.get("lowerBound"),
                    (Integer) tmp.get("upperBound"),
                    (Boolean) tmp.get("isValid")
            );

        } else return new ValidateEpMetricResponse(null, null, null);
    }

    public ResponseEntity<?> getEpMetrics(Integer userId, String workspaceContextCode, Integer uwYear, CurveType curveType) {
        try {
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findEpMetricsByWorkspaceAndUserAndCurveType(userId, workspaceContextCode, uwYear, curveType.getCurveType()));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<?> getSingleEpMetric(String workspaceContextCode, Integer uwYear, CurveType curveType, Integer rp) {
        try {
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findSingleEpMetricsByWorkspaceAndCurveTypeAndRP(workspaceContextCode, uwYear, curveType.getCurveType(), rp));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<?> getDefaultReturnPeriods() {
        try {
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findByIsTableRPOrderByReturnPeriodAsc());
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
