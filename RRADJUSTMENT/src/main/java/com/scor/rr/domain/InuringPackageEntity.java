package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "InuringPackage", schema = "dbo", catalog = "RiskReveal")
public class InuringPackageEntity {
    private int inuringPackageId;
    private String workspaceId;
    private Integer lastExportUserId;
    private Integer createdUserId;
    private Integer modifiedUserId;
    private Integer lastImportUserId;

    @Id
    @Column(name = "InuringPackage_Id", nullable = false, precision = 0)
    public int getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(int inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Basic
    @Column(name = "Workspace_Id", length = 255,insertable = false ,updatable = false)
    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Basic
    @Column(name = "LastExport_User_Id", precision = 0)
    public Integer getLastExportUserId() {
        return lastExportUserId;
    }

    public void setLastExportUserId(Integer lastExportUserId) {
        this.lastExportUserId = lastExportUserId;
    }

    @Basic
    @Column(name = "Created_User_Id", precision = 0)
    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    @Basic
    @Column(name = "Modified_User_Id", precision = 0)
    public Integer getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Integer modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    @Basic
    @Column(name = "LastImport_User_Id", precision = 0)
    public Integer getLastImportUserId() {
        return lastImportUserId;
    }

    public void setLastImportUserId(Integer lastImportUserId) {
        this.lastImportUserId = lastImportUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringPackageEntity that = (InuringPackageEntity) o;
        return inuringPackageId == that.inuringPackageId &&
                Objects.equals(workspaceId, that.workspaceId) &&
                Objects.equals(lastExportUserId, that.lastExportUserId) &&
                Objects.equals(createdUserId, that.createdUserId) &&
                Objects.equals(modifiedUserId, that.modifiedUserId) &&
                Objects.equals(lastImportUserId, that.lastImportUserId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringPackageId, workspaceId, lastExportUserId, createdUserId, modifiedUserId, lastImportUserId);
    }
}
