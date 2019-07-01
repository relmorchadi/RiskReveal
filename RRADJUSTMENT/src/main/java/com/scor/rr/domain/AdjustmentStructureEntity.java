package com.scor.rr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentStructure", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentStructureEntity {
    private int adjustmentStuctureId;

    @Id
    @Column(name = "adjustmentStuctureId", nullable = false)
    public int getAdjustmentStuctureId() {
        return adjustmentStuctureId;
    }

    public void setAdjustmentStuctureId(int adjustmentStuctureId) {
        this.adjustmentStuctureId = adjustmentStuctureId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentStructureEntity that = (AdjustmentStructureEntity) o;
        return adjustmentStuctureId == that.adjustmentStuctureId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentStuctureId);
    }
}
