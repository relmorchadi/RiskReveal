package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNodeProcessing", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeProcessingEntity {
    private int id;
    private Integer adjustmentNodeId;
    private Integer inputPltId;
    private Integer adjustedPltId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "adjustmentNodeId", nullable = true)
    public Integer getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(Integer adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Basic
    @Column(name = "inputPltId", nullable = true)
    public Integer getInputPltId() {
        return inputPltId;
    }

    public void setInputPltId(Integer inputPltId) {
        this.inputPltId = inputPltId;
    }

    @Basic
    @Column(name = "adjustedPltId", nullable = true)
    public Integer getAdjustedPltId() {
        return adjustedPltId;
    }

    public void setAdjustedPltId(Integer adjustedPltId) {
        this.adjustedPltId = adjustedPltId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeProcessingEntity that = (AdjustmentNodeProcessingEntity) o;
        return id == that.id &&
                Objects.equals(adjustmentNodeId, that.adjustmentNodeId) &&
                Objects.equals(inputPltId, that.inputPltId) &&
                Objects.equals(adjustedPltId, that.adjustedPltId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, adjustmentNodeId, inputPltId, adjustedPltId);
    }
}
