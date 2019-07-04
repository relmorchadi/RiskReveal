package com.scor.rr.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@IdClass(ProjectViewId.class)
@Table(name = "PROJECTS_VIEW", schema = "poc")
public class ProjectView implements Serializable {

    @Id
    @Column(name = "workspaceId")
    private String workspaceId;
    @Id
    @Column(name = "uwy")
    private Integer uwy;

    @Id
    @Column(name = "projectId")
    private String projectId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "assignedTo")
    private String assignedTo;
    @Column(name = "createdBy")
    private String createdBy;
    @Column(name = "creationDate")
    private Timestamp creationDate;
    @Column(name = "dueDate")
    private Timestamp dueDate;
    @Column(name = "isLocking")
    private Boolean isLocking;
    @Column(name = "linkFlag")
    private Boolean linkFlag;
    @Column(name = "postInuredFlag")
    private Boolean postInuredFlag;
    @Column(name = "publishFlag")
    private Boolean publishFlag;
    @Column(name = "pltSum")
    private Integer pltSum;
    @Column(name = "pltThreadSum")
    private Integer pltThreadSum;
    @Column(name = "regionPerilSum")
    private Integer regionPerilSum;
    @Column(name = "xactSum")
    private Integer xactSum;

    public ProjectView() {
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }


    public Integer getUwy() {
        return uwy;
    }

    public void setUwy(Integer uwy) {
        this.uwy = uwy;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String id) {
        this.projectId = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }


    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }


    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }


    public Boolean getLocking() {
        return isLocking;
    }

    public void setLocking(Boolean locking) {
        isLocking = locking;
    }


    public Boolean getLinkFlag() {
        return linkFlag;
    }

    public void setLinkFlag(Boolean linkFlag) {
        this.linkFlag = linkFlag;
    }


    public Boolean getPostInuredFlag() {
        return postInuredFlag;
    }

    public void setPostInuredFlag(Boolean postInuredFlag) {
        this.postInuredFlag = postInuredFlag;
    }


    public Boolean getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(Boolean publishFlag) {
        this.publishFlag = publishFlag;
    }


    public Integer getPltSum() {
        return pltSum;
    }

    public void setPltSum(Integer pltSum) {
        this.pltSum = pltSum;
    }


    public Integer getPltThreadSum() {
        return pltThreadSum;
    }

    public void setPltThreadSum(Integer pltThreadSum) {
        this.pltThreadSum = pltThreadSum;
    }


    public Integer getRegionPerilSum() {
        return regionPerilSum;
    }

    public void setRegionPerilSum(Integer regionPerilSum) {
        this.regionPerilSum = regionPerilSum;
    }


    public Integer getXactSum() {
        return xactSum;
    }

    public void setXactSum(Integer xactSum) {
        this.xactSum = xactSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectView that = (ProjectView) o;
        return Objects.equals(workspaceId, that.workspaceId) &&
                Objects.equals(uwy, that.uwy) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(assignedTo, that.assignedTo) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(isLocking, that.isLocking) &&
                Objects.equals(linkFlag, that.linkFlag) &&
                Objects.equals(postInuredFlag, that.postInuredFlag) &&
                Objects.equals(publishFlag, that.publishFlag) &&
                Objects.equals(pltSum, that.pltSum) &&
                Objects.equals(pltThreadSum, that.pltThreadSum) &&
                Objects.equals(regionPerilSum, that.regionPerilSum) &&
                Objects.equals(xactSum, that.xactSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, uwy, projectId, name, description, assignedTo, createdBy, creationDate, dueDate, isLocking, linkFlag, postInuredFlag, publishFlag, pltSum, pltThreadSum, regionPerilSum, xactSum);
    }
}
