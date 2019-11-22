package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "RR-Workspace", schema = "dbo", catalog = "RiskReveal")
public class RrWorkspaceEntity {
    private String id;
    private String contractId;
    private String workspaceContextCode;
    private String workspaceContextFlag;
    private Double workspaceUwYear;

    @Id
    @Basic
    @Column(name = "id", nullable = true, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "contractId", nullable = true, length = 255)
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "workspaceContextCode", nullable = true, length = 255)
    public String getWorkspaceContextCode() {
        return workspaceContextCode;
    }

    public void setWorkspaceContextCode(String workspaceContextCode) {
        this.workspaceContextCode = workspaceContextCode;
    }

    @Basic
    @Column(name = "workspaceContextFlag", nullable = true, length = 255)
    public String getWorkspaceContextFlag() {
        return workspaceContextFlag;
    }

    public void setWorkspaceContextFlag(String workspaceContextFlag) {
        this.workspaceContextFlag = workspaceContextFlag;
    }

    @Basic
    @Column(name = "workspaceUwYear", nullable = true, precision = 0)
    public Double getWorkspaceUwYear() {
        return workspaceUwYear;
    }

    public void setWorkspaceUwYear(Double workspaceUwYear) {
        this.workspaceUwYear = workspaceUwYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrWorkspaceEntity that = (RrWorkspaceEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(contractId, that.contractId) &&
                Objects.equals(workspaceContextCode, that.workspaceContextCode) &&
                Objects.equals(workspaceContextFlag, that.workspaceContextFlag) &&
                Objects.equals(workspaceUwYear, that.workspaceUwYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contractId, workspaceContextCode, workspaceContextFlag, workspaceUwYear);
    }
}
