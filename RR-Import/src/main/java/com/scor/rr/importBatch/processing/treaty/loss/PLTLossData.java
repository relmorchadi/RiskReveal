package com.scor.rr.importBatch.processing.treaty.loss;

import com.google.common.base.Objects;
import lombok.Data;
import org.apache.commons.lang3.builder.CompareToBuilder;

import java.io.Serializable;

@Data
public class PLTLossData implements Comparable, Serializable {

    protected final int eventId;
    protected final long eventDate;
    protected final int simPeriod; // shared properties among PLTLossData in one analysis!
    protected final int seq;
    protected double maxExposure;
    protected double loss;

    public PLTLossData(int eventId, long eventDate, int simPeriod, int seq, double maxExposure, double loss) {
        this.eventId = eventId;
        this.eventDate = eventDate;
        this.simPeriod = simPeriod;
        this.seq = seq;
        this.maxExposure = maxExposure;
        this.loss = loss;
    }

    public PLTLossData(int simPeriod, Long seq, double maxExposure, double loss) {
        this.eventId = 1;
        this.eventDate = 0;
        this.simPeriod = simPeriod;
        this.seq = seq.intValue();
        this.maxExposure = maxExposure;
        this.loss = loss;
    }

    public PLTLossData(PLTLossData lossData) {
        this.eventId = lossData.eventId;
        this.eventDate = lossData.getEventDate();
        this.simPeriod = lossData.simPeriod;
        this.seq = lossData.seq;
        this.maxExposure = lossData.maxExposure;
        this.loss = lossData.loss;
    }

    // TODO - amend these methods
//    @Override
//    public String toString() {
//        final LocalDateTime localDateTime = new LocalDateTime(eventDate, DateTimeZone.UTC);
//        return new StringBuilder()
//                .append(eventId).append(";")
//                .append(seq).append(";")
//                .append(localDateTime.getDayOfMonth()).append("/").append(localDateTime.getMonthOfYear()).append(";")
//                .append(loss).toString();
//    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof PLTLossData))
            return -1;

        PLTLossData r = (PLTLossData) o;
        return new CompareToBuilder()
                .append(loss, r.loss)
                .append(simPeriod, r.simPeriod)
                .append(eventId, r.eventId)
                .append(seq, r.seq)
                .toComparison();
    }

    /**
     * Warning! Unreliable!
     *
     * @return
     */
    @Override
    public int hashCode() {
        return new StringBuilder().append(loss).append(simPeriod).append(eventId).append(seq).toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PLTLossData other = (PLTLossData) obj;
        return Objects.equal(this.loss, other.loss) && Objects.equal(this.simPeriod, other.simPeriod) && Objects.equal(this.eventId, other.eventId) && Objects.equal(this.seq, other.seq);
    }

    public String hashString() {
        return new StringBuilder()
                .append(eventId)
                .append(simPeriod)
                .append(seq).toString();
    }

}
