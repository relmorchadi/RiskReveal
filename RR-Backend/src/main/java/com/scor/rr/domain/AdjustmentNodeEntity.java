package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeEntity {
    private int adjustmentNodeId;
    private Integer adjustmentThreadId;
    private String layer;
    private Integer sequence;
    private String adjustmentType;
    private String adjustmentBasis;
    private String status;

    @Id
    @Column(name = "adjustmentNodeId", nullable = false)
    public int getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(int adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Basic
    @Column(name = "adjustmentThreadId", nullable = true)
    public Integer getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(Integer adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    @Basic
    @Column(name = "layer", nullable = true, length = 255)
    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    @Basic
    @Column(name = "sequence", nullable = true)
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Basic
    @Column(name = "adjustmentType", nullable = true, length = 255)
    public String getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(String adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @Basic
    @Column(name = "adjustmentBasis", nullable = true, length = 255)
    public String getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(String adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeEntity that = (AdjustmentNodeEntity) o;
        return adjustmentNodeId == that.adjustmentNodeId &&
                Objects.equals(adjustmentThreadId, that.adjustmentThreadId) &&
                Objects.equals(layer, that.layer) &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(adjustmentType, that.adjustmentType) &&
                Objects.equals(adjustmentBasis, that.adjustmentBasis) &&
                Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeId, adjustmentThreadId, layer, sequence, adjustmentType, adjustmentBasis, status);
    }
}
