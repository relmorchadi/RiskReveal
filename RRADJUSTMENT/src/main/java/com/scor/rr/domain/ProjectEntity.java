package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Project", schema = "dbo", catalog = "RiskReveal")
public class ProjectEntity {
    private int projectId;
    private String name;
    private String description;
    private Boolean masterFlag;
    private Boolean linkFlag;
    private Boolean publishFlag;
    private Boolean clonedFlag;
    private Boolean postInuredFlag;
    private Boolean mgaFlag;
    private String assignedTo;
    private Timestamp creationDate;
    private Timestamp receptionDate;
    private Timestamp dueDate;
    private String createdBy;
    private Integer fkLinkedSourceProjectId;
    private Integer fkCloneSourceProjectId;
    private Boolean deleted;
    private Timestamp deletedOn;
    private String deletedDue;
    private String deletedBy;
    private WorkspaceEntity workspaceByFkWorkspaceId;

    @Id
    @Column(name = "projectId", nullable = false)
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "masterFlag", nullable = true)
    public Boolean getMasterFlag() {
        return masterFlag;
    }

    public void setMasterFlag(Boolean masterFlag) {
        this.masterFlag = masterFlag;
    }

    @Basic
    @Column(name = "linkFlag", nullable = true)
    public Boolean getLinkFlag() {
        return linkFlag;
    }

    public void setLinkFlag(Boolean linkFlag) {
        this.linkFlag = linkFlag;
    }

    @Basic
    @Column(name = "publishFlag", nullable = true)
    public Boolean getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(Boolean publishFlag) {
        this.publishFlag = publishFlag;
    }

    @Basic
    @Column(name = "clonedFlag", nullable = true)
    public Boolean getClonedFlag() {
        return clonedFlag;
    }

    public void setClonedFlag(Boolean clonedFlag) {
        this.clonedFlag = clonedFlag;
    }

    @Basic
    @Column(name = "postInuredFlag", nullable = true)
    public Boolean getPostInuredFlag() {
        return postInuredFlag;
    }

    public void setPostInuredFlag(Boolean postInuredFlag) {
        this.postInuredFlag = postInuredFlag;
    }

    @Basic
    @Column(name = "mgaFlag", nullable = true)
    public Boolean getMgaFlag() {
        return mgaFlag;
    }

    public void setMgaFlag(Boolean mgaFlag) {
        this.mgaFlag = mgaFlag;
    }

    @Basic
    @Column(name = "assignedTo", nullable = true, length = 255)
    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Basic
    @Column(name = "creationDate", nullable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "receptionDate", nullable = true)
    public Timestamp getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Timestamp receptionDate) {
        this.receptionDate = receptionDate;
    }

    @Basic
    @Column(name = "dueDate", nullable = true)
    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    @Basic
    @Column(name = "createdBy", nullable = true, length = 255)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "FKLinkedSourceProjectId", nullable = true)
    public Integer getFkLinkedSourceProjectId() {
        return fkLinkedSourceProjectId;
    }

    public void setFkLinkedSourceProjectId(Integer fkLinkedSourceProjectId) {
        this.fkLinkedSourceProjectId = fkLinkedSourceProjectId;
    }

    @Basic
    @Column(name = "FKCloneSourceProjectId", nullable = true)
    public Integer getFkCloneSourceProjectId() {
        return fkCloneSourceProjectId;
    }

    public void setFkCloneSourceProjectId(Integer fkCloneSourceProjectId) {
        this.fkCloneSourceProjectId = fkCloneSourceProjectId;
    }

    @Basic
    @Column(name = "deleted", nullable = true)
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Basic
    @Column(name = "deletedOn", nullable = true)
    public Timestamp getDeletedOn() {
        return deletedOn;
    }

    public void setDeletedOn(Timestamp deletedOn) {
        this.deletedOn = deletedOn;
    }

    @Basic
    @Column(name = "deletedDue", nullable = true, length = 255)
    public String getDeletedDue() {
        return deletedDue;
    }

    public void setDeletedDue(String deletedDue) {
        this.deletedDue = deletedDue;
    }

    @Basic
    @Column(name = "deletedBy", nullable = true, length = 255)
    public String getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(String deletedBy) {
        this.deletedBy = deletedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectEntity that = (ProjectEntity) o;
        return projectId == that.projectId &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(masterFlag, that.masterFlag) &&
                Objects.equals(linkFlag, that.linkFlag) &&
                Objects.equals(publishFlag, that.publishFlag) &&
                Objects.equals(clonedFlag, that.clonedFlag) &&
                Objects.equals(postInuredFlag, that.postInuredFlag) &&
                Objects.equals(mgaFlag, that.mgaFlag) &&
                Objects.equals(assignedTo, that.assignedTo) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(receptionDate, that.receptionDate) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(fkLinkedSourceProjectId, that.fkLinkedSourceProjectId) &&
                Objects.equals(fkCloneSourceProjectId, that.fkCloneSourceProjectId) &&
                Objects.equals(deleted, that.deleted) &&
                Objects.equals(deletedOn, that.deletedOn) &&
                Objects.equals(deletedDue, that.deletedDue) &&
                Objects.equals(deletedBy, that.deletedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, name, description, masterFlag, linkFlag, publishFlag, clonedFlag, postInuredFlag, mgaFlag, assignedTo, creationDate, receptionDate, dueDate, createdBy, fkLinkedSourceProjectId, fkCloneSourceProjectId, deleted, deletedOn, deletedDue, deletedBy);
    }

    @ManyToOne
    @JoinColumn(name = "FKWorkspaceId", referencedColumnName = "workspaceId")
    public WorkspaceEntity getWorkspaceByFkWorkspaceId() {
        return workspaceByFkWorkspaceId;
    }

    public void setWorkspaceByFkWorkspaceId(WorkspaceEntity workspaceByFkWorkspaceId) {
        this.workspaceByFkWorkspaceId = workspaceByFkWorkspaceId;
    }
}
