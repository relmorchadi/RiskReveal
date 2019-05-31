package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ScorPLTHeader", schema = "dbo", catalog = "RiskReveal")
public class ScorPltHeaderEntity {
    private int scorPltHeaderId;
    private String pltType;
    private Integer rrAnalysisId;
    private Integer targetRapId;
    private Integer regionPerilId;
    private Integer projectId;
    private Boolean publishToPricing;
    private String e;
    private Integer pltSimulationPeriods;
    private Boolean generatedFromDefaultAdjustement;
    private Integer cloningSourceId;
    private String pltLossDataFilePath;
    private String pltLossDataFileName;
    private String ccyCode;
    private Timestamp createdDate;
    private String perilCode;
    private String geoCode;
    private String geoDescription;
    private Integer rmsSimulationSet;
    private Integer importSequence;
    private String threadName;
    private String udName;
    private String userOccurrenceBasis;
    private String defaultPltName;
    private String truncationThreshold;
    private String truncationExchangeRate;
    private String truncationCurrency;
    private Integer inuringPackageId;
    private String sourceLossEntityingBasis;
    private Timestamp deletedOn;
    private String deletedDue;
    private String deletedBy;

    @Id
    @Column(name = "scorPLTHeaderId", nullable = false)
    public int getScorPltHeaderId() {
        return scorPltHeaderId;
    }

    public void setScorPltHeaderId(int scorPltHeaderId) {
        this.scorPltHeaderId = scorPltHeaderId;
    }

    @Basic
    @Column(name = "pltType", nullable = true, length = 255)
    public String getPltType() {
        return pltType;
    }

    public void setPltType(String pltType) {
        this.pltType = pltType;
    }

    @Basic
    @Column(name = "rrAnalysisId", nullable = true)
    public Integer getRrAnalysisId() {
        return rrAnalysisId;
    }

    public void setRrAnalysisId(Integer rrAnalysisId) {
        this.rrAnalysisId = rrAnalysisId;
    }

    @Basic
    @Column(name = "targetRapId", nullable = true)
    public Integer getTargetRapId() {
        return targetRapId;
    }

    public void setTargetRapId(Integer targetRapId) {
        this.targetRapId = targetRapId;
    }

    @Basic
    @Column(name = "regionPerilId", nullable = true)
    public Integer getRegionPerilId() {
        return regionPerilId;
    }

    public void setRegionPerilId(Integer regionPerilId) {
        this.regionPerilId = regionPerilId;
    }

    @Basic
    @Column(name = "projectId", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "publishToPricing", nullable = true)
    public Boolean getPublishToPricing() {
        return publishToPricing;
    }

    public void setPublishToPricing(Boolean publishToPricing) {
        this.publishToPricing = publishToPricing;
    }

    @Basic
    @Column(name = "e", nullable = true, length = 255)
    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    @Basic
    @Column(name = "pltSimulationPeriods", nullable = true)
    public Integer getPltSimulationPeriods() {
        return pltSimulationPeriods;
    }

    public void setPltSimulationPeriods(Integer pltSimulationPeriods) {
        this.pltSimulationPeriods = pltSimulationPeriods;
    }

    @Basic
    @Column(name = "generatedFromDefaultAdjustement", nullable = true)
    public Boolean getGeneratedFromDefaultAdjustement() {
        return generatedFromDefaultAdjustement;
    }

    public void setGeneratedFromDefaultAdjustement(Boolean generatedFromDefaultAdjustement) {
        this.generatedFromDefaultAdjustement = generatedFromDefaultAdjustement;
    }

    @Basic
    @Column(name = "cloningSourceId", nullable = true)
    public Integer getCloningSourceId() {
        return cloningSourceId;
    }

    public void setCloningSourceId(Integer cloningSourceId) {
        this.cloningSourceId = cloningSourceId;
    }

    @Basic
    @Column(name = "pltLossDataFilePath", nullable = true, length = 255)
    public String getPltLossDataFilePath() {
        return pltLossDataFilePath;
    }

    public void setPltLossDataFilePath(String pltLossDataFilePath) {
        this.pltLossDataFilePath = pltLossDataFilePath;
    }

    @Basic
    @Column(name = "pltLossDataFileName", nullable = true, length = 255)
    public String getPltLossDataFileName() {
        return pltLossDataFileName;
    }

    public void setPltLossDataFileName(String pltLossDataFileName) {
        this.pltLossDataFileName = pltLossDataFileName;
    }

    @Basic
    @Column(name = "ccyCode", nullable = true, length = 255)
    public String getCcyCode() {
        return ccyCode;
    }

    public void setCcyCode(String ccyCode) {
        this.ccyCode = ccyCode;
    }

    @Basic
    @Column(name = "createdDate", nullable = true)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Basic
    @Column(name = "perilCode", nullable = true, length = 255)
    public String getPerilCode() {
        return perilCode;
    }

    public void setPerilCode(String perilCode) {
        this.perilCode = perilCode;
    }

    @Basic
    @Column(name = "geoCode", nullable = true, length = 255)
    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    @Basic
    @Column(name = "geoDescription", nullable = true, length = 255)
    public String getGeoDescription() {
        return geoDescription;
    }

    public void setGeoDescription(String geoDescription) {
        this.geoDescription = geoDescription;
    }

    @Basic
    @Column(name = "rmsSimulationSet", nullable = true)
    public Integer getRmsSimulationSet() {
        return rmsSimulationSet;
    }

    public void setRmsSimulationSet(Integer rmsSimulationSet) {
        this.rmsSimulationSet = rmsSimulationSet;
    }

    @Basic
    @Column(name = "importSequence", nullable = true)
    public Integer getImportSequence() {
        return importSequence;
    }

    public void setImportSequence(Integer importSequence) {
        this.importSequence = importSequence;
    }

    @Basic
    @Column(name = "threadName", nullable = true, length = 255)
    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    @Basic
    @Column(name = "udName", nullable = true, length = 255)
    public String getUdName() {
        return udName;
    }

    public void setUdName(String udName) {
        this.udName = udName;
    }

    @Basic
    @Column(name = "userOccurrenceBasis", nullable = true, length = 255)
    public String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }

    @Basic
    @Column(name = "defaultPltName", nullable = true, length = 255)
    public String getDefaultPltName() {
        return defaultPltName;
    }

    public void setDefaultPltName(String defaultPltName) {
        this.defaultPltName = defaultPltName;
    }

    @Basic
    @Column(name = "truncationThreshold", nullable = true, length = 255)
    public String getTruncationThreshold() {
        return truncationThreshold;
    }

    public void setTruncationThreshold(String truncationThreshold) {
        this.truncationThreshold = truncationThreshold;
    }

    @Basic
    @Column(name = "truncationExchangeRate", nullable = true, length = 255)
    public String getTruncationExchangeRate() {
        return truncationExchangeRate;
    }

    public void setTruncationExchangeRate(String truncationExchangeRate) {
        this.truncationExchangeRate = truncationExchangeRate;
    }

    @Basic
    @Column(name = "truncationCurrency", nullable = true, length = 255)
    public String getTruncationCurrency() {
        return truncationCurrency;
    }

    public void setTruncationCurrency(String truncationCurrency) {
        this.truncationCurrency = truncationCurrency;
    }

    @Basic
    @Column(name = "inuringPackageId", nullable = true)
    public Integer getInuringPackageId() {
        return inuringPackageId;
    }

    public void setInuringPackageId(Integer inuringPackageId) {
        this.inuringPackageId = inuringPackageId;
    }

    @Basic
    @Column(name = "sourceLossEntityingBasis", nullable = true, length = 255)
    public String getSourceLossEntityingBasis() {
        return sourceLossEntityingBasis;
    }

    public void setSourceLossEntityingBasis(String sourceLossEntityingBasis) {
        this.sourceLossEntityingBasis = sourceLossEntityingBasis;
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
        ScorPltHeaderEntity that = (ScorPltHeaderEntity) o;
        return scorPltHeaderId == that.scorPltHeaderId &&
                Objects.equals(pltType, that.pltType) &&
                Objects.equals(rrAnalysisId, that.rrAnalysisId) &&
                Objects.equals(targetRapId, that.targetRapId) &&
                Objects.equals(regionPerilId, that.regionPerilId) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(publishToPricing, that.publishToPricing) &&
                Objects.equals(e, that.e) &&
                Objects.equals(pltSimulationPeriods, that.pltSimulationPeriods) &&
                Objects.equals(generatedFromDefaultAdjustement, that.generatedFromDefaultAdjustement) &&
                Objects.equals(cloningSourceId, that.cloningSourceId) &&
                Objects.equals(pltLossDataFilePath, that.pltLossDataFilePath) &&
                Objects.equals(pltLossDataFileName, that.pltLossDataFileName) &&
                Objects.equals(ccyCode, that.ccyCode) &&
                Objects.equals(createdDate, that.createdDate) &&
                Objects.equals(perilCode, that.perilCode) &&
                Objects.equals(geoCode, that.geoCode) &&
                Objects.equals(geoDescription, that.geoDescription) &&
                Objects.equals(rmsSimulationSet, that.rmsSimulationSet) &&
                Objects.equals(importSequence, that.importSequence) &&
                Objects.equals(threadName, that.threadName) &&
                Objects.equals(udName, that.udName) &&
                Objects.equals(userOccurrenceBasis, that.userOccurrenceBasis) &&
                Objects.equals(defaultPltName, that.defaultPltName) &&
                Objects.equals(truncationThreshold, that.truncationThreshold) &&
                Objects.equals(truncationExchangeRate, that.truncationExchangeRate) &&
                Objects.equals(truncationCurrency, that.truncationCurrency) &&
                Objects.equals(inuringPackageId, that.inuringPackageId) &&
                Objects.equals(sourceLossEntityingBasis, that.sourceLossEntityingBasis) &&
                Objects.equals(deletedOn, that.deletedOn) &&
                Objects.equals(deletedDue, that.deletedDue) &&
                Objects.equals(deletedBy, that.deletedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scorPltHeaderId, pltType, rrAnalysisId, targetRapId, regionPerilId, projectId, publishToPricing, e, pltSimulationPeriods, generatedFromDefaultAdjustement, cloningSourceId, pltLossDataFilePath, pltLossDataFileName, ccyCode, createdDate, perilCode, geoCode, geoDescription, rmsSimulationSet, importSequence, threadName, udName, userOccurrenceBasis, defaultPltName, truncationThreshold, truncationExchangeRate, truncationCurrency, inuringPackageId, sourceLossEntityingBasis, deletedOn, deletedDue, deletedBy);
    }
}
