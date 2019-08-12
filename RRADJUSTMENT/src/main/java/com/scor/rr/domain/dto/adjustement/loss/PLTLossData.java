package com.scor.rr.domain.dto.adjustement.loss;


import java.util.Objects;

public class PLTLossData {

    private int simPeriod;
    private int eventId;
    private double eventDate;
    private int seq;
    private double maxExposure;
    private double loss;

    public PLTLossData(int simPeriod, int eventId, double eventDate, int seq, double maxExposure, double loss) {
        this.simPeriod = simPeriod;
        this.eventId = eventId;
        this.eventDate = eventDate;
        this.seq = seq;
        this.maxExposure = maxExposure;
        this.loss = loss;
    }

    public PLTLossData(int eventId, double eventDate, int simPeriod, int seq, double maxExposure, double loss) {
        this.eventId = eventId;
        this.eventDate = eventDate;
        this.simPeriod = simPeriod;
        this.seq = seq;
        this.maxExposure = maxExposure;
        this.loss = loss;
    }

    public PLTLossData(PLTLossData lossData) {
        this.eventId = lossData.eventId;
        this.eventDate = lossData.eventDate;
        this.simPeriod = lossData.simPeriod;
        this.seq = lossData.seq;
        this.maxExposure = lossData.maxExposure;
        this.loss = lossData.loss;
    }
    public PLTLossData(PLTLossData lossData,Double loss) {
        this.eventId = lossData.eventId;
        this.eventDate = lossData.eventDate;
        this.simPeriod = lossData.simPeriod;
        this.seq = lossData.seq;
        this.maxExposure = lossData.maxExposure;
        this.loss = loss;
    }

    public PLTLossData() {

    }

    public int getSimPeriod() {
        return simPeriod;
    }

    public void setSimPeriod(int simPeriod) {
        this.simPeriod = simPeriod;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public double getEventDate() {
        return eventDate;
    }

    public void setEventDate(double eventDate) {
        this.eventDate = eventDate;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public double getMaxExposure() {
        return maxExposure;
    }

    public void setMaxExposure(double maxExposure) {
        this.maxExposure = maxExposure;
    }

    public double getLoss() {
        return loss;
    }

    public void setLoss(double loss) {
        this.loss = loss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PLTLossData that = (PLTLossData) o;
        return simPeriod == that.simPeriod &&
                eventId == that.eventId &&
                Double.compare(that.eventDate, eventDate) == 0 &&
                seq == that.seq &&
                Double.compare(that.maxExposure, maxExposure) == 0 &&
                Double.compare(that.loss, loss) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(simPeriod, eventId, eventDate, seq, maxExposure, loss);
    }
}
