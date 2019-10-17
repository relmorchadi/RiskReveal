package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AnalysisEpCurves {

    private int analysisId;
    private String finPerspCode;
    private int epTypeCode;
    private double loss;
    private BigDecimal execeedanceProb;

}
