package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNodeProcessing", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeProcessingEntity {
    private int adjustmentNodeProcessingId;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNode;
    private PltHeaderEntity adjustedPlt;
    private PltHeaderEntity inputPlt;

    @Id
    @Column(name = "AdjustmentNodeProcessingId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentNodeProcessingId() {
        return adjustmentNodeProcessingId;
    }

    public void setAdjustmentNodeProcessingId(int adjustmentNodeProcessingId) {
        this.adjustmentNodeProcessingId = adjustmentNodeProcessingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeProcessingEntity that = (AdjustmentNodeProcessingEntity) o;
        return adjustmentNodeProcessingId == that.adjustmentNodeProcessingId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentNodeProcessingId);
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNode", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNode() {
        return adjustmentNodeByFkAdjustmentNode;
    }

    public void setAdjustmentNodeByFkAdjustmentNode(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNode) {
        this.adjustmentNodeByFkAdjustmentNode = adjustmentNodeByFkAdjustmentNode;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustedPlt", referencedColumnName = "PltHeaderId")
    public PltHeaderEntity getAdjustedPlt() {
        return adjustedPlt;
    }

    public void setAdjustedPlt(PltHeaderEntity adjustedPlt) {
        this.adjustedPlt = adjustedPlt;
    }

    @ManyToOne
    @JoinColumn(name = "InputPlt", referencedColumnName = "PltHeaderId")
    public PltHeaderEntity getInputPlt() {
        return inputPlt;
    }

    public void setInputPlt(PltHeaderEntity inputPlt) {
        this.inputPlt = inputPlt;
    }
}
