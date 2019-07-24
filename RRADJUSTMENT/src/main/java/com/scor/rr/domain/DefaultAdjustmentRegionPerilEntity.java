package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "DefaultAdjustmentRegionPeril", schema = "dbo", catalog = "RiskReveal")
@IdClass(DefaultAdjustmentRegionPerilEntityPK.class)
public class DefaultAdjustmentRegionPerilEntity {
    private int idDefaultAdjustment;
    private int idRegionPeril;
    private String includedExcluded;
    private DefaultAdjustmentEntity defaultAdjustmentByIdDefaultAdjustment;
    private RegionPerilEntity regionPerilByIdRegionPeril;

    @Id
    @Column(name = "id_default_adjustment", nullable = false)
    public int getIdDefaultAdjustment() {
        return idDefaultAdjustment;
    }

    public void setIdDefaultAdjustment(int idDefaultAdjustment) {
        this.idDefaultAdjustment = idDefaultAdjustment;
    }

    @Id
    @Column(name = "id_region_peril", nullable = false)
    public int getIdRegionPeril() {
        return idRegionPeril;
    }

    public void setIdRegionPeril(int idRegionPeril) {
        this.idRegionPeril = idRegionPeril;
    }

    @Basic
    @Column(name = "included_excluded", nullable = true, length = 1)
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
        return idDefaultAdjustment == that.idDefaultAdjustment &&
                idRegionPeril == that.idRegionPeril &&
                Objects.equals(includedExcluded, that.includedExcluded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDefaultAdjustment, idRegionPeril, includedExcluded);
    }

    @ManyToOne
    @JoinColumn(name = "id_default_adjustment", referencedColumnName = "id_default_adjustment", nullable = false,insertable = false, updatable = false)
    public DefaultAdjustmentEntity getDefaultAdjustmentByIdDefaultAdjustment() {
        return defaultAdjustmentByIdDefaultAdjustment;
    }

    public void setDefaultAdjustmentByIdDefaultAdjustment(DefaultAdjustmentEntity defaultAdjustmentByIdDefaultAdjustment) {
        this.defaultAdjustmentByIdDefaultAdjustment = defaultAdjustmentByIdDefaultAdjustment;
    }

    @ManyToOne
    @JoinColumn(name = "id_region_peril", referencedColumnName = "regionPerilId", nullable = false,insertable = false, updatable = false)
    public RegionPerilEntity getRegionPerilByIdRegionPeril() {
        return regionPerilByIdRegionPeril;
    }

    public void setRegionPerilByIdRegionPeril(RegionPerilEntity regionPerilByIdRegionPeril) {
        this.regionPerilByIdRegionPeril = regionPerilByIdRegionPeril;
    }
}
