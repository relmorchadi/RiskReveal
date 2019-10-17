package com.scor.rr.domain;

import lombok.Data;

@Data
public class AnalysisSummaryStats {

    private Long analysisId;
    private String fpCode ;
    private int epTypeCode;
    private double purePremium;
    private double stdDev;
    private double cov;

}
