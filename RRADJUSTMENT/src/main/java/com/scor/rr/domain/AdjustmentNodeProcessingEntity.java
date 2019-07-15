package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNodeProcessing", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeProcessingEntity {
    private int id;
    private AdjustmentNodeEntity adjustmentNodeByAdjustmentNodeId;
    private ScorPltHeaderEntity scorPltHeaderByInputPltId;
    private ScorPltHeaderEntity scorPltHeaderByAdjustedPltId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeProcessingEntity that = (AdjustmentNodeProcessingEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @ManyToOne
    @JoinColumn(name = "adjustmentNodeId", referencedColumnName = "adjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNodeByAdjustmentNodeId() {
        return adjustmentNodeByAdjustmentNodeId;
    }

    public void setAdjustmentNodeByAdjustmentNodeId(AdjustmentNodeEntity adjustmentNodeByAdjustmentNodeId) {
        this.adjustmentNodeByAdjustmentNodeId = adjustmentNodeByAdjustmentNodeId;
    }

    @ManyToOne
    @JoinColumn(name = "inputPltId", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByInputPltId() {
        return scorPltHeaderByInputPltId;
    }

    public void setScorPltHeaderByInputPltId(ScorPltHeaderEntity scorPltHeaderByInputPltId) {
        this.scorPltHeaderByInputPltId = scorPltHeaderByInputPltId;
    }

    @ManyToOne
    @JoinColumn(name = "adjustedPltId", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByAdjustedPltId() {
        return scorPltHeaderByAdjustedPltId;
    }

    public void setScorPltHeaderByAdjustedPltId(ScorPltHeaderEntity scorPltHeaderByAdjustedPltId) {
        this.scorPltHeaderByAdjustedPltId = scorPltHeaderByAdjustedPltId;
    }
}
