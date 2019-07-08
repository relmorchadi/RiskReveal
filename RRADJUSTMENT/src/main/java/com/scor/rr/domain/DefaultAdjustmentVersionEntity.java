package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentVersion", schema = "dbo", catalog = "RiskReveal")
public class DefaultAdjustmentVersionEntity {
    private int id;
    private Integer versionSequence;
    private Date effectiveTo;
    private Date effectiveFrom;
    private DefaultAdjustmentEntity defaultAdjustmentByFkDefaultAdjustment;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "version_sequence", nullable = true)
    public Integer getVersionSequence() {
        return versionSequence;
    }

    public void setVersionSequence(Integer versionSequence) {
        this.versionSequence = versionSequence;
    }

    @Basic
    @Column(name = "effective_to", nullable = true, length = 200)
    public Date getEffectiveTo() {
        return effectiveTo;
    }

    public void setEffectiveTo(Date effectiveTo) {
        this.effectiveTo = effectiveTo;
    }

    @Basic
    @Column(name = "effective_from", nullable = true, length = 200)
    public Date getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Date effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentVersionEntity that = (DefaultAdjustmentVersionEntity) o;
        return id == that.id &&
                Objects.equals(versionSequence, that.versionSequence) &&
                Objects.equals(effectiveTo, that.effectiveTo) &&
                Objects.equals(effectiveFrom, that.effectiveFrom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, versionSequence, effectiveTo, effectiveFrom);
    }

    @ManyToOne
    @JoinColumn(name = "fk_default_adjustment", referencedColumnName = "id")
    public DefaultAdjustmentEntity getDefaultAdjustmentByFkDefaultAdjustment() {
        return defaultAdjustmentByFkDefaultAdjustment;
    }

    public void setDefaultAdjustmentByFkDefaultAdjustment(DefaultAdjustmentEntity defaultAdjustmentByFkDefaultAdjustment) {
        this.defaultAdjustmentByFkDefaultAdjustment = defaultAdjustmentByFkDefaultAdjustment;
    }
}
