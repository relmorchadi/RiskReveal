package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "AdjustmentNode")
public class AdjustmentNode {
    private Integer adjustmentNodeId;
    private Integer entity;
    private AdjustmentThreadEntity adjustmentThread;
    private AdjustmentState adjustmentState;
    private String adjustmentBasisCode;
    private String adjustmentCategoryCode;
    private String adjustmentTypeCode;
    private Boolean capped;
    private String userNarrative;
    private AdjustmentNode cloningSource;


    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    public AdjustmentNode(Boolean cappedMaxExposure,
                          AdjustmentThreadEntity adjustmentThreadEntity,
                          String adjustmentBasisCode,
                          String adjustmentTypeCode,
                          AdjustmentState adjustmentStateEntity,
                          String adjustmentCategoryCode) {
        this.capped = cappedMaxExposure;
        this.adjustmentThread = adjustmentThreadEntity;
        this.adjustmentBasisCode = adjustmentBasisCode;
        this.adjustmentTypeCode = adjustmentTypeCode;
        this.adjustmentState = adjustmentStateEntity;
        this.adjustmentCategoryCode = adjustmentCategoryCode;
    }

    public AdjustmentNode() {

    }

    public AdjustmentNode(AdjustmentNode other) {
        this.capped = other.capped;
        this.adjustmentThread = other.adjustmentThread;
        this.adjustmentBasisCode = other.adjustmentBasisCode;
        this.adjustmentTypeCode = other.adjustmentTypeCode;
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

    @Column(name = "AdjustmentBasisCode")
    public String getAdjustmentBasisCode() {
        return adjustmentBasisCode;
    }

    public void setAdjustmentBasisCode(String adjustmentBasisCode) {
        this.adjustmentBasisCode = adjustmentBasisCode;
    }


    @Column(name = "AdjustmentTypeCode")
    public String getAdjustmentTypeCode() {
        return adjustmentTypeCode;
    }

    public void setAdjustmentTypeCode(String adjustmentTypeCode) {
        this.adjustmentBasisCode = adjustmentTypeCode;
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
    public AdjustmentNode getCloningSource() {
        return cloningSource;
    }

    public void setCloningSource(AdjustmentNode cloningSource) {
        this.cloningSource = cloningSource;
    }

    @Column(name = "AdjustmentCategoryCode")
    public String getAdjustmentCategoryCode() {
        return adjustmentCategoryCode;
    }

    public void setAdjustmentCategoryCode(String adjustmentCategoryCode) {
        this.adjustmentCategoryCode = adjustmentCategoryCode;
    }

    @Override
    public String toString() {
        return "AdjustmentNodeEntity{" +
                ", adjustmentNodeId=" + adjustmentNodeId +
                ", adjustmentThread=" + adjustmentThread +
                '}';
    }
}
