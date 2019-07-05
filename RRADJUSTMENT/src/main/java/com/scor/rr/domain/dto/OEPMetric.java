package com.scor.rr.domain.dto;

public class OEPMetric {

    double frequency;
    double returnPeriod;
    double lossOep;

    public OEPMetric(double frequency, double returnPeriod, double lossOep) {
        this.frequency = frequency;
        this.returnPeriod = returnPeriod;
        this.lossOep = lossOep;
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

    public double getLossOep() {
        return lossOep;
    }

    public void setLossOep(double lossOep) {
        this.lossOep = lossOep;
    }
}
