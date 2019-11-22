package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalysisSummaryStats {

    private Long analysisId;
    private String fpCode ;
    private int epTypeCode;
    private double purePremium = 0d;
    private double stdDev = 0d;
    private double cov = 0d;
}
