package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeEntity {
    private Integer sequence;
    private Boolean capped;
    private int adjustmentNodeId;
    private AdjustmentThreadEntity adjustmentThread;
    private AdjustmentBasisEntity adjustmentBasis;
    private AdjustmentNodeEntity adjustmentNode;
    private AdjustmentTypeEntity adjustmentType;
    private AdjustmentStateEntity adjustmentState;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeIdCloning;

    public AdjustmentNodeEntity(Integer sequence, Boolean cappedMaxExposure, AdjustmentThreadEntity adjustmentThreadEntity, AdjustmentBasisEntity adjustmentBasis, AdjustmentTypeEntity adjustmentType, AdjustmentStateEntity adjustmentStateEntityByCodeValid) {
        this.sequence = sequence;
        this.capped = cappedMaxExposure;
        this.adjustmentThread = adjustmentThreadEntity;
        this.adjustmentBasis = adjustmentBasis;
        this.adjustmentType = adjustmentType;
        this.adjustmentState = adjustmentStateEntityByCodeValid;
    }

    public AdjustmentNodeEntity() {

    }

    public AdjustmentNodeEntity(AdjustmentNodeEntity other) {
        this.capped = other.capped;
        this.adjustmentThread = other.adjustmentThread;
        this.adjustmentBasis = other.adjustmentBasis;
        this.adjustmentNode = other.adjustmentNode;
        this.adjustmentType = other.adjustmentType;
        this.adjustmentState = other.adjustmentState;
    }

    @Basic
    @Column(name = "sequence")
    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Basic
    @Column(name = "Capped")
    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }

    @Id
    @Column(name = "AdjustmentNodeId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(int adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNodeEntity that = (AdjustmentNodeEntity) o;
        return adjustmentNodeId == that.adjustmentNodeId &&
                Objects.equals(sequence, that.sequence) &&
                Objects.equals(capped, that.capped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sequence, capped, adjustmentNodeId);
    }

    @ManyToOne
    @JoinColumn(name = "FKAdjustmentThreadId", referencedColumnName = "AdjustmentThreadId")
    public AdjustmentThreadEntity getAdjustmentThread() {
        return adjustmentThread;
    }

    public void setAdjustmentThread(AdjustmentThreadEntity adjustmentThread) {
        this.adjustmentThread = adjustmentThread;
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
    @JoinColumn(name = "FKAdjustmentNodeIdCloning", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNodeEntity getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNodeEntity adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
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
    @JoinColumn(name = "FKAdjustmentStateId", referencedColumnName = "AdjustmentStateId")
    public AdjustmentStateEntity getAdjustmentState() {
        return adjustmentState;
    }

    public void setAdjustmentState(AdjustmentStateEntity adjustmentState) {
        this.adjustmentState = adjustmentState;
    }

    @ManyToOne
    @JoinColumn(name = "FKAdjustmentNodeIdCloning", referencedColumnName = "AdjustmentNodeId",insertable = false,updatable = false)
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNodeIdCloning() {
        return adjustmentNodeByFkAdjustmentNodeIdCloning;
    }

    public void setAdjustmentNodeByFkAdjustmentNodeIdCloning(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeIdCloning) {
        this.adjustmentNodeByFkAdjustmentNodeIdCloning = adjustmentNodeByFkAdjustmentNodeIdCloning;
    }

    @Override
    public String toString() {
        return "AdjustmentNodeEntity{" +
                ", adjustmentNodeId=" + adjustmentNodeId +
                ", adjustmentThread=" + adjustmentThread +
                '}';
    }
}
