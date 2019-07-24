package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentNodeEntity {
    private Integer sequence;
    private int idDefaultAdjustmentNode;
    private Boolean isCappedMaxExposure;
    private AdjustmentBasisEntity adjustmentBasis;
    private AdjustmentTypeEntity adjustmentType;
    private DefaultAdjustmentThreadEntity defaultAdjustmentThread;

    @Basic
    @Column(name = "sequence", nullable = true)
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Id
    @Column(name = "id_default_adjustment_node", nullable = false)
    public int getIdDefaultAdjustmentNode() {
        return idDefaultAdjustmentNode;
    }

    public void setIdDefaultAdjustmentNode(int idDefaultAdjustmentNode) {
        this.idDefaultAdjustmentNode = idDefaultAdjustmentNode;
    }

    @Basic
    @Column(name = "is_capped_max_exposure", nullable = true)
    public Boolean getCappedMaxExposure() {
        return isCappedMaxExposure;
    }

    public void setCappedMaxExposure(Boolean cappedMaxExposure) {
        isCappedMaxExposure = cappedMaxExposure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentNodeEntity that = (DefaultAdjustmentNodeEntity) o;
        return idDefaultAdjustmentNode == that.idDefaultAdjustmentNode &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(isCappedMaxExposure, that.isCappedMaxExposure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence, idDefaultAdjustmentNode, isCappedMaxExposure);
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
    @JoinColumn(name = "id_adjustment_type", referencedColumnName = "id_type")
    public AdjustmentTypeEntity getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentTypeEntity adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @ManyToOne
    @JoinColumn(name = "id_adjustment_thread", referencedColumnName = "id_default_adjustment_thread")
    public DefaultAdjustmentThreadEntity getDefaultAdjustmentThread() {
        return defaultAdjustmentThread;
    }

    public void setDefaultAdjustmentThread(DefaultAdjustmentThreadEntity defaultAdjustmentThread) {
        this.defaultAdjustmentThread = defaultAdjustmentThread;
    }
}
