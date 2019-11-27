package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "DefaultReturnPeriodBandingAdjustmentParameter")
public class DefaultReturnPeriodBandingAdjustmentParameter {
    private Long id;
    private Integer entity;
    private DefaultAdjustmentNode adjustmentNode;
    private double returnPeriod;
    private double adjustmentFactor;

    @Id
    @Column(name = "DefaultReturnPeriodBandingAdjustmentParameterId", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    @ManyToOne
    @JoinColumn(name = "DefaultAdjustmentNodeId", referencedColumnName = "DefaultAdjustmentNodeId")
    public DefaultAdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(DefaultAdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
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
    public double getAdjustmentFactor() {
        return adjustmentFactor;
    }

    public void setAdjustmentFactor(double adjustmentFactor) {
        this.adjustmentFactor = adjustmentFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultReturnPeriodBandingAdjustmentParameter that = (DefaultReturnPeriodBandingAdjustmentParameter) o;
        return id == that.id &&
                Objects.equals(adjustmentNode, that.adjustmentNode) &&
                Objects.equals(returnPeriod, that.returnPeriod) &&
                Objects.equals(adjustmentFactor, that.adjustmentFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adjustmentNode, returnPeriod, adjustmentFactor);
    }
}
