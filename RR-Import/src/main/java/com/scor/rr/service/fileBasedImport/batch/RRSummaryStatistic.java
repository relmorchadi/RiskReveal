package com.scor.rr.service.fileBasedImport.batch;

import org.springframework.data.annotation.Transient;

import java.io.Serializable;

public class RRSummaryStatistic implements Serializable {
    private Double purePremium;

    private Double standardDeviation;

    private Double cov;

    public RRSummaryStatistic()
    {
        super();
    }

    public RRSummaryStatistic(RRSummaryStatistic summaryStatistic)  // ELTEPSummaryStatistic
    {
        this(summaryStatistic.getPurePremium(), summaryStatistic.getStandardDeviation(), summaryStatistic.getCov());
    }

    public RRSummaryStatistic(Double purePremium,Double standardDeviation,Double cov)
    {
        super();
        this.purePremium=purePremium;
        this.standardDeviation=standardDeviation;
        this.cov=cov;
    }

    public Double getPurePremium()
    {
        return purePremium;
    }

    public void setPurePremium(Double purePremium)
    {
        this.purePremium=purePremium;
    }

    public Double getStandardDeviation()
    {
        return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation)
    {
        this.standardDeviation=standardDeviation;
    }

    public Double getCov()
    {
        return cov;
    }

    public void setCov(Double cov)
    {
        this.cov=cov;
    }

    @Transient
    public Boolean isNull() {
        return (purePremium == null || standardDeviation == null || cov == null) ? Boolean.TRUE : Boolean.FALSE;
    }
}
