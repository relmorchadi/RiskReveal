package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RRAnalysis")
public class RrAnalysisEntity {
    private int rrAnalysisId;
    private Integer projectId;
    private Timestamp importedDate;
    private Timestamp creationDate;
    private Timestamp runDate;
    private String importStatus;
    private String sourceModellingSystemInstance;
    private String sourceModellingVendor;
    private String sourceModellingSystem;
    private String sourceModellingSystemVersion;
    private Long dataSourceId;
    private String dataSourceName;
    private String fileName;
    private Integer analysisId;
    private String analysisName;
    private String grain;
    private String financialPerspective;
    private String treatyLabel;
    private String treatyTag;
    private String peril;
    private String geoCode;
    private String regionPeril;
    private String sourceCurrency;
    private String targetCurrency;
    private String targetCurrencyBasis;
    private BigDecimal exchangeRate;
    private String defaultOccurrenceBasis;
    private String userOccurrenceBasis;
    private BigDecimal proportion;
    private String proxyScalingBasis;
    private String proxyScalingNarrative;
    private BigDecimal unitMultiplier;
    private String multiplierBasis;
    private String multiplierNarrative;
    private String profileKey;
    private String description;
    private String analysisLevel;
    private String lossAmplification;
    private String model;
    private String tags;
    private String userNotes;
    private String overrideReasonText;
    private String resultName;
    private String sourceLossModellingBasis;

    @Id
    @Column(name = "rrAnalysisId", nullable = false)
    public int getRrAnalysisId() {
        return rrAnalysisId;
    }

    public void setRrAnalysisId(int rrAnalysisId) {
        this.rrAnalysisId = rrAnalysisId;
    }

    @Basic
    @Column(name = "projectId")
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "importedDate")
    public Timestamp getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Timestamp importedDate) {
        this.importedDate = importedDate;
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
    @Column(name = "runDate")
    public Timestamp getRunDate() {
        return runDate;
    }

    public void setRunDate(Timestamp runDate) {
        this.runDate = runDate;
    }

    @Basic
    @Column(name = "importStatus", length = 255)
    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    @Basic
    @Column(name = "sourceModellingSystemInstance", length = 255)
    public String getSourceModellingSystemInstance() {
        return sourceModellingSystemInstance;
    }

    public void setSourceModellingSystemInstance(String sourceModellingSystemInstance) {
        this.sourceModellingSystemInstance = sourceModellingSystemInstance;
    }

    @Basic
    @Column(name = "sourceModellingVendor", length = 255)
    public String getSourceModellingVendor() {
        return sourceModellingVendor;
    }

    public void setSourceModellingVendor(String sourceModellingVendor) {
        this.sourceModellingVendor = sourceModellingVendor;
    }

    @Basic
    @Column(name = "sourceModellingSystem", length = 255)
    public String getSourceModellingSystem() {
        return sourceModellingSystem;
    }

    public void setSourceModellingSystem(String sourceModellingSystem) {
        this.sourceModellingSystem = sourceModellingSystem;
    }

    @Basic
    @Column(name = "sourceModellingSystemVersion", length = 255)
    public String getSourceModellingSystemVersion() {
        return sourceModellingSystemVersion;
    }

    public void setSourceModellingSystemVersion(String sourceModellingSystemVersion) {
        this.sourceModellingSystemVersion = sourceModellingSystemVersion;
    }

    @Basic
    @Column(name = "dataSourceId")
    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Basic
    @Column(name = "dataSourceName", length = 255)
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Basic
    @Column(name = "fileName", length = 255)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "analysisId")
    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
    }

    @Basic
    @Column(name = "analysisName", length = 255)
    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    @Basic
    @Column(name = "grain", length = 255)
    public String getGrain() {
        return grain;
    }

    public void setGrain(String grain) {
        this.grain = grain;
    }

    @Basic
    @Column(name = "financialPerspective", length = 255)
    public String getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(String financialPerspective) {
        this.financialPerspective = financialPerspective;
    }

    @Basic
    @Column(name = "treatyLabel", length = 255)
    public String getTreatyLabel() {
        return treatyLabel;
    }

    public void setTreatyLabel(String treatyLabel) {
        this.treatyLabel = treatyLabel;
    }

    @Basic
    @Column(name = "treatyTag", length = 255)
    public String getTreatyTag() {
        return treatyTag;
    }

    public void setTreatyTag(String treatyTag) {
        this.treatyTag = treatyTag;
    }

    @Basic
    @Column(name = "peril", length = 255)
    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    @Basic
    @Column(name = "geoCode", length = 255)
    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    @Basic
    @Column(name = "regionPeril", length = 255)
    public String getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(String regionPeril) {
        this.regionPeril = regionPeril;
    }

    @Basic
    @Column(name = "sourceCurrency", length = 255)
    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    @Basic
    @Column(name = "targetCurrency", length = 255)
    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Basic
    @Column(name = "targetCurrencyBasis", length = 255)
    public String getTargetCurrencyBasis() {
        return targetCurrencyBasis;
    }

    public void setTargetCurrencyBasis(String targetCurrencyBasis) {
        this.targetCurrencyBasis = targetCurrencyBasis;
    }

    @Basic
    @Column(name = "exchangeRate", precision = 7)
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Basic
    @Column(name = "defaultOccurrenceBasis", length = 255)
    public String getDefaultOccurrenceBasis() {
        return defaultOccurrenceBasis;
    }

    public void setDefaultOccurrenceBasis(String defaultOccurrenceBasis) {
        this.defaultOccurrenceBasis = defaultOccurrenceBasis;
    }

    @Basic
    @Column(name = "userOccurrenceBasis", length = 255)
    public String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }

    @Basic
    @Column(name = "proportion", precision = 7)
    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    @Basic
    @Column(name = "proxyScalingBasis", length = 255)
    public String getProxyScalingBasis() {
        return proxyScalingBasis;
    }

    public void setProxyScalingBasis(String proxyScalingBasis) {
        this.proxyScalingBasis = proxyScalingBasis;
    }

    @Basic
    @Column(name = "proxyScalingNarrative", length = 255)
    public String getProxyScalingNarrative() {
        return proxyScalingNarrative;
    }

    public void setProxyScalingNarrative(String proxyScalingNarrative) {
        this.proxyScalingNarrative = proxyScalingNarrative;
    }

    @Basic
    @Column(name = "unitMultiplier", precision = 7)
    public BigDecimal getUnitMultiplier() {
        return unitMultiplier;
    }

    public void setUnitMultiplier(BigDecimal unitMultiplier) {
        this.unitMultiplier = unitMultiplier;
    }

    @Basic
    @Column(name = "multiplierBasis", length = 255)
    public String getMultiplierBasis() {
        return multiplierBasis;
    }

    public void setMultiplierBasis(String multiplierBasis) {
        this.multiplierBasis = multiplierBasis;
    }

    @Basic
    @Column(name = "multiplierNarrative", length = 255)
    public String getMultiplierNarrative() {
        return multiplierNarrative;
    }

    public void setMultiplierNarrative(String multiplierNarrative) {
        this.multiplierNarrative = multiplierNarrative;
    }

    @Basic
    @Column(name = "profileKey", length = 255)
    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    @Basic
    @Column(name = "description", length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "analysisLevel", length = 255)
    public String getAnalysisLevel() {
        return analysisLevel;
    }

    public void setAnalysisLevel(String analysisLevel) {
        this.analysisLevel = analysisLevel;
    }

    @Basic
    @Column(name = "lossAmplification", length = 255)
    public String getLossAmplification() {
        return lossAmplification;
    }

    public void setLossAmplification(String lossAmplification) {
        this.lossAmplification = lossAmplification;
    }

    @Basic
    @Column(name = "model", length = 255)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "tags", length = 255)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Basic
    @Column(name = "userNotes", length = 255)
    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    @Basic
    @Column(name = "overrideReasonText", length = 255)
    public String getOverrideReasonText() {
        return overrideReasonText;
    }

    public void setOverrideReasonText(String overrideReasonText) {
        this.overrideReasonText = overrideReasonText;
    }

    @Basic
    @Column(name = "resultName", length = 255)
    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    @Basic
    @Column(name = "sourceLossModellingBasis", length = 255)
    public String getSourceLossModellingBasis() {
        return sourceLossModellingBasis;
    }

    public void setSourceLossModellingBasis(String sourceLossModellingBasis) {
        this.sourceLossModellingBasis = sourceLossModellingBasis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrAnalysisEntity that = (RrAnalysisEntity) o;
        return rrAnalysisId == that.rrAnalysisId &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(importedDate, that.importedDate) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(runDate, that.runDate) &&
                Objects.equals(importStatus, that.importStatus) &&
                Objects.equals(sourceModellingSystemInstance, that.sourceModellingSystemInstance) &&
                Objects.equals(sourceModellingVendor, that.sourceModellingVendor) &&
                Objects.equals(sourceModellingSystem, that.sourceModellingSystem) &&
                Objects.equals(sourceModellingSystemVersion, that.sourceModellingSystemVersion) &&
                Objects.equals(dataSourceId, that.dataSourceId) &&
                Objects.equals(dataSourceName, that.dataSourceName) &&
                Objects.equals(fileName, that.fileName) &&
                Objects.equals(analysisId, that.analysisId) &&
                Objects.equals(analysisName, that.analysisName) &&
                Objects.equals(grain, that.grain) &&
                Objects.equals(financialPerspective, that.financialPerspective) &&
                Objects.equals(treatyLabel, that.treatyLabel) &&
                Objects.equals(treatyTag, that.treatyTag) &&
                Objects.equals(peril, that.peril) &&
                Objects.equals(geoCode, that.geoCode) &&
                Objects.equals(regionPeril, that.regionPeril) &&
                Objects.equals(sourceCurrency, that.sourceCurrency) &&
                Objects.equals(targetCurrency, that.targetCurrency) &&
                Objects.equals(targetCurrencyBasis, that.targetCurrencyBasis) &&
                Objects.equals(exchangeRate, that.exchangeRate) &&
                Objects.equals(defaultOccurrenceBasis, that.defaultOccurrenceBasis) &&
                Objects.equals(userOccurrenceBasis, that.userOccurrenceBasis) &&
                Objects.equals(proportion, that.proportion) &&
                Objects.equals(proxyScalingBasis, that.proxyScalingBasis) &&
                Objects.equals(proxyScalingNarrative, that.proxyScalingNarrative) &&
                Objects.equals(unitMultiplier, that.unitMultiplier) &&
                Objects.equals(multiplierBasis, that.multiplierBasis) &&
                Objects.equals(multiplierNarrative, that.multiplierNarrative) &&
                Objects.equals(profileKey, that.profileKey) &&
                Objects.equals(description, that.description) &&
                Objects.equals(analysisLevel, that.analysisLevel) &&
                Objects.equals(lossAmplification, that.lossAmplification) &&
                Objects.equals(model, that.model) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(userNotes, that.userNotes) &&
                Objects.equals(overrideReasonText, that.overrideReasonText) &&
                Objects.equals(resultName, that.resultName) &&
                Objects.equals(sourceLossModellingBasis, that.sourceLossModellingBasis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rrAnalysisId, projectId, importedDate, creationDate, runDate, importStatus, sourceModellingSystemInstance, sourceModellingVendor, sourceModellingSystem, sourceModellingSystemVersion, dataSourceId, dataSourceName, fileName, analysisId, analysisName, grain, financialPerspective, treatyLabel, treatyTag, peril, geoCode, regionPeril, sourceCurrency, targetCurrency, targetCurrencyBasis, exchangeRate, defaultOccurrenceBasis, userOccurrenceBasis, proportion, proxyScalingBasis, proxyScalingNarrative, unitMultiplier, multiplierBasis, multiplierNarrative, profileKey, description, analysisLevel, lossAmplification, model, tags, userNotes, overrideReasonText, resultName, sourceLossModellingBasis);
    }
}
