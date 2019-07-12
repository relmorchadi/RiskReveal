package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "FAC", schema = "dbo", catalog = "RiskReveal")
public class FacEntity {
    private String id;
    private String analysisName;
    private String analysisCtrBusinessType;
    private String analysisCtrId;
    private String analysisCtrEndorsementNmber;
    private String analysisCtrFacNumber;
    private String analysisCtrInsured;
    private String analysisCtrLabel;
    private String analysisCtrLob;
    private String analysisCtrOrderNumber;
    private Integer analysisCtrSubsidiary;
    private Integer analysisCtrYear;
    private Timestamp assignedDate;
    private String assignedTo;
    private Timestamp lastUpdateDate;
    private String lastUpdatedBy;
    private Integer modellingSystemInstance;

    @Id
    @Column(name = "id", nullable = false, length = 255,insertable = false ,updatable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "analysisName", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    @Basic
    @Column(name = "analysisCtrBusinessType", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisCtrBusinessType() {
        return analysisCtrBusinessType;
    }

    public void setAnalysisCtrBusinessType(String analysisCtrBusinessType) {
        this.analysisCtrBusinessType = analysisCtrBusinessType;
    }

    @Basic
    @Column(name = "analysisCtrId", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisCtrId() {
        return analysisCtrId;
    }

    public void setAnalysisCtrId(String analysisCtrId) {
        this.analysisCtrId = analysisCtrId;
    }

    @Basic
    @Column(name = "analysisCtrEndorsementNmber", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisCtrEndorsementNmber() {
        return analysisCtrEndorsementNmber;
    }

    public void setAnalysisCtrEndorsementNmber(String analysisCtrEndorsementNmber) {
        this.analysisCtrEndorsementNmber = analysisCtrEndorsementNmber;
    }

    @Basic
    @Column(name = "analysisCtrFacNumber", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisCtrFacNumber() {
        return analysisCtrFacNumber;
    }

    public void setAnalysisCtrFacNumber(String analysisCtrFacNumber) {
        this.analysisCtrFacNumber = analysisCtrFacNumber;
    }

    @Basic
    @Column(name = "analysisCtrInsured", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisCtrInsured() {
        return analysisCtrInsured;
    }

    public void setAnalysisCtrInsured(String analysisCtrInsured) {
        this.analysisCtrInsured = analysisCtrInsured;
    }

    @Basic
    @Column(name = "analysisCtrLabel", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisCtrLabel() {
        return analysisCtrLabel;
    }

    public void setAnalysisCtrLabel(String analysisCtrLabel) {
        this.analysisCtrLabel = analysisCtrLabel;
    }

    @Basic
    @Column(name = "analysisCtrLob", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisCtrLob() {
        return analysisCtrLob;
    }

    public void setAnalysisCtrLob(String analysisCtrLob) {
        this.analysisCtrLob = analysisCtrLob;
    }

    @Basic
    @Column(name = "analysisCtrOrderNumber", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getAnalysisCtrOrderNumber() {
        return analysisCtrOrderNumber;
    }

    public void setAnalysisCtrOrderNumber(String analysisCtrOrderNumber) {
        this.analysisCtrOrderNumber = analysisCtrOrderNumber;
    }

    @Basic
    @Column(name = "analysisCtrSubsidiary", nullable = true)
    public Integer getAnalysisCtrSubsidiary() {
        return analysisCtrSubsidiary;
    }

    public void setAnalysisCtrSubsidiary(Integer analysisCtrSubsidiary) {
        this.analysisCtrSubsidiary = analysisCtrSubsidiary;
    }

    @Basic
    @Column(name = "analysisCtrYear", nullable = true)
    public Integer getAnalysisCtrYear() {
        return analysisCtrYear;
    }

    public void setAnalysisCtrYear(Integer analysisCtrYear) {
        this.analysisCtrYear = analysisCtrYear;
    }

    @Basic
    @Column(name = "assignedDate", nullable = true)
    public Timestamp getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(Timestamp assignedDate) {
        this.assignedDate = assignedDate;
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
    @Column(name = "lastUpdateDate", nullable = true)
    public Timestamp getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Timestamp lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Basic
    @Column(name = "lastUpdatedBy", nullable = true, length = 255,insertable = false ,updatable = false)
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Basic
    @Column(name = "modellingSystemInstance", nullable = true)
    public Integer getEntitylingSystemInstance() {
        return modellingSystemInstance;
    }

    public void setEntitylingSystemInstance(Integer modellingSystemInstance) {
        this.modellingSystemInstance = modellingSystemInstance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FacEntity facEntity = (FacEntity) o;
        return Objects.equals(id, facEntity.id) &&
                Objects.equals(analysisName, facEntity.analysisName) &&
                Objects.equals(analysisCtrBusinessType, facEntity.analysisCtrBusinessType) &&
                Objects.equals(analysisCtrId, facEntity.analysisCtrId) &&
                Objects.equals(analysisCtrEndorsementNmber, facEntity.analysisCtrEndorsementNmber) &&
                Objects.equals(analysisCtrFacNumber, facEntity.analysisCtrFacNumber) &&
                Objects.equals(analysisCtrInsured, facEntity.analysisCtrInsured) &&
                Objects.equals(analysisCtrLabel, facEntity.analysisCtrLabel) &&
                Objects.equals(analysisCtrLob, facEntity.analysisCtrLob) &&
                Objects.equals(analysisCtrOrderNumber, facEntity.analysisCtrOrderNumber) &&
                Objects.equals(analysisCtrSubsidiary, facEntity.analysisCtrSubsidiary) &&
                Objects.equals(analysisCtrYear, facEntity.analysisCtrYear) &&
                Objects.equals(assignedDate, facEntity.assignedDate) &&
                Objects.equals(assignedTo, facEntity.assignedTo) &&
                Objects.equals(lastUpdateDate, facEntity.lastUpdateDate) &&
                Objects.equals(lastUpdatedBy, facEntity.lastUpdatedBy) &&
                Objects.equals(modellingSystemInstance, facEntity.modellingSystemInstance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, analysisName, analysisCtrBusinessType, analysisCtrId, analysisCtrEndorsementNmber, analysisCtrFacNumber, analysisCtrInsured, analysisCtrLabel, analysisCtrLob, analysisCtrOrderNumber, analysisCtrSubsidiary, analysisCtrYear, assignedDate, assignedTo, lastUpdateDate, lastUpdatedBy, modellingSystemInstance);
    }
}
