package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AdjustmentParameterEntityPK implements Serializable {
    private int adjustmentNodeId;
    private String paramField;

    @Column(name = "adjustmentNodeId", nullable = false)
    @Id
    public int getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(int adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Column(name = "paramField", nullable = false, length = 255,insertable = false ,updatable = false)
    @Id
    public String getParamField() {
        return paramField;
    }

    public void setParamField(String paramField) {
        this.paramField = paramField;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentParameterEntityPK that = (AdjustmentParameterEntityPK) o;
        return adjustmentNodeId == that.adjustmentNodeId &&
                Objects.equals(paramField, that.paramField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeId, paramField);
    }
}
