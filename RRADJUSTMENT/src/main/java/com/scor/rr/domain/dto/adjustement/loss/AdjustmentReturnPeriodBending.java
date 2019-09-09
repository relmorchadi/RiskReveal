package com.scor.rr.domain.dto.adjustement.loss;

public class AdjustmentReturnPeriodBending {

    private Double returnPeriod;
    private Double lmf;

    public AdjustmentReturnPeriodBending() {
    }

    public AdjustmentReturnPeriodBending(Double returnPeriod, Double lmf) {
        this.returnPeriod = returnPeriod;
        this.lmf = lmf;
    }

    public Double getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(Double returnPeriod) {
        this.returnPeriod = returnPeriod;
    }

    public Double getLmf() {
        return lmf;
    }

    public void setLmf(Double lmf) {
        this.lmf = lmf;
    }
}
