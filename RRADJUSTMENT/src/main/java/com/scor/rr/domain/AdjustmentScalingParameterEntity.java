package com.scor.rr.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentScalingParameter", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentScalingParameterEntity {
    private int idAdjustmentParameter;
    private double factor;
    private AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode;

    public AdjustmentScalingParameterEntity() {
    }

    public AdjustmentScalingParameterEntity(double factor, AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode) {
        this.factor = factor;
        this.adjustmentNodeByIdAdjustmentNode = adjustmentNodeByIdAdjustmentNode;
    }

    @Id
    @Column(name = "id_adjustment_parameter", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getIdAdjustmentParameter() {
        return idAdjustmentParameter;
    }

    public void setIdAdjustmentParameter(int idAdjustmentParameter) {
        this.idAdjustmentParameter = idAdjustmentParameter;
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
        return idAdjustmentParameter == that.idAdjustmentParameter &&
                Objects.equals(factor, that.factor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdjustmentParameter, factor);
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustment_node", referencedColumnName = "id_adjustment_node")
    public AdjustmentNodeEntity getAdjustmentNodeByIdAdjustmentNode() {
        return adjustmentNodeByIdAdjustmentNode;
    }

    public void setAdjustmentNodeByIdAdjustmentNode(AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode) {
        this.adjustmentNodeByIdAdjustmentNode = adjustmentNodeByIdAdjustmentNode;
    }
}
