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

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public String getFpCode() {
        return fpCode;
    }

    public void setFpCode(String fpCode) {
        this.fpCode = fpCode;
    }

    public int getEpTypeCode() {
        return epTypeCode;
    }

    public void setEpTypeCode(int epTypeCode) {
        this.epTypeCode = epTypeCode;
    }

    public double getPurePremium() {
        return purePremium;
    }

    public void setPurePremium(double purePremium) {
        this.purePremium = purePremium;
    }

    public double getStdDev() {
        return stdDev;
    }

    public void setStdDev(double stdDev) {
        this.stdDev = stdDev;
    }

    public double getCov() {
        return cov;
    }

    public void setCov(double cov) {
        this.cov = cov;
    }
}
