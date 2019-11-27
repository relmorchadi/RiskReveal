package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNodeProcessing")
public class AdjustmentNodeProcessingEntity {
    private int adjustmentNodeProcessingId;
    private AdjustmentNode adjustmentNode;
    private PltHeaderEntity adjustedPLT;
    private PltHeaderEntity inputPLT;

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
    @JoinColumn(name = "AdjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustedPLTId", referencedColumnName = "PltHeaderId")
    public PltHeaderEntity getAdjustedPLT() {
        return adjustedPLT;
    }

    public void setAdjustedPLT(PltHeaderEntity adjustedPLT) {
        this.adjustedPLT = adjustedPLT;
    }

    @ManyToOne
    @JoinColumn(name = "InputPLTId", referencedColumnName = "PltHeaderId")
    public PltHeaderEntity getInputPLT() {
        return inputPLT;
    }

    public void setInputPLT(PltHeaderEntity inputPLT) {
        this.inputPLT = inputPLT;
    }
}
