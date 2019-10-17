package com.scor.rr.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RRAnalysisNEW", schema = "dbo", catalog = "RiskReveal")
public class RrAnalysisNewEntity {
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
    private String useres;
    private String overrideReasonText;
    private String resultName;
    private String sourceLossModellingBasis;
    private String sourceLossTableType;
    private String eventSet;
    private String modelModule;
    private String sourceResultsReference;
    private String subPeril;
    private String region;
    private String profileName;
    private String occurrenceBasisOverrideReason;
    private String occurenceBasisOverridenBy;
    private String metadata;
    private String sourceEntitylingSystem;
    private String sourceEntitylingSystemInstance;
    private String sourceEntitylingSystemVersion;
    private String sourceEntitylingVendor;
    private String sourceLossEntitylingBasis;
    private ProjectImportRunEntity projectImportRunByProjectImportRunId;

    @Id
    @Column(name = "rrAnalysisId", nullable = false)
    public int getRrAnalysisId() {
        return rrAnalysisId;
    }

    public void setRrAnalysisId(int rrAnalysisId) {
        this.rrAnalysisId = rrAnalysisId;
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
    @Column(name = "importedDate", nullable = true)
    public Timestamp getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(Timestamp importedDate) {
        this.importedDate = importedDate;
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
    @Column(name = "runDate", nullable = true)
    public Timestamp getRunDate() {
        return runDate;
    }

    public void setRunDate(Timestamp runDate) {
        this.runDate = runDate;
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
    @Column(name = "sourceModellingSystemInstance", nullable = true, length = 255)
    public String getSourceModellingSystemInstance() {
        return sourceModellingSystemInstance;
    }

    public void setSourceModellingSystemInstance(String sourceModellingSystemInstance) {
        this.sourceModellingSystemInstance = sourceModellingSystemInstance;
    }

    @Basic
    @Column(name = "sourceModellingVendor", nullable = true, length = 255)
    public String getSourceModellingVendor() {
        return sourceModellingVendor;
    }

    public void setSourceModellingVendor(String sourceModellingVendor) {
        this.sourceModellingVendor = sourceModellingVendor;
    }

    @Basic
    @Column(name = "sourceModellingSystem", nullable = true, length = 255)
    public String getSourceModellingSystem() {
        return sourceModellingSystem;
    }

    public void setSourceModellingSystem(String sourceModellingSystem) {
        this.sourceModellingSystem = sourceModellingSystem;
    }

    @Basic
    @Column(name = "sourceModellingSystemVersion", nullable = true, length = 255)
    public String getSourceModellingSystemVersion() {
        return sourceModellingSystemVersion;
    }

    public void setSourceModellingSystemVersion(String sourceModellingSystemVersion) {
        this.sourceModellingSystemVersion = sourceModellingSystemVersion;
    }

    @Basic
    @Column(name = "dataSourceId", nullable = true)
    public Long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Long dataSourceId) {
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
    @Column(name = "fileName", nullable = true, length = 255)
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Basic
    @Column(name = "analysisId", nullable = true)
    public Integer getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Integer analysisId) {
        this.analysisId = analysisId;
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
    @Column(name = "grain", nullable = true, length = 255)
    public String getGrain() {
        return grain;
    }

    public void setGrain(String grain) {
        this.grain = grain;
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
    @Column(name = "treatyLabel", nullable = true, length = 255)
    public String getTreatyLabel() {
        return treatyLabel;
    }

    public void setTreatyLabel(String treatyLabel) {
        this.treatyLabel = treatyLabel;
    }

    @Basic
    @Column(name = "treatyTag", nullable = true, length = 255)
    public String getTreatyTag() {
        return treatyTag;
    }

    public void setTreatyTag(String treatyTag) {
        this.treatyTag = treatyTag;
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
    @Column(name = "geoCode", nullable = true, length = 255)
    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
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
    @Column(name = "sourceCurrency", nullable = true, length = 255)
    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
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
    @Column(name = "exchangeRate", nullable = true, precision = 7)
    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
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
    @Column(name = "userOccurrenceBasis", nullable = true, length = 255)
    public String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }

    @Basic
    @Column(name = "proportion", nullable = true, precision = 7)
    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    @Basic
    @Column(name = "proxyScalingBasis", nullable = true, length = 255)
    public String getProxyScalingBasis() {
        return proxyScalingBasis;
    }

    public void setProxyScalingBasis(String proxyScalingBasis) {
        this.proxyScalingBasis = proxyScalingBasis;
    }

    @Basic
    @Column(name = "proxyScalingNarrative", nullable = true, length = 255)
    public String getProxyScalingNarrative() {
        return proxyScalingNarrative;
    }

    public void setProxyScalingNarrative(String proxyScalingNarrative) {
        this.proxyScalingNarrative = proxyScalingNarrative;
    }

    @Basic
    @Column(name = "unitMultiplier", nullable = true, precision = 7)
    public BigDecimal getUnitMultiplier() {
        return unitMultiplier;
    }

    public void setUnitMultiplier(BigDecimal unitMultiplier) {
        this.unitMultiplier = unitMultiplier;
    }

    @Basic
    @Column(name = "multiplierBasis", nullable = true, length = 255)
    public String getMultiplierBasis() {
        return multiplierBasis;
    }

    public void setMultiplierBasis(String multiplierBasis) {
        this.multiplierBasis = multiplierBasis;
    }

    @Basic
    @Column(name = "multiplierNarrative", nullable = true, length = 255)
    public String getMultiplierNarrative() {
        return multiplierNarrative;
    }

    public void setMultiplierNarrative(String multiplierNarrative) {
        this.multiplierNarrative = multiplierNarrative;
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
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
    @Column(name = "lossAmplification", nullable = true, length = 255)
    public String getLossAmplification() {
        return lossAmplification;
    }

    public void setLossAmplification(String lossAmplification) {
        this.lossAmplification = lossAmplification;
    }

    @Basic
    @Column(name = "model", nullable = true, length = 255)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
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
    @Column(name = "useres", nullable = true, length = 255)
    public String getUseres() {
        return useres;
    }

    public void setUseres(String useres) {
        this.useres = useres;
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
    @Column(name = "resultName", nullable = true, length = 255)
    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    @Basic
    @Column(name = "sourceLossModellingBasis", nullable = true, length = 255)
    public String getSourceLossModellingBasis() {
        return sourceLossModellingBasis;
    }

    public void setSourceLossModellingBasis(String sourceLossModellingBasis) {
        this.sourceLossModellingBasis = sourceLossModellingBasis;
    }

    @Basic
    @Column(name = "sourceLossTableType", nullable = true, length = 255)
    public String getSourceLossTableType() {
        return sourceLossTableType;
    }

    public void setSourceLossTableType(String sourceLossTableType) {
        this.sourceLossTableType = sourceLossTableType;
    }

    @Basic
    @Column(name = "eventSet", nullable = true, length = 255)
    public String getEventSet() {
        return eventSet;
    }

    public void setEventSet(String eventSet) {
        this.eventSet = eventSet;
    }

    @Basic
    @Column(name = "modelModule", nullable = true, length = 255)
    public String getModelModule() {
        return modelModule;
    }

    public void setModelModule(String modelModule) {
        this.modelModule = modelModule;
    }

    @Basic
    @Column(name = "sourceResultsReference", nullable = true, length = 255)
    public String getSourceResultsReference() {
        return sourceResultsReference;
    }

    public void setSourceResultsReference(String sourceResultsReference) {
        this.sourceResultsReference = sourceResultsReference;
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
    @Column(name = "region", nullable = true, length = 255)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
    @Column(name = "occurrenceBasisOverrideReason", nullable = true, length = 255)
    public String getOccurrenceBasisOverrideReason() {
        return occurrenceBasisOverrideReason;
    }

    public void setOccurrenceBasisOverrideReason(String occurrenceBasisOverrideReason) {
        this.occurrenceBasisOverrideReason = occurrenceBasisOverrideReason;
    }

    @Basic
    @Column(name = "occurenceBasisOverridenBy", nullable = true, length = 255)
    public String getOccurenceBasisOverridenBy() {
        return occurenceBasisOverridenBy;
    }

    public void setOccurenceBasisOverridenBy(String occurenceBasisOverridenBy) {
        this.occurenceBasisOverridenBy = occurenceBasisOverridenBy;
    }

    @Basic
    @Column(name = "metadata", nullable = true, length = -1)
    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
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
    @Column(name = "sourceEntitylingSystemVersion", nullable = true, length = 255)
    public String getSourceEntitylingSystemVersion() {
        return sourceEntitylingSystemVersion;
    }

    public void setSourceEntitylingSystemVersion(String sourceEntitylingSystemVersion) {
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
    @Column(name = "sourceLossEntitylingBasis", nullable = true, length = 255)
    public String getSourceLossEntitylingBasis() {
        return sourceLossEntitylingBasis;
    }

    public void setSourceLossEntitylingBasis(String sourceLossEntitylingBasis) {
        this.sourceLossEntitylingBasis = sourceLossEntitylingBasis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrAnalysisNewEntity that = (RrAnalysisNewEntity) o;
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
                Objects.equals(useres, that.useres) &&
                Objects.equals(overrideReasonText, that.overrideReasonText) &&
                Objects.equals(resultName, that.resultName) &&
                Objects.equals(sourceLossModellingBasis, that.sourceLossModellingBasis) &&
                Objects.equals(sourceLossTableType, that.sourceLossTableType) &&
                Objects.equals(eventSet, that.eventSet) &&
                Objects.equals(modelModule, that.modelModule) &&
                Objects.equals(sourceResultsReference, that.sourceResultsReference) &&
                Objects.equals(subPeril, that.subPeril) &&
                Objects.equals(region, that.region) &&
                Objects.equals(profileName, that.profileName) &&
                Objects.equals(occurrenceBasisOverrideReason, that.occurrenceBasisOverrideReason) &&
                Objects.equals(occurenceBasisOverridenBy, that.occurenceBasisOverridenBy) &&
                Objects.equals(metadata, that.metadata) &&
                Objects.equals(sourceEntitylingSystem, that.sourceEntitylingSystem) &&
                Objects.equals(sourceEntitylingSystemInstance, that.sourceEntitylingSystemInstance) &&
                Objects.equals(sourceEntitylingSystemVersion, that.sourceEntitylingSystemVersion) &&
                Objects.equals(sourceEntitylingVendor, that.sourceEntitylingVendor) &&
                Objects.equals(sourceLossEntitylingBasis, that.sourceLossEntitylingBasis);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rrAnalysisId, projectId, importedDate, creationDate, runDate, importStatus, sourceModellingSystemInstance, sourceModellingVendor, sourceModellingSystem, sourceModellingSystemVersion, dataSourceId, dataSourceName, fileName, analysisId, analysisName, grain, financialPerspective, treatyLabel, treatyTag, peril, geoCode, regionPeril, sourceCurrency, targetCurrency, targetCurrencyBasis, exchangeRate, defaultOccurrenceBasis, userOccurrenceBasis, proportion, proxyScalingBasis, proxyScalingNarrative, unitMultiplier, multiplierBasis, multiplierNarrative, profileKey, description, analysisLevel, lossAmplification, model, tags, useres, overrideReasonText, resultName, sourceLossModellingBasis, sourceLossTableType, eventSet, modelModule, sourceResultsReference, subPeril, region, profileName, occurrenceBasisOverrideReason, occurenceBasisOverridenBy, metadata, sourceEntitylingSystem, sourceEntitylingSystemInstance, sourceEntitylingSystemVersion, sourceEntitylingVendor, sourceLossEntitylingBasis);
    }

    @ManyToOne
    @JoinColumn(name = "projectImportRunId", referencedColumnName = "ProjectImportRunId")
    public ProjectImportRunEntity getProjectImportRunByProjectImportRunId() {
        return projectImportRunByProjectImportRunId;
    }

    public void setProjectImportRunByProjectImportRunId(ProjectImportRunEntity projectImportRunByProjectImportRunId) {
        this.projectImportRunByProjectImportRunId = projectImportRunByProjectImportRunId;
    }
}
