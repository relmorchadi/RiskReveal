package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentThread", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentThreadEntity {
    private String threadType;
    private Boolean locked;
    private String createdBy;
    private Timestamp createdOn;
    private String lastModifiedBy;
    private Timestamp lastModifiedOn;
    private Timestamp lastGeneratedOn;
    private Timestamp generatedOn;
    private int adjustmentThreadId;
    private PltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadId;
    private PltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadPureId;

    @Basic
    @Column(name = "ThreadType", length = 255)
    public String getThreadType() {
        return threadType;
    }

    public void setThreadType(String threadType) {
        this.threadType = threadType;
    }

    @Basic
    @Column(name = "locked")
    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    @Basic
    @Column(name = "CreatedBy", length = 100)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "CreatedOn")
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "LastModifiedBy", length = 100)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "LastModifiedOn")
    public Timestamp getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Timestamp lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Basic
    @Column(name = "LastGeneratedOn")
    public Timestamp getLastGeneratedOn() {
        return lastGeneratedOn;
    }

    public void setLastGeneratedOn(Timestamp lastGeneratedOn) {
        this.lastGeneratedOn = lastGeneratedOn;
    }

    @Basic
    @Column(name = "GeneratedOn")
    public Timestamp getGeneratedOn() {
        return generatedOn;
    }

    public void setGeneratedOn(Timestamp generatedOn) {
        this.generatedOn = generatedOn;
    }

    @Id
    @Column(name = "AdjustmentThreadId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getAdjustmentThreadId() {
        return adjustmentThreadId;
    }

    public void setAdjustmentThreadId(int adjustmentThreadId) {
        this.adjustmentThreadId = adjustmentThreadId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentThreadEntity that = (AdjustmentThreadEntity) o;
        return adjustmentThreadId == that.adjustmentThreadId &&
                Objects.equals(threadType, that.threadType) &&
                Objects.equals(locked, that.locked) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
                Objects.equals(lastModifiedOn, that.lastModifiedOn) &&
                Objects.equals(lastGeneratedOn, that.lastGeneratedOn) &&
                Objects.equals(generatedOn, that.generatedOn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(threadType, locked, createdBy, createdOn, lastModifiedBy, lastModifiedOn, lastGeneratedOn, generatedOn, adjustmentThreadId);
    }

    @ManyToOne
    @JoinColumn(name = "ScorPltHeaderThreadId", referencedColumnName = "ScorPltHeaderId")
    public PltHeaderEntity getScorPltHeaderByFkScorPltHeaderThreadId() {
        return scorPltHeaderByFkScorPltHeaderThreadId;
    }

    public void setScorPltHeaderByFkScorPltHeaderThreadId(PltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadId) {
        this.scorPltHeaderByFkScorPltHeaderThreadId = scorPltHeaderByFkScorPltHeaderThreadId;
    }

    @ManyToOne
    @JoinColumn(name = "ScorPltHeaderThreadPureId", referencedColumnName = "ScorPltHeaderId")
    public PltHeaderEntity getScorPltHeaderByFkScorPltHeaderThreadPureId() {
        return scorPltHeaderByFkScorPltHeaderThreadPureId;
    }

    public void setScorPltHeaderByFkScorPltHeaderThreadPureId(PltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadPureId) {
        this.scorPltHeaderByFkScorPltHeaderThreadPureId = scorPltHeaderByFkScorPltHeaderThreadPureId;
    }

    @Override
    public String toString() {
        return "AdjustmentThreadEntity{" +
                " adjustmentThreadId=" + adjustmentThreadId +
                '}';
    }
}
