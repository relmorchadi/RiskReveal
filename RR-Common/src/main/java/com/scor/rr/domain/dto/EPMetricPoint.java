package com.scor.rr.domain.dto;

/**
 * Created by u004602 on 09/12/2019.
 */
public class EPMetricPoint {
    private int rank;
    private double frequency;
    private double returnPeriod;
    private double loss;

    public EPMetricPoint(double frequency, double returnPeriod, double loss, int rank) {
        this.frequency = frequency;
        this.returnPeriod = returnPeriod;
        this.loss = loss;
        this.rank = rank;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
