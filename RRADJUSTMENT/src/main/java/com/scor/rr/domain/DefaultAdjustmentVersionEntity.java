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
    private int idDefaultAdjustmentVersion;
    private Boolean isactive;
    private Timestamp deletedDt;
    private String deletedBy;
    private String notes;
    private DefaultAdjustmentEntity defaultAdjustment;

    @Basic
    @Column(name = "version_sequence", nullable = true)
    public Integer getVersionSequence() {
        return versionSequence;
    }

    public void setVersionSequence(Integer versionSequence) {
        this.versionSequence = versionSequence;
    }

    @Basic
    @Column(name = "effective_to", nullable = true)
    public Timestamp getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Timestamp effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    @Basic
    @Column(name = "effective_from", nullable = true)
    public Timestamp getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Timestamp effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    @Id
    @Column(name = "id_default_adjustment_version", nullable = false)
    public int getIdDefaultAdjustmentVersion() {
        return idDefaultAdjustmentVersion;
    }

    public void setIdDefaultAdjustmentVersion(int idDefaultAdjustmentVersion) {
        this.idDefaultAdjustmentVersion = idDefaultAdjustmentVersion;
    }

    @Basic
    @Column(name = "isactive", nullable = true)
    public Boolean getIsactive() {
        return isactive;
    }

    public void setIsactive(Boolean isactive) {
        this.isactive = isactive;
    }

    @Basic
    @Column(name = "deleted_dt", nullable = true)
    public Timestamp getDeletedDt() {
        return deletedDt;
    }

    public void setDeletedDt(Timestamp deletedDt) {
        this.deletedDt = deletedDt;
    }

    @Basic
    @Column(name = "deleted_by", nullable = true, length = 50)
    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Basic
    @Column(name = "notes", nullable = true, length = 200)
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
        return idDefaultAdjustmentVersion == that.idDefaultAdjustmentVersion &&
                Objects.equals(versionSequence, that.versionSequence) &&
                Objects.equals(effectiveTo, that.effectiveTo) &&
                Objects.equals(effectiveFrom, that.effectiveFrom) &&
                Objects.equals(isactive, that.isactive) &&
                Objects.equals(deletedDt, that.deletedDt) &&
                Objects.equals(deletedBy, that.deletedBy) &&
                Objects.equals(notes, that.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versionSequence, effectiveTo, effectiveFrom, idDefaultAdjustmentVersion, isactive, deletedDt, deletedBy, notes);
    }

    @ManyToOne
    @JoinColumn(name = "id_default_adjustment", referencedColumnName = "id_default_adjustment")
    public DefaultAdjustmentEntity getDefaultAdjustment() {
        return defaultAdjustment;
    }

    public void setDefaultAdjustment(DefaultAdjustmentEntity defaultAdjustment) {
        this.defaultAdjustment = defaultAdjustment;
    }
}
