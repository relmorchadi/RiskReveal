package com.scor.rr.domain.dto.adjustement.loss;

import org.apache.commons.lang3.builder.EqualsBuilder;

public class PEATData {

    private final int eventId;
    private final int simPeriod; // shared properties among PLTLossData in one analysis!
    private final int seq;
    private double lmf;

    public PEATData(int eventId, int simPeriod, int seq, double lmf) {
        this.eventId = eventId;
        this.simPeriod = simPeriod;
        this.seq = seq;
        this.lmf = lmf;
    }

    public int getEventId() {
        return eventId;
    }

    public int getSimPeriod() {
        return simPeriod;
    }

    public int getSeq() {
        return seq;
    }

    public double getLmf() {
        return lmf;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(eventId).append(";")
                .append(simPeriod).append(";")
                .append(seq).append(";")
                .append(lmf).toString();
    }

    /**
     * Danger! Duplicated hashes detected
     * @return
     */
    @Override
    public int hashCode() {
        return new StringBuilder()
                .append(eventId)
                .append(simPeriod)
                .append(seq).toString().hashCode();
    }

    public String hashString() {
        return new StringBuilder()
                .append(eventId)
                .append(simPeriod)
                .append(seq).toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PEATData)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        PEATData rhs = (PEATData) obj;
        return new EqualsBuilder()
                .append(eventId, rhs.getEventId())
                .append(simPeriod, rhs.getSimPeriod())
                .append(seq, rhs.getSeq())
                .isEquals();
    }

}
