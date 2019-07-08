package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "catRequests", schema = "dbo", catalog = "RiskReveal")
public class CatRequestsEntity {
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
    private String analysisCtrSubsidiary;
    private String analysisCtrYear;
    private String assignDate;
    private String assignedTo;
    private String lastUpdateDate;
    private String lastUpdatedBy;
    private String modellingSystemInstance;
    private Timestamp lastUpdateDate2;
    private Timestamp assignDate2;

    @Id
    @Column(name = "id", nullable = false, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "analysisName", nullable = true, length = 255)
    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    @Basic
    @Column(name = "analysisCtrBusinessType", nullable = true, length = 255)
    public String getAnalysisCtrBusinessType() {
        return analysisCtrBusinessType;
    }

    public void setAnalysisCtrBusinessType(String analysisCtrBusinessType) {
        this.analysisCtrBusinessType = analysisCtrBusinessType;
    }

    @Basic
    @Column(name = "analysisCtrId", nullable = true, length = 255)
    public String getAnalysisCtrId() {
        return analysisCtrId;
    }

    public void setAnalysisCtrId(String analysisCtrId) {
        this.analysisCtrId = analysisCtrId;
    }

    @Basic
    @Column(name = "analysisCtrEndorsementNmber", nullable = true, length = 255)
    public String getAnalysisCtrEndorsementNmber() {
        return analysisCtrEndorsementNmber;
    }

    public void setAnalysisCtrEndorsementNmber(String analysisCtrEndorsementNmber) {
        this.analysisCtrEndorsementNmber = analysisCtrEndorsementNmber;
    }

    @Basic
    @Column(name = "analysisCtrFacNumber", nullable = true, length = 255)
    public String getAnalysisCtrFacNumber() {
        return analysisCtrFacNumber;
    }

    public void setAnalysisCtrFacNumber(String analysisCtrFacNumber) {
        this.analysisCtrFacNumber = analysisCtrFacNumber;
    }

    @Basic
    @Column(name = "analysisCtrInsured", nullable = true, length = 255)
    public String getAnalysisCtrInsured() {
        return analysisCtrInsured;
    }

    public void setAnalysisCtrInsured(String analysisCtrInsured) {
        this.analysisCtrInsured = analysisCtrInsured;
    }

    @Basic
    @Column(name = "analysisCtrLabel", nullable = true, length = 255)
    public String getAnalysisCtrLabel() {
        return analysisCtrLabel;
    }

    public void setAnalysisCtrLabel(String analysisCtrLabel) {
        this.analysisCtrLabel = analysisCtrLabel;
    }

    @Basic
    @Column(name = "analysisCtrLob", nullable = true, length = 255)
    public String getAnalysisCtrLob() {
        return analysisCtrLob;
    }

    public void setAnalysisCtrLob(String analysisCtrLob) {
        this.analysisCtrLob = analysisCtrLob;
    }

    @Basic
    @Column(name = "analysisCtrOrderNumber", nullable = true, length = 255)
    public String getAnalysisCtrOrderNumber() {
        return analysisCtrOrderNumber;
    }

    public void setAnalysisCtrOrderNumber(String analysisCtrOrderNumber) {
        this.analysisCtrOrderNumber = analysisCtrOrderNumber;
    }

    @Basic
    @Column(name = "analysisCtrSubsidiary", nullable = true, length = 255)
    public String getAnalysisCtrSubsidiary() {
        return analysisCtrSubsidiary;
    }

    public void setAnalysisCtrSubsidiary(String analysisCtrSubsidiary) {
        this.analysisCtrSubsidiary = analysisCtrSubsidiary;
    }

    @Basic
    @Column(name = "analysisCtrYear", nullable = true, length = 255)
    public String getAnalysisCtrYear() {
        return analysisCtrYear;
    }

    public void setAnalysisCtrYear(String analysisCtrYear) {
        this.analysisCtrYear = analysisCtrYear;
    }

    @Basic
    @Column(name = "assignDate", nullable = true, length = 255)
    public String getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(String assignDate) {
        this.assignDate = assignDate;
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
    @Column(name = "lastUpdateDate", nullable = true, length = 255)
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    @Basic
    @Column(name = "lastUpdatedBy", nullable = true, length = 255)
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    @Basic
    @Column(name = "modellingSystemInstance", nullable = true, length = 255)
    public String getEntitylingSystemInstance() {
        return modellingSystemInstance;
    }

    public void setEntitylingSystemInstance(String modellingSystemInstance) {
        this.modellingSystemInstance = modellingSystemInstance;
    }

    @Basic
    @Column(name = "lastUpdateDate2", nullable = true)
    public Timestamp getLastUpdateDate2() {
        return lastUpdateDate2;
    }

    public void setLastUpdateDate2(Timestamp lastUpdateDate2) {
        this.lastUpdateDate2 = lastUpdateDate2;
    }

    @Basic
    @Column(name = "assignDate2", nullable = true)
    public Timestamp getAssignDate2() {
        return assignDate2;
    }

    public void setAssignDate2(Timestamp assignDate2) {
        this.assignDate2 = assignDate2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatRequestsEntity that = (CatRequestsEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(analysisName, that.analysisName) &&
                Objects.equals(analysisCtrBusinessType, that.analysisCtrBusinessType) &&
                Objects.equals(analysisCtrId, that.analysisCtrId) &&
                Objects.equals(analysisCtrEndorsementNmber, that.analysisCtrEndorsementNmber) &&
                Objects.equals(analysisCtrFacNumber, that.analysisCtrFacNumber) &&
                Objects.equals(analysisCtrInsured, that.analysisCtrInsured) &&
                Objects.equals(analysisCtrLabel, that.analysisCtrLabel) &&
                Objects.equals(analysisCtrLob, that.analysisCtrLob) &&
                Objects.equals(analysisCtrOrderNumber, that.analysisCtrOrderNumber) &&
                Objects.equals(analysisCtrSubsidiary, that.analysisCtrSubsidiary) &&
                Objects.equals(analysisCtrYear, that.analysisCtrYear) &&
                Objects.equals(assignDate, that.assignDate) &&
                Objects.equals(assignedTo, that.assignedTo) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(lastUpdatedBy, that.lastUpdatedBy) &&
                Objects.equals(modellingSystemInstance, that.modellingSystemInstance) &&
                Objects.equals(lastUpdateDate2, that.lastUpdateDate2) &&
                Objects.equals(assignDate2, that.assignDate2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, analysisName, analysisCtrBusinessType, analysisCtrId, analysisCtrEndorsementNmber, analysisCtrFacNumber, analysisCtrInsured, analysisCtrLabel, analysisCtrLob, analysisCtrOrderNumber, analysisCtrSubsidiary, analysisCtrYear, assignDate, assignedTo, lastUpdateDate, lastUpdatedBy, modellingSystemInstance, lastUpdateDate2, assignDate2);
    }
}
