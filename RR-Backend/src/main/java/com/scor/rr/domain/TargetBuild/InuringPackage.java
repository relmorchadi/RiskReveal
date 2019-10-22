package com.scor.rr.domain.TargetBuild;

import com.scor.rr.domain.InuringPackageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "InuringPackage", schema = "dr")
@Data
@AllArgsConstructor
public class InuringPackage {

    private int inuringPackageId;
    private int entity;
    private String packageName;
    private String packageDescription;
    private int workspaceId;
    private int createdBy;
    private Date createdOn;
    private int lastModifiedBy;
    private Date lastModifiedOn;
    private boolean locked;
    private InuringPackageStatus inuringPackageStatus;

    public InuringPackage() {
    }

    public InuringPackage(String packageName, String packageDescription, int workspaceId, int createdBy) {
        this.entity = 1;
        this.packageName = packageName;
        this.packageDescription = packageDescription;
        this.workspaceId = workspaceId;
        this.createdBy = createdBy;
        this.createdOn = new Date();
        this.lastModifiedBy = createdBy;
        this.lastModifiedOn = this.createdOn;
        this.locked = false;
        this.inuringPackageStatus = InuringPackageStatus.Invalid;
    }

    @Id
    @Column(name = "InuringPackageId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(int inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Column(name = "Entity")
    public int getEntity() {
        return entity;
    }

    public void setEntity(int entity) {
        this.entity = entity;
    }

    @Column(name = "PackageName", nullable = false)
    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Column(name = "PackageDescription", nullable = false)
    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    @Column(name = "WorkspaceId", nullable = false)
    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Column(name = "CreatedBy", nullable = false)
    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "CreatedOn", nullable = false)
    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Column(name = "LastModifiedBy", nullable = false)
    public int getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(int lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Column(name = "LastModifiedOn", nullable = false)
    public Date getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(Date lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }

    @Column(name = "Locked", nullable = false)
    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Column(name = "InuringPackageStatus", nullable = false)
    public InuringPackageStatus getInuringPackageStatus() {
        return inuringPackageStatus;
    }

    public void setInuringPackageStatus(InuringPackageStatus inuringPackageStatus) {
        this.inuringPackageStatus = inuringPackageStatus;
    }
}
