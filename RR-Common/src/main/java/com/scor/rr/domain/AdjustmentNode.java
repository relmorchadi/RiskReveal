package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "AdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNode {
    private Integer adjustmentNodeId;
    private RREntity RREntity;
    private AdjustmentThreadEntity adjustmentThread;
    private AdjustmentState adjustmentState;
    private AdjustmentBasis adjustmentBasis;
    private AdjustmentCategoryEntity adjustmentCategory;
    private AdjustmentType adjustmentType;
    private Boolean capped;
    private String userNarrative;
    private AdjustmentNode adjustmentNodeCloning;


    @ManyToOne
    @JoinColumn(name = "RREntity", referencedColumnName = "EntityId",insertable = false,updatable = false)
    public RREntity getRREntity() {
        return RREntity;
    }

    public void setRREntity(RREntity RREntity) {
        this.RREntity = RREntity;
    }

    public AdjustmentNode(Boolean cappedMaxExposure,
                          AdjustmentThreadEntity adjustmentThreadEntity,
                          AdjustmentBasis adjustmentBasis,
                          AdjustmentType adjustmentType,
                          AdjustmentState adjustmentStateEntity,
                          AdjustmentCategoryEntity adjustmentCategory) {
        this.capped = cappedMaxExposure;
        this.adjustmentThread = adjustmentThreadEntity;
        this.adjustmentBasis = adjustmentBasis;
        this.adjustmentType = adjustmentType;
        this.adjustmentState = adjustmentStateEntity;
        this.adjustmentCategory = adjustmentCategory;
    }

    public AdjustmentNode() {

    }

    public AdjustmentNode(AdjustmentNode other) {
        this.capped = other.capped;
        this.adjustmentThread = other.adjustmentThread;
        this.adjustmentBasis = other.adjustmentBasis;
        this.adjustmentType = other.adjustmentType;
        this.adjustmentState = other.adjustmentState;
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
    public Integer getAdjustmentNodeId() {
        return adjustmentNodeId;
    }

    public void setAdjustmentNodeId(Integer adjustmentNodeId) {
        this.adjustmentNodeId = adjustmentNodeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentNode that = (AdjustmentNode) o;
        return adjustmentNodeId == that.adjustmentNodeId &&
                Objects.equals(capped, that.capped);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capped, adjustmentNodeId);
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentThreadId", referencedColumnName = "AdjustmentThreadId")
    public AdjustmentThreadEntity getAdjustmentThread() {
        return adjustmentThread;
    }

    public void setAdjustmentThread(AdjustmentThreadEntity adjustmentThread) {
        this.adjustmentThread = adjustmentThread;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentBasisId", referencedColumnName = "AdjustmentBasisId")
    public AdjustmentBasis getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(AdjustmentBasis adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentTypeId", referencedColumnName = "AdjustmentTypeId")
    public AdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeState", referencedColumnName = "AdjustmentStateId")
    public AdjustmentState getAdjustmentState() {
        return adjustmentState;
    }

    public void setAdjustmentState(AdjustmentState adjustmentState) {
        this.adjustmentState = adjustmentState;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeIdCloning", referencedColumnName = "AdjustmentNodeId", insertable = false, updatable = false)
    public AdjustmentNode getAdjustmentNodeCloning() {
        return adjustmentNodeCloning;
    }

    public void setAdjustmentNodeCloning(AdjustmentNode adjustmentNodeCloning) {
        this.adjustmentNodeCloning = adjustmentNodeCloning;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentCategoryId", referencedColumnName = "AdjustmentCategoryId")
    public AdjustmentCategoryEntity getAdjustmentCategory() {
        return adjustmentCategory;
    }

    public void setAdjustmentCategory(AdjustmentCategoryEntity adjustmentCategory) {
        this.adjustmentCategory = adjustmentCategory;
    }

    @Override
    public String toString() {
        return "AdjustmentNodeEntity{" +
                ", adjustmentNodeId=" + adjustmentNodeId +
                ", adjustmentThread=" + adjustmentThread +
                '}';
    }
}
