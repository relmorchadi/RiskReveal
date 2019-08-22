package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentScalingParameter", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentScalingParameterEntity {
    private int adjustmentScalingParameterId;
    private double factor;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeScaling;

    public AdjustmentScalingParameterEntity(double factor, AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeScaling) {
        this.factor = factor;
        this.adjustmentNodeByFkAdjustmentNodeScaling = adjustmentNodeByFkAdjustmentNodeScaling;
    }

    public AdjustmentScalingParameterEntity() {

    }

    @Id
    @Column(name = "AdjustmentScalingParameterId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentScalingParameterId() {
        return adjustmentScalingParameterId;
    }

    public void setAdjustmentScalingParameterId(int adjustmentScalingParameterId) {
        this.adjustmentScalingParameterId = adjustmentScalingParameterId;
    }

    @Basic
    @Column(name = "factor", nullable = true, precision = 7)
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
    @JoinColumn(name = "FKAdjustmentNodeScaling", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNodeScaling() {
        return adjustmentNodeByFkAdjustmentNodeScaling;
    }

    public void setAdjustmentNodeByFkAdjustmentNodeScaling(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeScaling) {
        this.adjustmentNodeByFkAdjustmentNodeScaling = adjustmentNodeByFkAdjustmentNodeScaling;
    }
}
