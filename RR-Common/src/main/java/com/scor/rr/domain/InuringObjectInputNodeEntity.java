package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringObjectInputNode", schema = "dbo", catalog = "RiskReveal")
public class InuringObjectInputNodeEntity {
    private int inuringObjectInputNodeId;
    private Integer inuringPackageId;

    @Id
    @Column(name = "InuringObjectInputNodeId", nullable = false, precision = 0)
    public int getInuringObjectInputNodeId() {
        return inuringObjectInputNodeId;
    }

    public void setInuringObjectInputNodeId(int inuringObjectInputNodeId) {
        this.inuringObjectInputNodeId = inuringObjectInputNodeId;
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
        InuringObjectInputNodeEntity that = (InuringObjectInputNodeEntity) o;
        return inuringObjectInputNodeId == that.inuringObjectInputNodeId &&
                Objects.equals(inuringPackageId, that.inuringPackageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringObjectInputNodeId, inuringPackageId);
    }
}
