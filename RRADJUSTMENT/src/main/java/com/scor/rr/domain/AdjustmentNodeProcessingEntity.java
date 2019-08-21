package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNodeProcessing", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeProcessingEntity {
    private int adjustmentNodeProcessingId;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNode;
    private ScorPltHeaderEntity scorPltHeaderByFkAdjustedPlt;
    private ScorPltHeaderEntity scorPltHeaderByFkInputPlt;

    @Id
    @Column(name = "AdjustmentNodeProcessingId", nullable = false)
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
    @JoinColumn(name = "FKAdjustmentNode", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNode() {
        return adjustmentNodeByFkAdjustmentNode;
    }

    public void setAdjustmentNodeByFkAdjustmentNode(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNode) {
        this.adjustmentNodeByFkAdjustmentNode = adjustmentNodeByFkAdjustmentNode;
    }

    @ManyToOne
    @JoinColumn(name = "FKAdjustedPlt", referencedColumnName = "PKScorPltHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByFkAdjustedPlt() {
        return scorPltHeaderByFkAdjustedPlt;
    }

    public void setScorPltHeaderByFkAdjustedPlt(ScorPltHeaderEntity scorPltHeaderByFkAdjustedPlt) {
        this.scorPltHeaderByFkAdjustedPlt = scorPltHeaderByFkAdjustedPlt;
    }

    @ManyToOne
    @JoinColumn(name = "FKInputPlt", referencedColumnName = "PKScorPltHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByFkInputPlt() {
        return scorPltHeaderByFkInputPlt;
    }

    public void setScorPltHeaderByFkInputPlt(ScorPltHeaderEntity scorPltHeaderByFkInputPlt) {
        this.scorPltHeaderByFkInputPlt = scorPltHeaderByFkInputPlt;
    }
}
