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

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    public double getStdDevI() {
        return stdDevI;
    }

    public void setStdDevI(double stdDevI) {
        this.stdDevI = stdDevI;
    }

    public double getStdDevC() {
        return stdDevC;
    }

    public void setStdDevC(double stdDevC) {
        this.stdDevC = stdDevC;
    }

    public double getExposureValue() {
        return exposureValue;
    }

    public void setExposureValue(double exposureValue) {
        this.exposureValue = exposureValue;
    }
}
