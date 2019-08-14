package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentReturnPeriodBandingParameter", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentReturnPeriodBandingParameterEntity {
    private int idAdjustmentParameter;
    private double returnPeriod;
    private double factor;
    private AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode;

    public AdjustmentReturnPeriodBandingParameterEntity() {
    }

    public AdjustmentReturnPeriodBandingParameterEntity(double returnPeriod, double factor, AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode) {
        this.returnPeriod = returnPeriod;
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
    @Column(name = "return_period", nullable = true, precision = 7)
    public double getReturnPeriod() {
        return returnPeriod;
    }

    public void setReturnPeriod(double returnPeriod) {
        this.returnPeriod = returnPeriod;
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
        AdjustmentReturnPeriodBandingParameterEntity that = (AdjustmentReturnPeriodBandingParameterEntity) o;
        return idAdjustmentParameter == that.idAdjustmentParameter &&
                Objects.equals(returnPeriod, that.returnPeriod) &&
                Objects.equals(factor, that.factor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdjustmentParameter, returnPeriod, factor);
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
