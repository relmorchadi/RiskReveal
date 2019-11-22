package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Objects;

@Entity
@Table(name = "Workspace", schema = "dbo", catalog = "RiskReveal")
public class WorkspaceEntity {
    private String workspaceId;
    private String workspaceContextCode;
    private Integer workspaceUwYear;
    private String workspaceName;
    private String cedantName;

    @Id
    @Column(name = "workspaceId", nullable = false, length = 255)
    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
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
    @Column(name = "workspaceUwYear", nullable = true)
    public Integer getWorkspaceUwYear() {
        return workspaceUwYear;
    }

    public void setWorkspaceUwYear(Integer workspaceUwYear) {
        this.workspaceUwYear = workspaceUwYear;
    }

    @Basic
    @Column(name = "workspaceName", nullable = true, length = 255)
    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    @Basic
    @Column(name = "cedantName", nullable = true, length = 255)
    public String getCedantName() {
        return cedantName;
    }

    public void setCedantName(String cedantName) {
        this.cedantName = cedantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkspaceEntity that = (WorkspaceEntity) o;
        return Objects.equals(workspaceId, that.workspaceId) &&
                Objects.equals(workspaceContextCode, that.workspaceContextCode) &&
                Objects.equals(workspaceUwYear, that.workspaceUwYear) &&
                Objects.equals(workspaceName, that.workspaceName) &&
                Objects.equals(cedantName, that.cedantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, workspaceContextCode, workspaceUwYear, workspaceName, cedantName);
    }
}
