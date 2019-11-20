package com.scor.rr.domain.views;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AnalysisDetailView {

    @Id
    @Column(name = "_id")
    private String id;
    @Column(name = "analysisId")
    private Double analysisId;
    @Column(name = "analysisLevel")
    private String analysisLevel;
    @Column(name = "analysisName")
    private String analysisName;
    @Column(name = "creationDate")
    private String creationDate;
    @Column(name = "dataSourceId")
    private Double dataSourceId;
    @Column(name = "dataSourceName")
    private String dataSourceName;
    @Column(name = "defaultOccurrenceBasis")
    private String defaultOccurrenceBasis;
    @Column(name = "description")
    private String description;
    @Column(name = "exchangeRate")
    private String exchangeRate;
    @Column(name = "financialPerspective")
    private String financialPerspective;
    @Column(name = "geoCode")
    private String geoCode;
    @Column(name = "grain")
    private String grain;
    @Column(name = "grouped")
    private String grouped;
    @Column(name = "importStatus")
    private String importStatus;
    @Column(name = "importedDate")
    private String importedDate;
    @Column(name = "includedTargetRapIds#0")
    private Double includedTargetRapIds0;
    @Column(name = "lossAmplification")
    private String lossAmplification;
    @Column(name = "model")
    private String model;
    @Column(name = "modelingResultDataSourceId")
    private String modelingResultDataSourceId;
    @Column(name = "overrideReasonText")
    private String overrideReasonText;
    @Column(name = "peril")
    private String peril;
    @Column(name = "profileKey")
    private String profileKey;
    @Column(name = "profileName")
    private String profileName;
    @Column(name = "projectId")
    private String projectId;
    @Column(name = "projectImportRunId")
    private String projectImportRunId;
    @Column(name = "proportion")
    private String proportion;
    @Column(name = "region")
    private String region;
    @Column(name = "regionPeril")
    private String regionPeril;
    @Column(name = "runDate")
    private String runDate;
    @Column(name = "sourceCurrency")
    private String sourceCurrency;
    @Column(name = "sourceModellingSystem")
    private String sourceModellingSystem;
    @Column(name = "sourceModellingSystemInstance")
    private String sourceModellingSystemInstance;
    @Column(name = "sourceModellingSystemVersion")
    private Double sourceModellingSystemVersion;
    @Column(name = "sourceModellingVendor")
    private String sourceModellingVendor;
    @Column(name = "subPeril")
    private String subPeril;
    @Column(name = "tags")
    private String tags;
    @Column(name = "targetCurrency")
    private String targetCurrency;
    @Column(name = "targetCurrencyBasis")
    private String targetCurrencyBasis;
    @Column(name = "unitMultiplier")
    private String unitMultiplier;
    @Column(name = "userNotes")
    private String userNotes;
    @Column(name = "userOccurrenceBasis")
    private String userOccurrenceBasis;
    @Column(name = "sourceEntitylingSystem")
    private String sourceEntitylingSystem;
    @Column(name = "sourceEntitylingSystemInstance")
    private String sourceEntitylingSystemInstance;
    @Column(name = "sourceEntitylingSystemVersion")
    private Double sourceEntitylingSystemVersion;
    @Column(name = "sourceEntitylingVendor")
    private String sourceEntitylingVendor;
    @Column(name = "isValidMinimumGrain")
    private String isValidMinimumGrain;


    public AnalysisDetailView() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Double getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Double analysisId) {
        this.analysisId = analysisId;
    }


    public String getAnalysisLevel() {
        return analysisLevel;
    }

    public void setAnalysisLevel(String analysisLevel) {
        this.analysisLevel = analysisLevel;
    }


    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }


    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }


    public Double getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(Double dataSourceId) {
        this.dataSourceId = dataSourceId;
    }


    public String getDataSourceName() {
        return dataSourceName;
    }

    public void setDataSourceName(String dataSourceName) {
        this.dataSourceName = dataSourceName;
    }


    public String getDefaultOccurrenceBasis() {
        return defaultOccurrenceBasis;
    }

    public void setDefaultOccurrenceBasis(String defaultOccurrenceBasis) {
        this.defaultOccurrenceBasis = defaultOccurrenceBasis;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }


    public String getFinancialPerspective() {
        return financialPerspective;
    }

    public void setFinancialPerspective(String financialPerspective) {
        this.financialPerspective = financialPerspective;
    }


    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }


    public String getGrain() {
        return grain;
    }

    public void setGrain(String grain) {
        this.grain = grain;
    }


    public String getGrouped() {
        return grouped;
    }

    public void setGrouped(String grouped) {
        this.grouped = grouped;
    }


    public String getImportStatus() {
        return importStatus;
    }

    public void setImportStatus(String importStatus) {
        this.importStatus = importStatus;
    }


    public String getImportedDate() {
        return importedDate;
    }

    public void setImportedDate(String importedDate) {
        this.importedDate = importedDate;
    }


    public Double getIncludedTargetRapIds0() {
        return includedTargetRapIds0;
    }

    public void setIncludedTargetRapIds0(Double includedTargetRapIds0) {
        this.includedTargetRapIds0 = includedTargetRapIds0;
    }


    public String getLossAmplification() {
        return lossAmplification;
    }

    public void setLossAmplification(String lossAmplification) {
        this.lossAmplification = lossAmplification;
    }


    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }


    public String getModelingResultDataSourceId() {
        return modelingResultDataSourceId;
    }

    public void setModelingResultDataSourceId(String modelingResultDataSourceId) {
        this.modelingResultDataSourceId = modelingResultDataSourceId;
    }


    public String getOverrideReasonText() {
        return overrideReasonText;
    }

    public void setOverrideReasonText(String overrideReasonText) {
        this.overrideReasonText = overrideReasonText;
    }


    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }


    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }


    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }


    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }


    public String getProjectImportRunId() {
        return projectImportRunId;
    }

    public void setProjectImportRunId(String projectImportRunId) {
        this.projectImportRunId = projectImportRunId;
    }


    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }


    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }


    public String getRegionPeril() {
        return regionPeril;
    }

    public void setRegionPeril(String regionPeril) {
        this.regionPeril = regionPeril;
    }


    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }


    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }


    public String getSourceModellingSystem() {
        return sourceModellingSystem;
    }

    public void setSourceModellingSystem(String sourceModellingSystem) {
        this.sourceModellingSystem = sourceModellingSystem;
    }


    public String getSourceModellingSystemInstance() {
        return sourceModellingSystemInstance;
    }

    public void setSourceModellingSystemInstance(String sourceModellingSystemInstance) {
        this.sourceModellingSystemInstance = sourceModellingSystemInstance;
    }


    public Double getSourceModellingSystemVersion() {
        return sourceModellingSystemVersion;
    }

    public void setSourceModellingSystemVersion(Double sourceModellingSystemVersion) {
        this.sourceModellingSystemVersion = sourceModellingSystemVersion;
    }


    public String getSourceModellingVendor() {
        return sourceModellingVendor;
    }

    public void setSourceModellingVendor(String sourceModellingVendor) {
        this.sourceModellingVendor = sourceModellingVendor;
    }


    public String getSubPeril() {
        return subPeril;
    }

    public void setSubPeril(String subPeril) {
        this.subPeril = subPeril;
    }


    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }


    public String getTargetCurrency() {
        return targetCurrency;
    }

    public void setTargetCurrency(String targetCurrency) {
        this.targetCurrency = targetCurrency;
    }


    public String getTargetCurrencyBasis() {
        return targetCurrencyBasis;
    }

    public void setTargetCurrencyBasis(String targetCurrencyBasis) {
        this.targetCurrencyBasis = targetCurrencyBasis;
    }


    public String getUnitMultiplier() {
        return unitMultiplier;
    }

    public void setUnitMultiplier(String unitMultiplier) {
        this.unitMultiplier = unitMultiplier;
    }


    public String getUserNotes() {
        return userNotes;
    }

    public void setUserNotes(String userNotes) {
        this.userNotes = userNotes;
    }


    public String getUserOccurrenceBasis() {
        return userOccurrenceBasis;
    }

    public void setUserOccurrenceBasis(String userOccurrenceBasis) {
        this.userOccurrenceBasis = userOccurrenceBasis;
    }


    public String getSourceEntitylingSystem() {
        return sourceEntitylingSystem;
    }

    public void setSourceEntitylingSystem(String sourceEntitylingSystem) {
        this.sourceEntitylingSystem = sourceEntitylingSystem;
    }


    public String getSourceEntitylingSystemInstance() {
        return sourceEntitylingSystemInstance;
    }

    public void setSourceEntitylingSystemInstance(String sourceEntitylingSystemInstance) {
        this.sourceEntitylingSystemInstance = sourceEntitylingSystemInstance;
    }


    public Double getSourceEntitylingSystemVersion() {
        return sourceEntitylingSystemVersion;
    }

    public void setSourceEntitylingSystemVersion(Double sourceEntitylingSystemVersion) {
        this.sourceEntitylingSystemVersion = sourceEntitylingSystemVersion;
    }


    public String getSourceEntitylingVendor() {
        return sourceEntitylingVendor;
    }

    public void setSourceEntitylingVendor(String sourceEntitylingVendor) {
        this.sourceEntitylingVendor = sourceEntitylingVendor;
    }


    public String getIsValidMinimumGrain() {
        return isValidMinimumGrain;
    }

    public void setIsValidMinimumGrain(String isValidMinimumGrain) {
        this.isValidMinimumGrain = isValidMinimumGrain;
    }

}
