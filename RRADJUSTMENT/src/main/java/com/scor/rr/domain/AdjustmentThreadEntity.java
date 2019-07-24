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
    private int idAdjustmentThread;
    private ScorPltHeaderEntity scorPltHeaderByIdPurePlt;
    private ScorPltHeaderEntity scorPltHeaderByIdThreadPlt;

    @Basic
    @Column(name = "thread_type", nullable = true, length = 255)
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
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
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
    @Column(name = "last_modified_by", nullable = true, length = 100)
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
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

    @Id
    @Column(name = "id_adjustment_thread", nullable = false)
    public int getIdAdjustmentThread() {
        return idAdjustmentThread;
    }

    public void setIdAdjustmentThread(int idAdjustmentThread) {
        this.idAdjustmentThread = idAdjustmentThread;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentThreadEntity that = (AdjustmentThreadEntity) o;
        return idAdjustmentThread == that.idAdjustmentThread &&
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
        return Objects.hash(threadType, locked, createdBy, createdOn, lastModifiedBy, lastModifiedOn, lastGeneratedOn, generatedOn, idAdjustmentThread);
    }

    @ManyToOne
    @JoinColumn(name = "id_pure_plt", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByIdPurePlt() {
        return scorPltHeaderByIdPurePlt;
    }

    public void setScorPltHeaderByIdPurePlt(ScorPltHeaderEntity scorPltHeaderByIdPurePlt) {
        this.scorPltHeaderByIdPurePlt = scorPltHeaderByIdPurePlt;
    }

    @ManyToOne
    @JoinColumn(name = "id_thread_plt", referencedColumnName = "scorPLTHeaderId")
    public ScorPltHeaderEntity getScorPltHeaderByIdThreadPlt() {
        return scorPltHeaderByIdThreadPlt;
    }

    public void setScorPltHeaderByIdThreadPlt(ScorPltHeaderEntity scorPltHeaderByIdThreadPlt) {
        this.scorPltHeaderByIdThreadPlt = scorPltHeaderByIdThreadPlt;
    }
}
