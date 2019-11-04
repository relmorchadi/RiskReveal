package com.scor.rr.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class AnalysisEpCurves {

    private int analysisId;
    private String finPerspCode;
    private int epTypeCode;
    private double loss;
    private BigDecimal execeedanceProb;


    public AnalysisEpCurves(BigDecimal execeedanceProb, double loss) {
        this.loss = loss;
        this.execeedanceProb = execeedanceProb;
    }

    public AnalysisEpCurves(AnalysisEpCurves curves) {
        this.analysisId= curves.getAnalysisId();
        this.finPerspCode= curves.getFinPerspCode();
        this.epTypeCode= curves.getEpTypeCode();
        this.loss = curves.getLoss();
        this.execeedanceProb = curves.getExeceedanceProb();
    }
}
