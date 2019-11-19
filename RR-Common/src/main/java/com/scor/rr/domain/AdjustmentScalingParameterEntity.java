package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentScalingParameter", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentScalingParameterEntity {
    private int adjustmentScalingParameterId;
    private double factor;
    private AdjustmentNode adjustmentNode;

    public AdjustmentScalingParameterEntity(double factor, AdjustmentNode adjustmentNode) {
        this.factor = factor;
        this.adjustmentNode = adjustmentNode;
    }

    public AdjustmentScalingParameterEntity() {

    }

    @Id
    @Column(name = "AdjustmentParameterId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentScalingParameterId() {
        return adjustmentScalingParameterId;
    }

    public void setAdjustmentScalingParameterId(int adjustmentScalingParameterId) {
        this.adjustmentScalingParameterId = adjustmentScalingParameterId;
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
        AdjustmentScalingParameterEntity that = (AdjustmentScalingParameterEntity) o;
        return adjustmentScalingParameterId == that.adjustmentScalingParameterId &&
                Objects.equals(factor, that.factor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentScalingParameterId, factor);
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
