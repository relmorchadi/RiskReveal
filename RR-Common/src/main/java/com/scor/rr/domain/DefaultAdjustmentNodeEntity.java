package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentNodeEntity {
    private Integer sequence;
    private int defaultAdjustmentNodeId;
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
    @Column(name = "DefaultAdjustmentNodeId", nullable = false)
    public int getDefaultAdjustmentNodeId() {
        return defaultAdjustmentNodeId;
    }

    public void setDefaultAdjustmentNodeId(int defaultAdjustmentNodeId) {
        this.defaultAdjustmentNodeId = defaultAdjustmentNodeId;
    }

    @Basic
    @Column(name = "IsCappedMaxExposure", nullable = true)
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
        return defaultAdjustmentNodeId == that.defaultAdjustmentNodeId &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(isCappedMaxExposure, that.isCappedMaxExposure);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence, defaultAdjustmentNodeId, isCappedMaxExposure);
    }

    @ManyToOne
    @JoinColumn(name = "FKAdjustmentBasisId", referencedColumnName = "AdjustmentBasisId")
    public AdjustmentBasisEntity getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(AdjustmentBasisEntity adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    @ManyToOne
    @JoinColumn(name = "FKAdjustmentTypeId", referencedColumnName = "AdjustmentTypeId")
    public AdjustmentTypeEntity getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentTypeEntity adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @ManyToOne
    @JoinColumn(name = "FKAdjustmentThreadId", referencedColumnName = "DefaultAdjustmentThreadId")
    public DefaultAdjustmentThreadEntity getDefaultAdjustmentThread() {
        return defaultAdjustmentThread;
    }

    public void setDefaultAdjustmentThread(DefaultAdjustmentThreadEntity defaultAdjustmentThread) {
        this.defaultAdjustmentThread = defaultAdjustmentThread;
    }
}
