package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentType", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentTypeEntity {
    private int adjustmentTypeId;
    private String type;
    private String description;

    @Id
    @Column(name = "AdjustmentTypeId", nullable = false)
    public int getAdjustmentTypeId() {
        return adjustmentTypeId;
    }

    public void setAdjustmentTypeId(int adjustmentTypeId) {
        this.adjustmentTypeId = adjustmentTypeId;
    }

    @Basic
    @Column(name = "Type", length = 200)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Basic
    @Column(name = "Description", length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentTypeEntity that = (AdjustmentTypeEntity) o;
        return adjustmentTypeId == that.adjustmentTypeId &&
                Objects.equals(type, that.type) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentTypeId, type, description);
    }
}
