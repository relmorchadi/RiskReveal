package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RRAnalysisold", schema = "dbo", catalog = "RiskReveal")
public class RrAnalysisOldEntity {
    private String id;
    private Double analysisId;
    private String analysisLevel;
    private String analysisName;
    private String creationDate;
    private Double dataSourceId;
    private String dataSourceName;
    private String defaultOccurrenceBasis;
    private String description;
    private String exchangeRate;
    private String financialPerspective;
    private String geoCode;
    private String grain;
    private String grouped;
    private String importStatus;
    private String importedDate;
    private Double includedTargetRapIds0;
    private String lossAmplification;
    private String model;
    private String modelingResultDataSourceId;
    private String overrideReasonText;
    private String peril;
    private String profileKey;
    private String profileName;
    private String projectId;
    private String projectImportRunId;
    private String proportion;
    private String region;
    private String regionPeril;
    private String runDate;
    private String sourceCurrency;
    private String sourceEntitylingSystem;
    private String sourceEntitylingSystemInstance;
    private Double sourceEntitylingSystemVersion;
    private String sourceEntitylingVendor;
    private String subPeril;
    private String tags;
    private String targetCurrency;
    private String targetCurrencyBasis;
    private String unitMultiplier;
    private String userNotes;
    private String userOccurrenceBasis;

    @Id
    @Basic
    @Column(name = "_id", nullable = true, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "analysisId", nullable = true, precision = 0)
    public Double getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Double analysisId) {
        this.analysisId = analysisId;
    }

    @Basic
    @Column(name = "analysisLevel", nullable = true, length = 255)
    public String getAnalysisLevel() {
        return analysisLevel;
    }

    public void setAnalysisLevel(String analysisLevel) {
        this.analysisLevel = analysisLevel;
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
    @Column(name = "creationDate", nullable = true, length = 255)
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "dataSourceId", nullable = true, precision = 0)
    public Double getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Double dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    @Basic
    @Column(name = "dataSourceName", nullable = true, length = 255)
    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }

    @Basic
    @Column(name = "defaultOccurrenceBasis", nullable = true, length = 255)
    public String getDefaultOccurrenceBasis() {
        return defaultOccurrenceBasis;
    }

    public void setDefaultOccurrenceBasis(String defaultOccurrenceBasis) {
        this.defaultOccurrenceBasis = defaultOccurrenceBasis;
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
    @Column(name = "exchangeRate", nullable = true, length = 255)
    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    @Basic
    @Column(name = "financialPerspective", nullable = true, length = 255)
    public String getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(String financialPerspective) {
        this.financialPerspective = financialPerspective;
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
    @Column(name = "grain", nullable = true, length = 255)
    public String getGrain() {
        return grain;
    }

    public void setGrain(String grain) {
        this.grain = grain;
    }

    @Basic
    @Column(name = "grouped", nullable = true, length = 255)
    public String getGrouped() {
        return grouped;
    }

    public void setGrouped(String grouped) {
        this.grouped = grouped;
    }

    @Basic
    @Column(name = "importStatus", nullable = true, length = 255)
    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }

    @Basic
    @Column(name = "importedDate", nullable = true, length = 255)
    public String getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(String importedDate) {
        this.importedDate = importedDate;
    }

    @Basic
    @Column(name = "includedTargetRapIds#0", nullable = true, precision = 0)
    public Double getIncludedTargetRapIds0() {
        return includedTargetRapIds0;
    }

    public void setIncludedTargetRapIds0(Double includedTargetRapIds0) {
        this.includedTargetRapIds0 = includedTargetRapIds0;
    }

    @Basic
    @Column(name = "lossAmplification", nullable = true, length = 255)
    public String getLossAmplification() {
        return lossAmplification;
    }

    public void setLossAmplification(String lossAmplification) {
        this.lossAmplification = lossAmplification;
    }

    @Basic
    @Column(name = "model", nullable = true, length = 255)
    public String getEntity() {
        return model;
    }

    public void setEntity(String model) {
        this.model = model;
    }

    @Basic
    @Column(name = "modelingResultDataSourceId", nullable = true, length = 255)
    public String getEntityingResultDataSourceId() {
        return modelingResultDataSourceId;
    }

    public void setEntityingResultDataSourceId(String modelingResultDataSourceId) {
        this.modelingResultDataSourceId = modelingResultDataSourceId;
    }

    @Basic
    @Column(name = "overrideReasonText", nullable = true, length = 255)
    public String getOverrideReasonText() {
        return overrideReasonText;
    }

    public void setOverrideReasonText(String overrideReasonText) {
        this.overrideReasonText = overrideReasonText;
    }

    @Basic
    @Column(name = "peril", nullable = true, length = 255)
    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    @Basic
    @Column(name = "profileKey", nullable = true, length = 255)
    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    @Basic
    @Column(name = "profileName", nullable = true, length = 255)
    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    @Basic
    @Column(name = "projectId", nullable = true, length = 255)
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "projectImportRunId", nullable = true, length = 255)
    public String getProjectImportRunId() {
        return projectImportRunId;
    }

    public void setProjectImportRunId(String projectImportRunId) {
        this.projectImportRunId = projectImportRunId;
    }

    @Basic
    @Column(name = "proportion", nullable = true, length = 255)
    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }

    @Basic
    @Column(name = "region", nullable = true, length = 255)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "regionPeril", nullable = true, length = 255)
    public String getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(String regionPeril) {
        this.regionPeril = regionPeril;
    }

    @Basic
    @Column(name = "runDate", nullable = true, length = 255)
    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    @Basic
    @Column(name = "sourceCurrency", nullable = true, length = 255)
    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }

    @Basic
    @Column(name = "sourceEntitylingSystem", nullable = true, length = 255)
    public String getSourceEntitylingSystem() {
        return sourceEntitylingSystem;
    }

    public void setSourceEntitylingSystem(String sourceEntitylingSystem) {
        this.sourceEntitylingSystem = sourceEntitylingSystem;
    }

    @Basic
    @Column(name = "sourceEntitylingSystemInstance", nullable = true, length = 255)
    public String getSourceEntitylingSystemInstance() {
        return sourceEntitylingSystemInstance;
    }

    public void setSourceEntitylingSystemInstance(String sourceEntitylingSystemInstance) {
        this.sourceEntitylingSystemInstance = sourceEntitylingSystemInstance;
    }

    @Basic
    @Column(name = "sourceEntitylingSystemVersion", nullable = true, precision = 0)
    public Double getSourceEntitylingSystemVersion() {
        return sourceEntitylingSystemVersion;
    }

    public void setSourceEntitylingSystemVersion(Double sourceEntitylingSystemVersion) {
        this.sourceEntitylingSystemVersion = sourceEntitylingSystemVersion;
    }

    @Basic
    @Column(name = "sourceEntitylingVendor", nullable = true, length = 255)
    public String getSourceEntitylingVendor() {
        return sourceEntitylingVendor;
    }

    public void setSourceEntitylingVendor(String sourceEntitylingVendor) {
        this.sourceEntitylingVendor = sourceEntitylingVendor;
    }

    @Basic
    @Column(name = "subPeril", nullable = true, length = 255)
    public String getSubPeril() {
        return subPeril;
    }

    public void setSubPeril(String subPeril) {
        this.subPeril = subPeril;
    }

    @Basic
    @Column(name = "tags", nullable = true, length = 255)
    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Basic
    @Column(name = "targetCurrency", nullable = true, length = 255)
    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }

    @Basic
    @Column(name = "targetCurrencyBasis", nullable = true, length = 255)
    public String getTargetCurrencyBasis() {
        return targetCurrencyBasis;
    }

    public void setTargetCurrencyBasis(String targetCurrencyBasis) {
        this.targetCurrencyBasis = targetCurrencyBasis;
    }

    @Basic
    @Column(name = "unitMultiplier", nullable = true, length = 255)
    public String getUnitMultiplier() {
        return unitMultiplier;
    }

    public void setUnitMultiplier(String unitMultiplier) {
        this.unitMultiplier = unitMultiplier;
    }

    @Basic
    @Column(name = "userNotes", nullable = true, length = 255)
    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }

    @Basic
    @Column(name = "userOccurrenceBasis", nullable = true, length = 255)
    public String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrAnalysisOldEntity that = (RrAnalysisOldEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(analysisId, that.analysisId) &&
                Objects.equals(analysisLevel, that.analysisLevel) &&
                Objects.equals(analysisName, that.analysisName) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(dataSourceId, that.dataSourceId) &&
                Objects.equals(dataSourceName, that.dataSourceName) &&
                Objects.equals(defaultOccurrenceBasis, that.defaultOccurrenceBasis) &&
                Objects.equals(description, that.description) &&
                Objects.equals(exchangeRate, that.exchangeRate) &&
                Objects.equals(financialPerspective, that.financialPerspective) &&
                Objects.equals(geoCode, that.geoCode) &&
                Objects.equals(grain, that.grain) &&
                Objects.equals(grouped, that.grouped) &&
                Objects.equals(importStatus, that.importStatus) &&
                Objects.equals(importedDate, that.importedDate) &&
                Objects.equals(includedTargetRapIds0, that.includedTargetRapIds0) &&
                Objects.equals(lossAmplification, that.lossAmplification) &&
                Objects.equals(model, that.model) &&
                Objects.equals(modelingResultDataSourceId, that.modelingResultDataSourceId) &&
                Objects.equals(overrideReasonText, that.overrideReasonText) &&
                Objects.equals(peril, that.peril) &&
                Objects.equals(profileKey, that.profileKey) &&
                Objects.equals(profileName, that.profileName) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(projectImportRunId, that.projectImportRunId) &&
                Objects.equals(proportion, that.proportion) &&
                Objects.equals(region, that.region) &&
                Objects.equals(regionPeril, that.regionPeril) &&
                Objects.equals(runDate, that.runDate) &&
                Objects.equals(sourceCurrency, that.sourceCurrency) &&
                Objects.equals(sourceEntitylingSystem, that.sourceEntitylingSystem) &&
                Objects.equals(sourceEntitylingSystemInstance, that.sourceEntitylingSystemInstance) &&
                Objects.equals(sourceEntitylingSystemVersion, that.sourceEntitylingSystemVersion) &&
                Objects.equals(sourceEntitylingVendor, that.sourceEntitylingVendor) &&
                Objects.equals(subPeril, that.subPeril) &&
                Objects.equals(tags, that.tags) &&
                Objects.equals(targetCurrency, that.targetCurrency) &&
                Objects.equals(targetCurrencyBasis, that.targetCurrencyBasis) &&
                Objects.equals(unitMultiplier, that.unitMultiplier) &&
                Objects.equals(userNotes, that.userNotes) &&
                Objects.equals(userOccurrenceBasis, that.userOccurrenceBasis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, analysisId, analysisLevel, analysisName, creationDate, dataSourceId, dataSourceName, defaultOccurrenceBasis, description, exchangeRate, financialPerspective, geoCode, grain, grouped, importStatus, importedDate, includedTargetRapIds0, lossAmplification, model, modelingResultDataSourceId, overrideReasonText, peril, profileKey, profileName, projectId, projectImportRunId, proportion, region, regionPeril, runDate, sourceCurrency, sourceEntitylingSystem, sourceEntitylingSystemInstance, sourceEntitylingSystemVersion, sourceEntitylingVendor, subPeril, tags, targetCurrency, targetCurrencyBasis, unitMultiplier, userNotes, userOccurrenceBasis);
    }
}
