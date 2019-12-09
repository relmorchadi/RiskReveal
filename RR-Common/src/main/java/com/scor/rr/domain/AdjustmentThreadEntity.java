package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "AdjustmentThread")
public class AdjustmentThreadEntity {
    private Boolean locked;
//    private String createdBy;
//    private Timestamp createdOn;
//    private String lastModifiedBy;
//    private Timestamp lastModifiedOn;
//    private Timestamp lastGeneratedOn;
//    private Timestamp generatedOn;
    private Integer adjustmentThreadId;
    private PltHeaderEntity initialPLT;
    private PltHeaderEntity finalPLT;
    private int threadIndex;
    private Integer entity;
    private String threadStatus;

    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    @Basic
    @Column(name = "ThreadIndex")
    public int getThreadIndex() {
        return threadIndex;
    }

    public void setThreadIndex(int threadIndex) {
        this.threadIndex = threadIndex;
    }

    @Basic
    @Column(name = "isLocked")
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Basic
    @Column(name = "ThreadStatus", length = 100)
    public String getThreadStatus() {
        return threadStatus;
    }

    public void setThreadStatus(String threadStatus) {
        this.threadStatus = threadStatus;
    }

//    @Basic
//    @Column(name = "CreatedBy", length = 100)
//    public String getCreatedBy() {
//        return createdBy;
//    }
//
//    public void setCreatedBy(String createdBy) {
//        this.createdBy = createdBy;
//    }
//
//    @Basic
//    @Column(name = "CreatedOn")
//    public Timestamp getCreatedOn() {
//        return createdOn;
//    }
//
//    public void setCreatedOn(Timestamp createdOn) {
//        this.createdOn = createdOn;
//    }
//
//    @Basic
//    @Column(name = "LastModifiedBy", length = 100)
//    public String getLastModifiedBy() {
//        return lastModifiedBy;
//    }
//
//    public void setLastModifiedBy(String lastModifiedBy) {
//        this.lastModifiedBy = lastModifiedBy;
//    }
//
//    @Basic
//    @Column(name = "LastModifiedOn")
//    public Timestamp getLastModifiedOn() {
//        return lastModifiedOn;
//    }
//
//    public void setLastModifiedOn(Timestamp lastModifiedOn) {
//        this.lastModifiedOn = lastModifiedOn;
//    }
//
//    @Basic
//    @Column(name = "LastGeneratedOn")
//    public Timestamp getLastGeneratedOn() {
//        return lastGeneratedOn;
//    }
//
//    public void setLastGeneratedOn(Timestamp lastGeneratedOn) {
//        this.lastGeneratedOn = lastGeneratedOn;
//    }
//
//    @Basic
//    @Column(name = "GeneratedOn")
//    public Timestamp getGeneratedOn() {
//        return generatedOn;
//    }
//
//    public void setGeneratedOn(Timestamp generatedOn) {
//        this.generatedOn = generatedOn;
//    }

    @Id
    @Column(name = "AdjustmentThreadId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(Integer adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        AdjustmentThreadEntity that = (AdjustmentThreadEntity) o;
//        return adjustmentThreadId == that.adjustmentThreadId &&
//                Objects.equals(isLocked, that.isLocked) &&
//                Objects.equals(createdBy, that.createdBy) &&
//                Objects.equals(createdOn, that.createdOn) &&
//                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
//                Objects.equals(lastModifiedOn, that.lastModifiedOn) &&
//                Objects.equals(lastGeneratedOn, that.lastGeneratedOn) &&
//                Objects.equals(generatedOn, that.generatedOn);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(isLocked, createdBy, createdOn, lastModifiedBy, lastModifiedOn, lastGeneratedOn, generatedOn, adjustmentThreadId);
//    }

    @ManyToOne
    @JoinColumn(name = "InitialPLT", referencedColumnName = "PltHeaderId")
    public PltHeaderEntity getInitialPLT() {
        return initialPLT;
    }

    public void setInitialPLT(PltHeaderEntity initialPLT) {
        this.initialPLT = initialPLT;
    }

    @ManyToOne
    @JoinColumn(name = "FinalPLT", referencedColumnName = "PltHeaderId")
    public PltHeaderEntity getFinalPLT() {
        return finalPLT;
    }

    public void setFinalPLT(PltHeaderEntity finalPLT) {
        this.finalPLT = finalPLT;
    }

    @Override
    public String toString() {
        return "AdjustmentThreadEntity{" +
                " adjustmentThreadId=" + adjustmentThreadId +
                '}';
    }
}
