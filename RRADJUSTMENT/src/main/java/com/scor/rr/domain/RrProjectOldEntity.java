package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RR-project-old", schema = "dbo", catalog = "RiskReveal")
public class RrProjectOldEntity {
    private String id;
    private String assignedTo;
    private Boolean clonedFlag;
    private String createdBy;
    private Timestamp creationDate;
    private String description;
    private Timestamp dueDate;
    private Boolean isLocking;
    private Boolean linkFlag;
    private Boolean masterFlag;
    private String mgaexpectedFrequencycode;
    private String mgafinancialBasiscode;
    private Integer mgasectionId;
    private String mgasubmissionPeriod;
    private String mgatreatyId;
    private String name;
    private Integer pltSum;
    private Integer pltThreadSum;
    private Boolean postInuredFlag;
    private Boolean publishFlag;
    private Timestamp receptionDate;
    private Integer regionPerilSum;
    private Integer xactSum;

    @Id
    @Column(name = "_id", nullable = false, length = 255,insertable = false ,updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "assignedTo", length = 255,insertable = false ,updatable = false)
    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    @Basic
    @Column(name = "clonedFlag")
    public Boolean getClonedFlag() {
        return clonedFlag;
    }

    public void setClonedFlag(Boolean clonedFlag) {
        this.clonedFlag = clonedFlag;
    }

    @Basic
    @Column(name = "createdBy", length = 255,insertable = false ,updatable = false)
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Basic
    @Column(name = "creationDate")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "description", length = 255,insertable = false ,updatable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "dueDate")
    public Timestamp getDueDate() {
        return dueDate;
    }

    public void setDueDate(Timestamp dueDate) {
        this.dueDate = dueDate;
    }

    @Basic
    @Column(name = "isLocking")
    public Boolean getLocking() {
        return isLocking;
    }

    public void setLocking(Boolean locking) {
        isLocking = locking;
    }

    @Basic
    @Column(name = "linkFlag")
    public Boolean getLinkFlag() {
        return linkFlag;
    }

    public void setLinkFlag(Boolean linkFlag) {
        this.linkFlag = linkFlag;
    }

    @Basic
    @Column(name = "masterFlag")
    public Boolean getMasterFlag() {
        return masterFlag;
    }

    public void setMasterFlag(Boolean masterFlag) {
        this.masterFlag = masterFlag;
    }

    @Basic
    @Column(name = "mgaexpectedFrequencycode", length = 255,insertable = false ,updatable = false)
    public String getMgaexpectedFrequencycode() {
        return mgaexpectedFrequencycode;
    }

    public void setMgaexpectedFrequencycode(String mgaexpectedFrequencycode) {
        this.mgaexpectedFrequencycode = mgaexpectedFrequencycode;
    }

    @Basic
    @Column(name = "mgafinancialBasiscode", length = 255,insertable = false ,updatable = false)
    public String getMgafinancialBasiscode() {
        return mgafinancialBasiscode;
    }

    public void setMgafinancialBasiscode(String mgafinancialBasiscode) {
        this.mgafinancialBasiscode = mgafinancialBasiscode;
    }

    @Basic
    @Column(name = "mgasectionId")
    public Integer getMgasectionId() {
        return mgasectionId;
    }

    public void setMgasectionId(Integer mgasectionId) {
        this.mgasectionId = mgasectionId;
    }

    @Basic
    @Column(name = "mgasubmissionPeriod", length = 255,insertable = false ,updatable = false)
    public String getMgasubmissionPeriod() {
        return mgasubmissionPeriod;
    }

    public void setMgasubmissionPeriod(String mgasubmissionPeriod) {
        this.mgasubmissionPeriod = mgasubmissionPeriod;
    }

    @Basic
    @Column(name = "mgatreatyId", length = 255,insertable = false ,updatable = false)
    public String getMgatreatyId() {
        return mgatreatyId;
    }

    public void setMgatreatyId(String mgatreatyId) {
        this.mgatreatyId = mgatreatyId;
    }

    @Basic
    @Column(name = "name", length = 255,insertable = false ,updatable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "pltSum")
    public Integer getPltSum() {
        return pltSum;
    }

    public void setPltSum(Integer pltSum) {
        this.pltSum = pltSum;
    }

    @Basic
    @Column(name = "pltThreadSum")
    public Integer getPltThreadSum() {
        return pltThreadSum;
    }

    public void setPltThreadSum(Integer pltThreadSum) {
        this.pltThreadSum = pltThreadSum;
    }

    @Basic
    @Column(name = "postInuredFlag")
    public Boolean getPostInuredFlag() {
        return postInuredFlag;
    }

    public void setPostInuredFlag(Boolean postInuredFlag) {
        this.postInuredFlag = postInuredFlag;
    }

    @Basic
    @Column(name = "publishFlag")
    public Boolean getPublishFlag() {
        return publishFlag;
    }

    public void setPublishFlag(Boolean publishFlag) {
        this.publishFlag = publishFlag;
    }

    @Basic
    @Column(name = "receptionDate")
    public Timestamp getReceptionDate() {
        return receptionDate;
    }

    public void setReceptionDate(Timestamp receptionDate) {
        this.receptionDate = receptionDate;
    }

    @Basic
    @Column(name = "regionPerilSum")
    public Integer getRegionPerilSum() {
        return regionPerilSum;
    }

    public void setRegionPerilSum(Integer regionPerilSum) {
        this.regionPerilSum = regionPerilSum;
    }

    @Basic
    @Column(name = "xactSum")
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
        RrProjectOldEntity that = (RrProjectOldEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(assignedTo, that.assignedTo) &&
                Objects.equals(clonedFlag, that.clonedFlag) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(description, that.description) &&
                Objects.equals(dueDate, that.dueDate) &&
                Objects.equals(isLocking, that.isLocking) &&
                Objects.equals(linkFlag, that.linkFlag) &&
                Objects.equals(masterFlag, that.masterFlag) &&
                Objects.equals(mgaexpectedFrequencycode, that.mgaexpectedFrequencycode) &&
                Objects.equals(mgafinancialBasiscode, that.mgafinancialBasiscode) &&
                Objects.equals(mgasectionId, that.mgasectionId) &&
                Objects.equals(mgasubmissionPeriod, that.mgasubmissionPeriod) &&
                Objects.equals(mgatreatyId, that.mgatreatyId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(pltSum, that.pltSum) &&
                Objects.equals(pltThreadSum, that.pltThreadSum) &&
                Objects.equals(postInuredFlag, that.postInuredFlag) &&
                Objects.equals(publishFlag, that.publishFlag) &&
                Objects.equals(receptionDate, that.receptionDate) &&
                Objects.equals(regionPerilSum, that.regionPerilSum) &&
                Objects.equals(xactSum, that.xactSum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assignedTo, clonedFlag, createdBy, creationDate, description, dueDate, isLocking, linkFlag, masterFlag, mgaexpectedFrequencycode, mgafinancialBasiscode, mgasectionId, mgasubmissionPeriod, mgatreatyId, name, pltSum, pltThreadSum, postInuredFlag, publishFlag, receptionDate, regionPerilSum, xactSum);
    }
}
