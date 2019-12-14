package com.scor.rr.domain.dto;

/**
 * Created by u004602 on 09/12/2019.
 */
public class EPMetricPoint {
    private double frequency;
    private double returnPeriod;
    private double loss;

    public EPMetricPoint(double frequency, double returnPeriod, double loss) {
        this.frequency = frequency;
        this.returnPeriod = returnPeriod;
        this.loss = loss;
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

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }
}
