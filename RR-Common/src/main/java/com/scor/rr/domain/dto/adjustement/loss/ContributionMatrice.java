package com.scor.rr.domain.dto.adjustement.loss;

import lombok.Data;

import java.util.List;
@Data
public class ContributionMatrice implements Cloneable {

    private int simPeriod;
    private int eventId;
    private long eventDate;
    private int seq;
    private List<Float> contributions;

    public ContributionMatrice() {
    }
    public Object clone()throws CloneNotSupportedException{
        return (ContributionMatrice)super.clone();
    }

    public ContributionMatrice(int simPeriod, int eventId, long eventDate, int seq, List<Float> contributions) {
        this.simPeriod = simPeriod;
        this.eventId = eventId;
        this.eventDate = eventDate;
        this.seq = seq;
        this.contributions = contributions;
    }
}
