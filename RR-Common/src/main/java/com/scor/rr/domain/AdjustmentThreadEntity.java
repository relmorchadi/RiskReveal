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
    private ScorPltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadId;
    private ScorPltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadPureId;

    @Basic
    @Column(name = "ThreadType", nullable = true, length = 255)
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
    @Column(name = "CreatedBy", nullable = true, length = 100)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "CreatedOn", nullable = true)
    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }

    @Basic
    @Column(name = "LastModifiedBy", nullable = true, length = 100)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Basic
    @Column(name = "LastModifiedOn", nullable = true)
    public Timestamp getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Timestamp lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Basic
    @Column(name = "LastGeneratedOn", nullable = true)
    public Timestamp getLastGeneratedOn() {
        return lastGeneratedOn;
    }

    public void setLastGeneratedOn(Timestamp lastGeneratedOn) {
        this.lastGeneratedOn = lastGeneratedOn;
    }

    @Basic
    @Column(name = "GeneratedOn", nullable = true)
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
    @JoinColumn(name = "FKScorPltHeaderThreadId", referencedColumnName = "PKScorPltHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByFkScorPltHeaderThreadId() {
        return scorPltHeaderByFkScorPltHeaderThreadId;
    }

    public void setScorPltHeaderByFkScorPltHeaderThreadId(ScorPltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadId) {
        this.scorPltHeaderByFkScorPltHeaderThreadId = scorPltHeaderByFkScorPltHeaderThreadId;
    }

    @ManyToOne
    @JoinColumn(name = "FKScorPltHeaderThreadPureId", referencedColumnName = "PKScorPltHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByFkScorPltHeaderThreadPureId() {
        return scorPltHeaderByFkScorPltHeaderThreadPureId;
    }

    public void setScorPltHeaderByFkScorPltHeaderThreadPureId(ScorPltHeaderEntity scorPltHeaderByFkScorPltHeaderThreadPureId) {
        this.scorPltHeaderByFkScorPltHeaderThreadPureId = scorPltHeaderByFkScorPltHeaderThreadPureId;
    }

    @Override
    public String toString() {
        return "AdjustmentThreadEntity{" +
                " adjustmentThreadId=" + adjustmentThreadId +
                '}';
    }
}
