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

    public int getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(int analysisId) {
        this.analysisId = analysisId;
    }

    public String getFinPerspCode() {
        return finPerspCode;
    }

    public void setFinPerspCode(String finPerspCode) {
        this.finPerspCode = finPerspCode;
    }

    public int getEpTypeCode() {
        return epTypeCode;
    }

    public void setEpTypeCode(int epTypeCode) {
        this.epTypeCode = epTypeCode;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public BigDecimal getExeceedanceProb() {
        return execeedanceProb;
    }

    public void setExeceedanceProb(BigDecimal execeedanceProb) {
        this.execeedanceProb = execeedanceProb;
    }
}
