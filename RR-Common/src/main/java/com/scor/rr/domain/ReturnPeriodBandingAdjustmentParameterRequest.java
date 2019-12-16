package com.scor.rr.domain;

public class ReturnPeriodBandingAdjustmentParameterRequest {
    private double returnPeriod;
    private double adjustmentFactor;

    public ReturnPeriodBandingAdjustmentParameterRequest() {

    }

    public ReturnPeriodBandingAdjustmentParameterRequest(double returnPeriod, double adjustmentFactor) {
        this.returnPeriod = returnPeriod;
        this.adjustmentFactor = adjustmentFactor;
    }
    public double getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(double returnPeriod) {
        this.returnPeriod = returnPeriod;
    }

    public double getAdjustmentFactor() {
        return adjustmentFactor;
    }

    public void setAdjustmentFactor(double adjustmentFactor) {
        this.adjustmentFactor = adjustmentFactor;
    }
}
