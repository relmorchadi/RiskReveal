package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.hibernate.annotations.JoinFormula;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ReturnPeriodBandingAdjustmentParameter")
public class ReturnPeriodBandingAdjustmentParameter {
    private Long id;
    private AdjustmentNode adjustmentNode;
    private double returnPeriod;
    private double adjustmentFactor;
    private Integer entity;

    public ReturnPeriodBandingAdjustmentParameter(Double returnPeriod, Double adjustmentFactor, AdjustmentNode adjustmentNode) {
        this.returnPeriod = returnPeriod;
        this.adjustmentFactor = adjustmentFactor;
        this.adjustmentNode = adjustmentNode;
    }

    public ReturnPeriodBandingAdjustmentParameter() {

    }

    public ReturnPeriodBandingAdjustmentParameter(double returnPeriod, double adjustmentFactor) {
        this.returnPeriod = returnPeriod;
        this.adjustmentFactor = adjustmentFactor;
    }

    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    @Id
    @Column(name = "AdjustmentParameterId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        ReturnPeriodBandingAdjustmentParameter that = (ReturnPeriodBandingAdjustmentParameter) o;
        return id == that.id &&
                Objects.equals(returnPeriod, that.returnPeriod) &&
                Objects.equals(adjustmentFactor, that.adjustmentFactor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, returnPeriod, adjustmentFactor);
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    @JsonBackReference
    public AdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
    }

}
