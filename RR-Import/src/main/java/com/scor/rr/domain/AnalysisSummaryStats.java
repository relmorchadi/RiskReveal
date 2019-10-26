package com.scor.rr.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class AnalysisSummaryStats {

    private Long analysisId;
    private String fpCode ;
    private int epTypeCode;
    private double purePremium;
    private double stdDev;
    private double cov;

}
