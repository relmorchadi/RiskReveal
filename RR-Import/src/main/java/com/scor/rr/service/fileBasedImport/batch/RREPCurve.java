package com.scor.rr.service.fileBasedImport.batch;

import org.apache.commons.lang.builder.EqualsBuilder;

import java.io.Serializable;

public class RREPCurve implements Serializable {

    private Double exceedanceProbability;

    private Double lossAmount;

    private Integer returnPeriod;

    public RREPCurve()
    {
        super();
    }

    public RREPCurve(Integer returnPeriod, Double exceedanceProbability, Double lossAmount)
    {
        super();
        this.returnPeriod = returnPeriod;
        this.exceedanceProbability=exceedanceProbability;
        this.lossAmount=lossAmount;
    }

    public RREPCurve(RREPCurve eltepCurve)
    {
        this(eltepCurve.getReturnPeriod(), eltepCurve.getExceedanceProbability(), eltepCurve.getLossAmount());
    }

    public Double getExceedanceProbability()
    {
        return exceedanceProbability;
    }

    public void setExceedanceProbability(Double exceedanceProbability)
    {
        this.exceedanceProbability=exceedanceProbability;
    }

    public Double getLossAmount()
    {
        return lossAmount;
    }

    public void setLossAmount(Double lossAmount)
    {
        this.lossAmount=lossAmount;
    }

    public Integer getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(Integer returnPeriod) {
        this.returnPeriod = returnPeriod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        RREPCurve that = (RREPCurve) o;

        return new EqualsBuilder()
                .append(getLossAmount(), that.getLossAmount())
                .append(getReturnPeriod(), that.getReturnPeriod())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return returnPeriod;
    }

    public RREPCurve(Double exceedanceProbability, Double lossAmount) {
        this(null, exceedanceProbability, lossAmount);
    }

}