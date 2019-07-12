package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RR-project", schema = "dbo", catalog = "RiskReveal")
public class RrProjectEntity {
    private String id;
    private String assignedTo;
    private String cloneSourceProject;
    private String clonedFlag;
    private String createdBy;
    private Timestamp creationDate;
    private String description;
    private Timestamp dueDate;
    private String linkFlag;
    private String masterFlag;
    private String name;
    private String pltSum;
    private Double pltThreadSum;
    private String postInuredFlag;
    private String publishFlag;
    private String receptionDate;
    private Double regionPerilSum;
    private String sourceId;
    private Double xactSum;

    @Id
    @Basic
    @Column(name = "id", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "assignedTo", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Basic
    @Column(name = "cloneSourceProject", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getCloneSourceProject() {
        return cloneSourceProject;
    }

    public void setCloneSourceProject(String cloneSourceProject) {
        this.cloneSourceProject = cloneSourceProject;
    }

    @Basic
    @Column(name = "clonedFlag", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getClonedFlag() {
        return clonedFlag;
    }

    public void setClonedFlag(String clonedFlag) {
        this.clonedFlag = clonedFlag;
    }

    @Basic
    @Column(name = "createdBy", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
    @Column(name = "description", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "linkFlag", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLinkFlag() {
        return linkFlag;
    }

    public void setLinkFlag(String linkFlag) {
        this.linkFlag = linkFlag;
    }

    @Basic
    @Column(name = "masterFlag", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getMasterFlag() {
        return masterFlag;
    }

    public void setMasterFlag(String masterFlag) {
        this.masterFlag = masterFlag;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pltSum", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getPltSum() {
        return pltSum;
    }

    public void setPltSum(String pltSum) {
        this.pltSum = pltSum;
    }

    @Basic
    @Column(name = "pltThreadSum", nullable = true, precision = 0)
    public Double getPltThreadSum() {
        return pltThreadSum;
    }

    public void setPltThreadSum(Double pltThreadSum) {
        this.pltThreadSum = pltThreadSum;
    }

    @Basic
    @Column(name = "postInuredFlag", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getPostInuredFlag() {
        return postInuredFlag;
    }

    public void setPostInuredFlag(String postInuredFlag) {
        this.postInuredFlag = postInuredFlag;
    }

    @Basic
    @Column(name = "publishFlag", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(String publishFlag) {
        this.publishFlag = publishFlag;
    }

    @Basic
    @Column(name = "receptionDate", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(String receptionDate) {
        this.receptionDate = receptionDate;
    }

    @Basic
    @Column(name = "regionPerilSum", nullable = true, precision = 0)
    public Double getRegionPerilSum() {
        return regionPerilSum;
    }

    public void setRegionPerilSum(Double regionPerilSum) {
        this.regionPerilSum = regionPerilSum;
    }

    @Basic
    @Column(name = "sourceID", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Basic
    @Column(name = "xactSum", nullable = true, precision = 0)
    public Double getXactSum() {
        return xactSum;
    }

    public void setXactSum(Double xactSum) {
        this.xactSum = xactSum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrProjectEntity that = (RrProjectEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(assignedTo, that.assignedTo) &&
                Objects.equals(cloneSourceProject, that.cloneSourceProject) &&
                Objects.equals(clonedFlag, that.clonedFlag) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(linkFlag, that.linkFlag) &&
                Objects.equals(masterFlag, that.masterFlag) &&
                Objects.equals(name, that.name) &&
                Objects.equals(pltSum, that.pltSum) &&
                Objects.equals(pltThreadSum, that.pltThreadSum) &&
                Objects.equals(postInuredFlag, that.postInuredFlag) &&
                Objects.equals(publishFlag, that.publishFlag) &&
                Objects.equals(receptionDate, that.receptionDate) &&
                Objects.equals(regionPerilSum, that.regionPerilSum) &&
                Objects.equals(sourceId, that.sourceId) &&
                Objects.equals(xactSum, that.xactSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assignedTo, cloneSourceProject, clonedFlag, createdBy, creationDate, description, dueDate, linkFlag, masterFlag, name, pltSum, pltThreadSum, postInuredFlag, publishFlag, receptionDate, regionPerilSum, sourceId, xactSum);
    }
}
