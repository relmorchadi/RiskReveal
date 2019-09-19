package com.scor.rr.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AnalysisSummaryStats {

    private Long analysisId;
    private String fpCode ;
    private int epTypeCode;
    private double purePremium;
    private double stdDev;
    private double cov;

}
