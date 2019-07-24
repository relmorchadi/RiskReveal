package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class AdjustmentParameterEntityPK implements Serializable {
    private int idAdjustmentNode;
    private String paramField;

    @Column(name = "id_adjustment_node", nullable = false)
    @Id
    public int getIdAdjustmentNode() {
        return idAdjustmentNode;
    }

    public void setIdAdjustmentNode(int idAdjustmentNode) {
        this.idAdjustmentNode = idAdjustmentNode;
    }

    @Column(name = "param_field", nullable = false, length = 255)
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
        return idAdjustmentNode == that.idAdjustmentNode &&
                Objects.equals(paramField, that.paramField);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAdjustmentNode, paramField);
    }
}
