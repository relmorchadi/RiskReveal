package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentNode", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentNodeEntity {
    private int adjustmentNodeId;
    private EntityEntity entity;
    private AdjustmentThreadEntity adjustmentThread;
    private AdjustmentStateEntity adjustmentState;
    private AdjustmentBasisEntity adjustmentBasis;
    private AdjustmentCategoryEntity adjustmentCategory;
    private AdjustmentTypeEntity adjustmentType;
    private Boolean capped;
    private String userNarrative;
    private AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeIdCloning;


    @ManyToOne
    @JoinColumn(name = "Entity", referencedColumnName = "EntityId",insertable = false,updatable = false)
    public EntityEntity getEntity() {
        return entity;
    }

    public void setEntity(EntityEntity entity) {
        this.entity = entity;
    }

    public AdjustmentNodeEntity(Boolean cappedMaxExposure,
                                AdjustmentThreadEntity adjustmentThreadEntity,
                                AdjustmentBasisEntity adjustmentBasis,
                                AdjustmentTypeEntity adjustmentType,
                                AdjustmentStateEntity adjustmentStateEntity,
                                AdjustmentCategoryEntity adjustmentCategory) {
        this.capped = cappedMaxExposure;
        this.adjustmentThread = adjustmentThreadEntity;
        this.adjustmentBasis = adjustmentBasis;
        this.adjustmentType = adjustmentType;
        this.adjustmentState = adjustmentStateEntity;
        this.adjustmentCategory = adjustmentCategory;
    }

    public AdjustmentNodeEntity() {

    }

    public AdjustmentNodeEntity(AdjustmentNodeEntity other) {
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
    public AdjustmentBasisEntity getAdjustmentBasis() {
        return adjustmentBasis;
    }

    public void setAdjustmentBasis(AdjustmentBasisEntity adjustmentBasis) {
        this.adjustmentBasis = adjustmentBasis;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentTypeId", referencedColumnName = "AdjustmentTypeId")
    public AdjustmentTypeEntity getAdjustmentType() {
        return adjustmentType;
    }

    public void setAdjustmentType(AdjustmentTypeEntity adjustmentType) {
        this.adjustmentType = adjustmentType;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeState", referencedColumnName = "AdjustmentStateId")
    public AdjustmentStateEntity getAdjustmentState() {
        return adjustmentState;
    }

    public void setAdjustmentState(AdjustmentStateEntity adjustmentState) {
        this.adjustmentState = adjustmentState;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeIdCloning", referencedColumnName = "AdjustmentNodeId",insertable = false,updatable = false)
    public AdjustmentNodeEntity getAdjustmentNodeByFkAdjustmentNodeIdCloning() {
        return adjustmentNodeByFkAdjustmentNodeIdCloning;
    }

    public void setAdjustmentNodeByFkAdjustmentNodeIdCloning(AdjustmentNodeEntity adjustmentNodeByFkAdjustmentNodeIdCloning) {
        this.adjustmentNodeByFkAdjustmentNodeIdCloning = adjustmentNodeByFkAdjustmentNodeIdCloning;
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
