package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentVersion", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentVersionEntity {
    private Integer versionSequence;
    private Timestamp effectiveTo;
    private Timestamp effectiveFrom;
    private int defaultAdjustmentVersionId;
    private Boolean isActive;
    private Timestamp deletedDt;
    private String deletedBy;
    private String notes;
    private DefaultAdjustmentEntity defaultAdjustment;

    @Basic
    @Column(name = "VersionSequence", nullable = true)
    public Integer getVersionSequence() {
        return versionSequence;
    }

    public void setVersionSequence(Integer versionSequence) {
        this.versionSequence = versionSequence;
    }

    @Basic
    @Column(name = "EffectiveTo", nullable = true)
    public Timestamp getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Timestamp effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    @Basic
    @Column(name = "EffectiveFrom", nullable = true)
    public Timestamp getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Timestamp effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    @Id
    @Column(name = "DefaultAdjustmentVersionId", nullable = false)
    public int getDefaultAdjustmentVersionId() {
        return defaultAdjustmentVersionId;
    }

    public void setDefaultAdjustmentVersionId(int defaultAdjustmentVersionId) {
        this.defaultAdjustmentVersionId = defaultAdjustmentVersionId;
    }

    @Basic
    @Column(name = "IsActive", nullable = true)
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "DeletedDt", nullable = true)
    public Timestamp getDeletedDt() {
        return deletedDt;
    }

    public void setDeletedDt(Timestamp deletedDt) {
        this.deletedDt = deletedDt;
    }

    @Basic
    @Column(name = "DeletedBy", nullable = true, length = 50)
    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Basic
    @Column(name = "Notes", nullable = true, length = 200)
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentVersionEntity that = (DefaultAdjustmentVersionEntity) o;
        return defaultAdjustmentVersionId == that.defaultAdjustmentVersionId &&
                Objects.equals(versionSequence, that.versionSequence) &&
                Objects.equals(effectiveTo, that.effectiveTo) &&
                Objects.equals(effectiveFrom, that.effectiveFrom) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(deletedDt, that.deletedDt) &&
                Objects.equals(deletedBy, that.deletedBy) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionSequence, effectiveTo, effectiveFrom, defaultAdjustmentVersionId, isActive, deletedDt, deletedBy, notes);
    }

    @ManyToOne
    @JoinColumn(name = "DefaultAdjustmentId", referencedColumnName = "DefaultAdjustmentId",insertable = false,updatable = false)
    public DefaultAdjustmentEntity getDefaultAdjustment() {
        return defaultAdjustment;
    }

    public void setDefaultAdjustment(DefaultAdjustmentEntity defaultAdjustment) {
        this.defaultAdjustment = defaultAdjustment;
    }
}
