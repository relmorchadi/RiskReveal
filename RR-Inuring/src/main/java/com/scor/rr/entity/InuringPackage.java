package com.scor.rr.entity;

import com.scor.rr.enums.InuringPackageStatus;


import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Created by u004602 on 11/09/2019.
 */
@Entity
@Table(name = "InuringPackage", schema = "dr", catalog = "RiskReveal")
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
//        this.createdOn = new Date();
        this.lastModifiedBy = createdBy;
//        this.lastModifiedOn = this.createdOn;
        this.locked = false;
        this.inuringPackageStatus = InuringPackageStatus.Invalid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InuringPackageId")
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InuringPackage that = (InuringPackage) o;
        return inuringPackageId == that.inuringPackageId &&
                entity == that.entity &&
                workspaceId == that.workspaceId &&
                createdBy == that.createdBy &&
                lastModifiedBy == that.lastModifiedBy &&
                locked == that.locked &&
                Objects.equals(packageName, that.packageName) &&
                Objects.equals(packageDescription, that.packageDescription) &&
                Objects.equals(createdOn, that.createdOn) &&
                Objects.equals(lastModifiedOn, that.lastModifiedOn) &&
                inuringPackageStatus == that.inuringPackageStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(inuringPackageId, entity, packageName, packageDescription, workspaceId, createdBy, createdOn, lastModifiedBy, lastModifiedOn, locked, inuringPackageStatus);
    }
}
