package com.scor.rr.domain.dto.adjustement.loss;

public class AdjustmentReturnPeriodBending {

    private double returnPeriod;
    private double lmf;

    public AdjustmentReturnPeriodBending() {
    }

    public AdjustmentReturnPeriodBending(double returnPeriod, double lmf) {
        this.returnPeriod = returnPeriod;
        this.lmf = lmf;
    }

    public double getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(double returnPeriod) {
        this.returnPeriod = returnPeriod;
    }

    public double getLmf() {
        return lmf;
    }

    public void setLmf(double lmf) {
        this.lmf = lmf;
    }
}
