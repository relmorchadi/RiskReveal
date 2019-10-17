package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringObjectNode", schema = "dbo", catalog = "RiskReveal")
public class InuringObjectNodeEntity {
    private int inuringObjectNodeId;
    private Integer inuringPackageId;

    @Id
    @Column(name = "InuringObjectNodeId", nullable = false, precision = 0)
    public int getInuringObjectNodeId() {
        return inuringObjectNodeId;
    }

    public void setInuringObjectNodeId(int inuringObjectNodeId) {
        this.inuringObjectNodeId = inuringObjectNodeId;
    }

    @Basic
    @Column(name = "FKInuringPackageId", nullable = true, precision = 0)
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
        InuringObjectNodeEntity that = (InuringObjectNodeEntity) o;
        return inuringObjectNodeId == that.inuringObjectNodeId &&
                Objects.equals(inuringPackageId, that.inuringPackageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringObjectNodeId, inuringPackageId);
    }
}
