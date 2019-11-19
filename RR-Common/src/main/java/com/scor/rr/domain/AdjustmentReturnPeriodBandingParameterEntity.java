package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentReturnPeriodBandingParameter", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentReturnPeriodBandingParameterEntity {
    private int adjustmentReturnPeriodBandingParameterId;
    private double returnPeriod;
    private double factor;
    private AdjustmentNode adjustmentNode;

    public AdjustmentReturnPeriodBandingParameterEntity(double returnPeriod, double factor, AdjustmentNode adjustmentNode) {
        this.returnPeriod = returnPeriod;
        this.factor = factor;
        this.adjustmentNode = adjustmentNode;
    }

    public AdjustmentReturnPeriodBandingParameterEntity() {

    }

    public AdjustmentReturnPeriodBandingParameterEntity(double returnPeriod, double factor) {
        this.returnPeriod = returnPeriod;
        this.factor = factor;
    }

    @Id
    @Column(name = "AdjustmentParameterId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentReturnPeriodBandingParameterId() {
        return adjustmentReturnPeriodBandingParameterId;
    }

    public void setAdjustmentReturnPeriodBandingParameterId(int adjustmentReturnPeriodBandingParameterId) {
        this.adjustmentReturnPeriodBandingParameterId = adjustmentReturnPeriodBandingParameterId;
    }

    @Basic
    @Column(name = "ReturnPeriod", nullable = true, precision = 7)
    public double getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(double returnPeriod) {
        this.returnPeriod = returnPeriod;
    }

    @Basic
    @Column(name = "AdjustmentFactor", nullable = true, precision = 7)
    public double getFactor() {
        return factor;
    }

    public void setFactor(double factor) {
        this.factor = factor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentReturnPeriodBandingParameterEntity that = (AdjustmentReturnPeriodBandingParameterEntity) o;
        return adjustmentReturnPeriodBandingParameterId == that.adjustmentReturnPeriodBandingParameterId &&
                Objects.equals(returnPeriod, that.returnPeriod) &&
                Objects.equals(factor, that.factor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentReturnPeriodBandingParameterId, returnPeriod, factor);
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
    }
}
