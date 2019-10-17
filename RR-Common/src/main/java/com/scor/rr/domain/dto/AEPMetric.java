package com.scor.rr.domain.dto;

public class AEPMetric {
    double frequency;
    double returnPeriod;
    double lossAep;

    public AEPMetric(double frequency, double returnPeriod, double lossAep) {
        this.frequency = frequency;
        this.returnPeriod = returnPeriod;
        this.lossAep = lossAep;
    }

    public double getFrequency() {
        return frequency;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public double getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(double returnPeriod) {
        this.returnPeriod = returnPeriod;
    }

    public double getLossAep() {
        return lossAep;
    }

    public void setLossAep(double lossOep) {
        this.lossAep = lossOep;
    }
}
