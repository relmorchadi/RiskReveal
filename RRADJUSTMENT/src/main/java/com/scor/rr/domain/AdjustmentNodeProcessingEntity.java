package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNodeProcessing", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeProcessingEntity {
    private int idNodeProcessing;
    private AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode;
    private ScorPltHeaderEntity scorPltHeaderByIdInputPlt;
    private ScorPltHeaderEntity scorPltHeaderByIdAdjustedPlt;

    @Id
    @Column(name = "id_node_processing", nullable = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    public int getIdNodeProcessing() {
        return idNodeProcessing;
    }

    public void setIdNodeProcessing(int idNodeProcessing) {
        this.idNodeProcessing = idNodeProcessing;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeProcessingEntity that = (AdjustmentNodeProcessingEntity) o;
        return idNodeProcessing == that.idNodeProcessing;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idNodeProcessing);
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustment_node", referencedColumnName = "id_adjustment_node")
    public AdjustmentNodeEntity getAdjustmentNodeByIdAdjustmentNode() {
        return adjustmentNodeByIdAdjustmentNode;
    }

    public void setAdjustmentNodeByIdAdjustmentNode(AdjustmentNodeEntity adjustmentNodeByIdAdjustmentNode) {
        this.adjustmentNodeByIdAdjustmentNode = adjustmentNodeByIdAdjustmentNode;
    }

    @ManyToOne
    @JoinColumn(name = "id_input_plt", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByIdInputPlt() {
        return scorPltHeaderByIdInputPlt;
    }

    public void setScorPltHeaderByIdInputPlt(ScorPltHeaderEntity scorPltHeaderByIdInputPlt) {
        this.scorPltHeaderByIdInputPlt = scorPltHeaderByIdInputPlt;
    }

    @ManyToOne
    @JoinColumn(name = "id_adjusted_plt", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByIdAdjustedPlt() {
        return scorPltHeaderByIdAdjustedPlt;
    }

    public void setScorPltHeaderByIdAdjustedPlt(ScorPltHeaderEntity scorPltHeaderByIdAdjustedPlt) {
        this.scorPltHeaderByIdAdjustedPlt = scorPltHeaderByIdAdjustedPlt;
    }
}
