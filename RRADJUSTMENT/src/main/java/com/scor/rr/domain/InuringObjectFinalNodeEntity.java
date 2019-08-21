package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringObjectFinalNode", schema = "dbo", catalog = "RiskReveal")
public class InuringObjectFinalNodeEntity {
    private int inuringObjectFinalNodeId;
    private Integer inuringPackageId;

    @Id
    @Column(name = "InuringObjectFinalNodeId", nullable = false, precision = 0)
    public int getInuringObjectFinalNodeId() {
        return inuringObjectFinalNodeId;
    }

    public void setInuringObjectFinalNodeId(int inuringObjectFinalNodeId) {
        this.inuringObjectFinalNodeId = inuringObjectFinalNodeId;
    }

    @Basic
    @Column(name = "FKInuringPackage", nullable = true, precision = 0)
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringObjectFinalNodeEntity that = (InuringObjectFinalNodeEntity) o;
        return inuringObjectFinalNodeId == that.inuringObjectFinalNodeId &&
                Objects.equals(inuringPackageId, that.inuringPackageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringObjectFinalNodeId, inuringPackageId);
    }
}
