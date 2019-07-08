package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AdjustmentStructure", schema = "dbo", catalog = "RiskReveal")
public class AdjustmentStructureEntity {
    private int adjustmentStuctureId;
    private Integer projectId;

    @Id
    @Column(name = "adjustmentStuctureId", nullable = false)
    public int getAdjustmentStuctureId() {
        return adjustmentStuctureId;
    }

    public void setAdjustmentStuctureId(int adjustmentStuctureId) {
        this.adjustmentStuctureId = adjustmentStuctureId;
    }

    @Basic
    @Column(name = "projectId", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjustmentStructureEntity that = (AdjustmentStructureEntity) o;
        return adjustmentStuctureId == that.adjustmentStuctureId &&
                Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjustmentStuctureId, projectId);
    }
}
