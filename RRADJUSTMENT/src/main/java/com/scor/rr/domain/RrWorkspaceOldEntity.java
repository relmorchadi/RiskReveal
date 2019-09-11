package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RR-Workspace-old", schema = "dbo", catalog = "RiskReveal")
public class RrWorkspaceOldEntity {
    private String id;
    private String audit;
    private String cedantName;
    private String contractId;
    private String workspaceContextCode;
    private String workspaceContextFlag;
    private String workspaceName;
    private Integer workspaceUwYear;

    @Id
    @Basic
    @Column(name = "id", nullable = false, length = 11)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "audit", length = 2147483647)
    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }

    @Basic
    @Column(name = "cedantName", length = 2147483647)
    public String getCedantName() {
        return cedantName;
    }

    public void setCedantName(String cedantName) {
        this.cedantName = cedantName;
    }

    @Basic
    @Column(name = "contractId", length = 2147483647)
    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    @Basic
    @Column(name = "workspaceContextCode", length = 2147483647)
    public String getWorkspaceContextCode() {
        return workspaceContextCode;
    }

    public void setWorkspaceContextCode(String workspaceContextCode) {
        this.workspaceContextCode = workspaceContextCode;
    }

    @Basic
    @Column(name = "workspaceContextFlag", length = 2147483647)
    public String getWorkspaceContextFlag() {
        return workspaceContextFlag;
    }

    public void setWorkspaceContextFlag(String workspaceContextFlag) {
        this.workspaceContextFlag = workspaceContextFlag;
    }

    @Basic
    @Column(name = "workspaceName", length = 2147483647)
    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    @Basic
    @Column(name = "workspaceUwYear")
    public Integer getWorkspaceUwYear() {
        return workspaceUwYear;
    }

    public void setWorkspaceUwYear(Integer workspaceUwYear) {
        this.workspaceUwYear = workspaceUwYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrWorkspaceOldEntity that = (RrWorkspaceOldEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(audit, that.audit) &&
                Objects.equals(cedantName, that.cedantName) &&
                Objects.equals(contractId, that.contractId) &&
                Objects.equals(workspaceContextCode, that.workspaceContextCode) &&
                Objects.equals(workspaceContextFlag, that.workspaceContextFlag) &&
                Objects.equals(workspaceName, that.workspaceName) &&
                Objects.equals(workspaceUwYear, that.workspaceUwYear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, audit, cedantName, contractId, workspaceContextCode, workspaceContextFlag, workspaceName, workspaceUwYear);
    }
}
