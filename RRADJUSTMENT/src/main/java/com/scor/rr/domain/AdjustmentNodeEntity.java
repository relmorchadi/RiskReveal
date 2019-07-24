package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeEntity {
    private Integer sequence;
    private Boolean capped;
    private int idAdjustmentNode;
    private AdjustmentThreadEntity adjustmentThread;
    private AdjustmentBasisEntity adjustmentBasis;
    private AdjustmentNodeEntity adjustmentNodeById;
    private AdjustmentTypeEntity adjustmentType;
    private AdjustmentStateEntity adjustmentState;

    @Basic
    @Column(name = "sequence", nullable = true)
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Basic
    @Column(name = "capped", nullable = true)
    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }

    @Id
    @Column(name = "id_adjustment_node", nullable = false)
    public int getIdAdjustmentNode() {
        return idAdjustmentNode;
    }

    public void setIdAdjustmentNode(int idAdjustmentNode) {
        this.idAdjustmentNode = idAdjustmentNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeEntity that = (AdjustmentNodeEntity) o;
        return idAdjustmentNode == that.idAdjustmentNode &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(capped, that.capped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence, capped, idAdjustmentNode);
    }

    @ManyToOne
    @JoinColumn(name = "adjustmentThreadId", referencedColumnName = "id_adjustment_thread")
    public AdjustmentThreadEntity getAdjustmentThread() {
        return adjustmentThread;
    }

    public void setAdjustmentThread(AdjustmentThreadEntity adjustmentThread) {
        this.adjustmentThread = adjustmentThread;
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustment_basis", referencedColumnName = "code")
    public AdjustmentBasisEntity getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(AdjustmentBasisEntity adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustmentNodeEntityCloning", referencedColumnName = "id_adjustment_node")
    public AdjustmentNodeEntity getAdjustmentNodeById() {
        return adjustmentNodeById;
    }

    public void setAdjustmentNodeById(AdjustmentNodeEntity adjustmentNodeById) {
        this.adjustmentNodeById = adjustmentNodeById;
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustment_type", referencedColumnName = "id_type")
    public AdjustmentTypeEntity getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentTypeEntity adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @ManyToOne
    @JoinColumn(name = "id_state", referencedColumnName = "id_state")
    public AdjustmentStateEntity getAdjustmentState() {
        return adjustmentState;
    }

    public void setAdjustmentState(AdjustmentStateEntity adjustmentState) {
        this.adjustmentState = adjustmentState;
    }
}
