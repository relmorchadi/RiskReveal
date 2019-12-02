package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class DefaultAdjustmentRegionPerilEntityPK implements Serializable {
    private int fkDefaultAdjustmentId;
    private long fkRegionPerilId;

    @Column(name = "DefaultAdjustmentId", nullable = false)
    @Id
    public int getFkDefaultAdjustmentId() {
        return fkDefaultAdjustmentId;
    }

    public void setFkDefaultAdjustmentId(int fkDefaultAdjustmentId) {
        this.fkDefaultAdjustmentId = fkDefaultAdjustmentId;
    }

    @Column(name = "RegionPerilId", nullable = false)
    @Id
    public long getFkRegionPerilId() {
        return fkRegionPerilId;
    }

    public void setFkRegionPerilId(int fkRegionPerilId) {
        this.fkRegionPerilId = fkRegionPerilId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DefaultAdjustmentRegionPerilEntityPK that = (DefaultAdjustmentRegionPerilEntityPK) o;
        return fkDefaultAdjustmentId == that.fkDefaultAdjustmentId &&
                fkRegionPerilId == that.fkRegionPerilId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fkDefaultAdjustmentId, fkRegionPerilId);
    }
}
