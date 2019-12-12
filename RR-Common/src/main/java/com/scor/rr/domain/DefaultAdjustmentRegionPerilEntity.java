package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentRegionPeril")
@IdClass(DefaultAdjustmentRegionPerilEntityPK.class)
public class DefaultAdjustmentRegionPerilEntity {
    private int fkDefaultAdjustmentId;
    private long fkRegionPerilId;
    private String includedExcluded;
    private DefaultAdjustmentEntity defaultAdjustment;
    private RegionPerilEntity regionPeril;

    @Id
    @Column(name = "DefaultAdjustmentId", nullable = false)
    public int getFkDefaultAdjustmentId() {
        return fkDefaultAdjustmentId;
    }

    public void setFkDefaultAdjustmentId(int fkDefaultAdjustmentId) {
        this.fkDefaultAdjustmentId = fkDefaultAdjustmentId;
    }

    @Id
    @Column(name = "RegionPerilId", nullable = false)
    public long getFkRegionPerilId() {
        return fkRegionPerilId;
    }

    public void setFkRegionPerilId(long fkRegionPerilId) {
        this.fkRegionPerilId = fkRegionPerilId;
    }

    @Basic
    @Column(name = "IncludedExcluded", nullable = true, length = 1)
    public String getIncludedExcluded() {
        return includedExcluded;
    }

    public void setIncludedExcluded(String includedExcluded) {
        this.includedExcluded = includedExcluded;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentRegionPerilEntity that = (DefaultAdjustmentRegionPerilEntity) o;
        return fkDefaultAdjustmentId == that.fkDefaultAdjustmentId &&
                fkRegionPerilId == that.fkRegionPerilId &&
                Objects.equals(includedExcluded, that.includedExcluded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkDefaultAdjustmentId, fkRegionPerilId, includedExcluded);
    }

    @ManyToOne
    @JoinColumn(name = "DefaultAdjustmentId", referencedColumnName = "DefaultAdjustmentId", nullable = false,insertable = false,updatable = false)
    public DefaultAdjustmentEntity getDefaultAdjustment() {
        return defaultAdjustment;
    }

    public void setDefaultAdjustment(DefaultAdjustmentEntity defaultAdjustment) {
        this.defaultAdjustment = defaultAdjustment;
    }

    @ManyToOne
    @JoinColumn(name = "RegionPerilId", referencedColumnName = "regionPerilId", nullable = false,insertable = false,updatable = false)
    public RegionPerilEntity getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(RegionPerilEntity regionPeril) {
        this.regionPeril = regionPeril;
    }
}
