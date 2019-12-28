package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "AdjustmentNode")
public class AdjustmentNode {
    private Integer adjustmentNodeId;
    private Integer entity;
    private AdjustmentThread adjustmentThread;
    private AdjustmentProcessingRecap adjustmentProcessingRecap;
    private AdjustmentState adjustmentState;
    private String adjustmentBasisCode;
    private String adjustmentCategoryCode;
    private String adjustmentTypeCode;
    private Boolean capped;
    private String userNarrative;
    private AdjustmentNode cloningSource;

    private List<ReturnPeriodBandingAdjustmentParameter> nonLinearAdjustment;

    private List<ScalingAdjustmentParameter> linearAdjustment;

    @Column(name = "UserNarrative")
    public String getUserNarrative() {
        return userNarrative;
    }

    public void setUserNarrative(String userNarrative) {
        this.userNarrative = userNarrative;
    }

    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    public AdjustmentNode(Boolean cappedMaxExposure,
                          AdjustmentThread adjustmentThread,
                          String adjustmentBasisCode,
                          String adjustmentTypeCode,
                          AdjustmentState adjustmentStateEntity,
                          String adjustmentCategoryCode) {
        this.capped = cappedMaxExposure;
        this.adjustmentThread = adjustmentThread;
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
        this.adjustmentCategoryCode = other.getAdjustmentCategoryCode();
        this.userNarrative = other.getUserNarrative();
        this.entity = other.getEntity();
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
    public AdjustmentThread getAdjustmentThread() {
        return adjustmentThread;
    }

    public void setAdjustmentThread(AdjustmentThread adjustmentThread) {
        this.adjustmentThread = adjustmentThread;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentProcessingRecapId", referencedColumnName = "AdjustmentProcessingRecapId")
    public AdjustmentProcessingRecap getAdjustmentProcessingRecap() {
        return adjustmentProcessingRecap;
    }

    public void setAdjustmentProcessingRecap(AdjustmentProcessingRecap adjustmentProcessingRecap) {
        this.adjustmentProcessingRecap = adjustmentProcessingRecap;
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
        this.adjustmentTypeCode = adjustmentTypeCode;
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


    public String getAdjustmentCategoryCode() {
        return adjustmentCategoryCode;
    }

    public void setAdjustmentCategoryCode(String adjustmentCategoryCode) {
        this.adjustmentCategoryCode = adjustmentCategoryCode;
    }

    @OneToMany(mappedBy = "adjustmentNode")
    @JsonManagedReference
    public List<ReturnPeriodBandingAdjustmentParameter> getNonLinearAdjustment() {
        return nonLinearAdjustment;
    }

    public void setNonLinearAdjustment(List<ReturnPeriodBandingAdjustmentParameter> nonLinearAdjustment) {
        this.nonLinearAdjustment = nonLinearAdjustment;
    }

    @OneToMany(mappedBy = "adjustmentNode")
    @JsonManagedReference
    public List<ScalingAdjustmentParameter> getLinearAdjustment() {
        return linearAdjustment;
    }

    public void setLinearAdjustment(List<ScalingAdjustmentParameter> linearAdjustment) {
        this.linearAdjustment = linearAdjustment;
    }

    @Override
    public String toString() {
        return "AdjustmentNodeEntity{" +
                ", adjustmentNode=" + adjustmentNodeId +
                ", adjustmentThread=" + adjustmentThread +
                '}';
    }
}
