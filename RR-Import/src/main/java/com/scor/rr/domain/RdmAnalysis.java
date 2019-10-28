package com.scor.rr.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RdmAnalysis {

    private Long rdmId;
    private String rdmName;
    private Long analysisId;
    private String analysisName;
    private String description;
    private String defaultGrain;
    private String exposureType;
    private int exposureTypeCode;
    private String edmNameSourceLink;
    private Long exposureId;
    private String analysisCurrency;
    private BigDecimal rmsExchangeRate;
    private int typeCode;
    private String analysisType;
    private String runDate;
    private String region;
    private String peril;
    private String rpCode;
    private String subPeril;
    private String lossAmplification;
    private Long status;
    private int analysisMode;
    private int engineTypeCode;
    private String engineType;
    private String engineVersion;
    private String engineVersionMajor;
    private String profileName;
    private String profileKey;
    private Boolean hasMultiRegionPerils;
    private Boolean validForExtract;
    private String notValidReason;
    private BigDecimal purePremium;
    private double exposureTiv;
    private String geoCode;
    private String geoDescription;
    private String user1;
    private String user2;
    private String user3;
    private String user4;

    public Long getRdmId() {
        return rdmId;
    }

    public void setRdmId(Long rdmId) {
        this.rdmId = rdmId;
    }

    public String getRdmName() {
        return rdmName;
    }

    public void setRdmName(String rdmName) {
        this.rdmName = rdmName;
    }

    public Long getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(Long analysisId) {
        this.analysisId = analysisId;
    }

    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultGrain() {
        return defaultGrain;
    }

    public void setDefaultGrain(String defaultGrain) {
        this.defaultGrain = defaultGrain;
    }

    public String getExposureType() {
        return exposureType;
    }

    public void setExposureType(String exposureType) {
        this.exposureType = exposureType;
    }

    public int getExposureTypeCode() {
        return exposureTypeCode;
    }

    public void setExposureTypeCode(int exposureTypeCode) {
        this.exposureTypeCode = exposureTypeCode;
    }

    public String getEdmNameSourceLink() {
        return edmNameSourceLink;
    }

    public void setEdmNameSourceLink(String edmNameSourceLink) {
        this.edmNameSourceLink = edmNameSourceLink;
    }

    public Long getExposureId() {
        return exposureId;
    }

    public void setExposureId(Long exposureId) {
        this.exposureId = exposureId;
    }

    public String getAnalysisCurrency() {
        return analysisCurrency;
    }

    public void setAnalysisCurrency(String analysisCurrency) {
        this.analysisCurrency = analysisCurrency;
    }

    public BigDecimal getRmsExchangeRate() {
        return rmsExchangeRate;
    }

    public void setRmsExchangeRate(BigDecimal rmsExchangeRate) {
        this.rmsExchangeRate = rmsExchangeRate;
    }

    public int getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(int typeCode) {
        this.typeCode = typeCode;
    }

    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
    }

    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    public String getRpCode() {
        return rpCode;
    }

    public void setRpCode(String rpCode) {
        this.rpCode = rpCode;
    }

    public String getSubPeril() {
        return subPeril;
    }

    public void setSubPeril(String subPeril) {
        this.subPeril = subPeril;
    }

    public String getLossAmplification() {
        return lossAmplification;
    }

    public void setLossAmplification(String lossAmplification) {
        this.lossAmplification = lossAmplification;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public int getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(int analysisMode) {
        this.analysisMode = analysisMode;
    }

    public int getEngineTypeCode() {
        return engineTypeCode;
    }

    public void setEngineTypeCode(int engineTypeCode) {
        this.engineTypeCode = engineTypeCode;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    public String getEngineVersionMajor() {
        return engineVersionMajor;
    }

    public void setEngineVersionMajor(String engineVersionMajor) {
        this.engineVersionMajor = engineVersionMajor;
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    public Boolean getHasMultiRegionPerils() {
        return hasMultiRegionPerils;
    }

    public void setHasMultiRegionPerils(Boolean hasMultiRegionPerils) {
        this.hasMultiRegionPerils = hasMultiRegionPerils;
    }

    public Boolean getValidForExtract() {
        return validForExtract;
    }

    public void setValidForExtract(Boolean validForExtract) {
        this.validForExtract = validForExtract;
    }

    public String getNotValidReason() {
        return notValidReason;
    }

    public void setNotValidReason(String notValidReason) {
        this.notValidReason = notValidReason;
    }

    public BigDecimal getPurePremium() {
        return purePremium;
    }

    public void setPurePremium(BigDecimal purePremium) {
        this.purePremium = purePremium;
    }

    public double getExposureTiv() {
        return exposureTiv;
    }

    public void setExposureTiv(double exposureTiv) {
        this.exposureTiv = exposureTiv;
    }

    public String getGeoCode() {
        return geoCode;
    }

    public void setGeoCode(String geoCode) {
        this.geoCode = geoCode;
    }

    public String getGeoDescription() {
        return geoDescription;
    }

    public void setGeoDescription(String geoDescription) {
        this.geoDescription = geoDescription;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public String getUser3() {
        return user3;
    }

    public void setUser3(String user3) {
        this.user3 = user3;
    }

    public String getUser4() {
        return user4;
    }

    public void setUser4(String user4) {
        this.user4 = user4;
    }
}
