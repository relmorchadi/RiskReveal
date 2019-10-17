package com.scor.rr.importBatch.processing.domain;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Objects;

/**
 * Created by U002629 on 19/02/2015.
 */
public class PLTResult implements Comparable {

    private final int eventId;
    private final long eventDate;
    private final int seq;
    private final double loss;

    public PLTResult(int eventId, int seq, double loss, long eventDate) {
        this.eventId = eventId;
        this.seq = seq;
        this.loss = loss;
        this.eventDate = eventDate;
    }

    public int getEventId() {
        return eventId;
    }

    public int getSeq() {
        return seq;
    }

    public double getLoss() {
        return loss;
    }

    public long getEventDate() {
        return eventDate;
    }

    @Override
    public String toString() {
        final LocalDateTime localDateTime = new LocalDateTime(eventDate, DateTimeZone.UTC);
        return new StringBuilder()
                .append(eventId).append(";")
                .append(seq).append(";")
                .append(localDateTime.getDayOfMonth()).append("/").append(localDateTime.getMonthOfYear()).append(";")
                .append(loss).toString();
    }

    @Override
    public int compareTo(Object o) {
        if(! (o instanceof PLTResult))
            return -1;

        PLTResult r = (PLTResult)o;
        return new CompareToBuilder()
                .append(eventId, r.eventId)
                .append(seq, r.seq)
                .append(eventDate, r.eventDate)
                .toComparison();
    }


    @Override
    public int hashCode() {
        return Objects.hash(eventId, seq, loss, eventDate);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final PLTResult other = (PLTResult) obj;
        return Objects.equals(this.eventId, other.eventId) && Objects.equals(this.seq, other.seq) && Objects.equals(this.loss, other.loss) && Objects.equals(this.eventDate, other.eventDate);
    }
}
