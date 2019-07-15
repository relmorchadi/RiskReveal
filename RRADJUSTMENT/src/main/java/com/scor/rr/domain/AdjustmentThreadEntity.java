package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentThread", schema = "dbo", catalog = "RiskReveal")
@JsonIgnoreProperties({"scorPltHeaderByPurePltId","scorPltHeaderByThreadPltId"})
public class AdjustmentThreadEntity {
    private int adjustmentThreadId;
    private String threadType;
    private Boolean locked;
    private String createdBy;
    private Timestamp createdOn;
    private String accessBy;
    private Timestamp accessOn;
    private String lastModifiedBy;
    private Timestamp lastModifiedOn;
    private Timestamp lastGeneratedOn;
    private Timestamp generatedOn;
    private ScorPltHeaderEntity scorPltHeaderByPurePltId;
    private ScorPltHeaderEntity scorPltHeaderByThreadPltId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adjustmentThreadId", nullable = false)
    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    @Basic
    @Column(name = "threadType", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    @Basic
    @Column(name = "locked", nullable = true)
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
    
    @Basic
    @Column(name = "created_by", nullable = true, length = 100)
    public java.lang.String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(java.lang.String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "created_on", nullable = true)
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "access_by", nullable = true, length = 100)
    public java.lang.String getAccessBy() {
        return accessBy;
    }

    public void setAccessBy(java.lang.String accessBy) {
        this.accessBy = accessBy;
    }

    @Basic
    @Column(name = "access_on", nullable = true)
    public Timestamp getAccessOn() {
        return accessOn;
    }

    public void setAccessOn(Timestamp accessOn) {
        this.accessOn = accessOn;
    }

    @Basic
    @Column(name = "last_modified_by", nullable = true, length = 100)
    public java.lang.String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(java.lang.String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "last_modified_on", nullable = true)
    public Timestamp getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Timestamp lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Basic
    @Column(name = "last_generated_on", nullable = true)
    public Timestamp getLastGeneratedOn() {
        return lastGeneratedOn;
    }

    public void setLastGeneratedOn(Timestamp lastGeneratedOn) {
        this.lastGeneratedOn = lastGeneratedOn;
    }

    @Basic
    @Column(name = "generated_on", nullable = true)
    public Timestamp getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(Timestamp generatedOn) {
        this.generatedOn = generatedOn;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentThreadEntity that = (AdjustmentThreadEntity) o;
        return adjustmentThreadId == that.adjustmentThreadId &&
                Objects.equals(threadType, that.threadType) &&
                Objects.equals(locked, that.locked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentThreadId, threadType, locked);
    }

    @ManyToOne
    @JoinColumn(name = "purePltId", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByPurePltId() {
        return scorPltHeaderByPurePltId;
    }

    public void setScorPltHeaderByPurePltId(ScorPltHeaderEntity scorPltHeaderByPurePltId) {
        this.scorPltHeaderByPurePltId = scorPltHeaderByPurePltId;
    }

    @ManyToOne
    @JoinColumn(name = "threadPltId", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByThreadPltId() {
        return scorPltHeaderByThreadPltId;
    }

    public void setScorPltHeaderByThreadPltId(ScorPltHeaderEntity scorPltHeaderByThreadPltId) {
        this.scorPltHeaderByThreadPltId = scorPltHeaderByThreadPltId;
    }
}
