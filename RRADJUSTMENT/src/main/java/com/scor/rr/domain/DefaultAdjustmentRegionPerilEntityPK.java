package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class DefaultAdjustmentRegionPerilEntityPK implements Serializable {
    private int idDefaultAdjustment;
    private int idRegionPeril;

    @Column(name = "id_default_adjustment", nullable = false)
    @Id
    public int getIdDefaultAdjustment() {
        return idDefaultAdjustment;
    }

    public void setIdDefaultAdjustment(int idDefaultAdjustment) {
        this.idDefaultAdjustment = idDefaultAdjustment;
    }

    @Column(name = "id_region_peril", nullable = false)
    @Id
    public int getIdRegionPeril() {
        return idRegionPeril;
    }

    public void setIdRegionPeril(int idRegionPeril) {
        this.idRegionPeril = idRegionPeril;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentRegionPerilEntityPK that = (DefaultAdjustmentRegionPerilEntityPK) o;
        return idDefaultAdjustment == that.idDefaultAdjustment &&
                idRegionPeril == that.idRegionPeril;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idDefaultAdjustment, idRegionPeril);
    }
}
