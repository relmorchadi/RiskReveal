package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentParameter", schema = "dbo", catalog = "RiskReveal")
@IdClass(AdjustmentParameterEntityPK.class)
public class AdjustmentParameterEntity {
    private int idAdjustmentNode;
    private String paramField;
    private String paramValue;
    private String paramType;
    private AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode;

    @Id
    @Column(name = "id_adjustment_node", nullable = false)
    public int getIdAdjustmentNode() {
        return idAdjustmentNode;
    }

    public void setIdAdjustmentNode(int idAdjustmentNode) {
        this.idAdjustmentNode = idAdjustmentNode;
    }

    @Id
    @Column(name = "param_field", nullable = false, length = 255)
    public String getParamField() {
        return paramField;
    }

    public void setParamField(String paramField) {
        this.paramField = paramField;
    }

    @Basic
    @Column(name = "param_value", length = 255)
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Basic
    @Column(name = "param_type", length = 255)
    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentParameterEntity that = (AdjustmentParameterEntity) o;
        return idAdjustmentNode == that.idAdjustmentNode &&
                Objects.equals(paramField, that.paramField) &&
                Objects.equals(paramValue, that.paramValue) &&
                Objects.equals(paramType, that.paramType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdjustmentNode, paramField, paramValue, paramType);
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustment_node", referencedColumnName = "id_adjustment_node", nullable = false,insertable = false, updatable = false)
    public AdjustmentNodeEntity getAdjustmentNodeByIdAdjustmentNode() {
        return adjustmentNodeByIdAdjustmentNode;
    }

    public void setAdjustmentNodeByIdAdjustmentNode(AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode) {
        this.adjustmentNodeByIdAdjustmentNode = adjustmentNodeByIdAdjustmentNode;
    }
}
