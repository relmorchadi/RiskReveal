package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentParameter", schema = "dbo", catalog = "RiskReveal")
@IdClass(AdjustmentParameterEntityPK.class)
public class AdjustmentParameterEntity {
    private int adjustmentNodeId;
    private String paramField;
    private String paramValue;
    private String paramType;
    private AdjustmentNodeEntity adjustmentNodeByAdjustmentNodeId;

    @Id
    @Column(name = "adjustmentNodeId", nullable = false,insertable = false ,updatable = false)
    public int getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(int adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Id
    @Column(name = "paramField", nullable = false, length = 255,insertable = false ,updatable = false)
    public String getParamField() {
        return paramField;
    }

    public void setParamField(String paramField) {
        this.paramField = paramField;
    }

    @Basic
    @Column(name = "paramValue", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    @Basic
    @Column(name = "paramType", nullable = true, length = 255,insertable = false ,updatable = false)
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
        return adjustmentNodeId == that.adjustmentNodeId &&
                Objects.equals(paramField, that.paramField) &&
                Objects.equals(paramValue, that.paramValue) &&
                Objects.equals(paramType, that.paramType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeId, paramField, paramValue, paramType);
    }

    @ManyToOne
    @JoinColumn(name = "adjustmentNodeId", referencedColumnName = "adjustmentNodeId", nullable = false,insertable = false ,updatable = false)
    public AdjustmentNodeEntity getAdjustmentNodeByAdjustmentNodeId() {
        return adjustmentNodeByAdjustmentNodeId;
    }

    public void setAdjustmentNodeByAdjustmentNodeId(AdjustmentNodeEntity adjustmentNodeByAdjustmentNodeId) {
        this.adjustmentNodeByAdjustmentNodeId = adjustmentNodeByAdjustmentNodeId;
    }
}
