package com.scor.rr.domain;

import lombok.Data;

@Data
public class AnalysisElt {
    private int analysisId;
    private String finPerspCode;
    private Long eventId;
    private double rate;
    private double loss;
    private double stdDevI;
    private double stdDevC;
    private double exposureValue;
}
