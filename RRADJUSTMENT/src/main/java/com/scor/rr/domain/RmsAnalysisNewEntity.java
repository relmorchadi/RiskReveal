package com.scor.rr.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "RmsAnalysisNEW", schema = "dbo", catalog = "RiskReveal")
public class RmsAnalysisNewEntity {
    private int rmsAnalysisId;
    private Integer projectId;
    private BigDecimal rdmId;
    private String rmdName;
    private BigDecimal analysisId;
    private String analysisName;
    private String description;
    private String defaultGrain;
    private String exposureType;
    private Integer exposureTypeCode;
    private String edmNameSourceLink;
    private BigDecimal exposureId;
    private String anakysisCurrency;
    private BigDecimal rmsExchangeRate;
    private Integer typeCode;
    private String analysisType;
    private Timestamp runDate;
    private String region;
    private String peril;
    private String rpCode;
    private String subPeril;
    private String lossAmplification;
    private BigDecimal status;
    private Integer analysisMode;
    private Integer engineTypeCode;
    private String engineType;
    private String engineVersion;
    private String engineVersionMajor;
    private String profileName;
    private String profileKey;
    private BigDecimal purePremium;
    private BigDecimal exposureTiv;

    @Id
    @Column(name = "rmsAnalysisId", nullable = false)
    public int getRmsAnalysisId() {
        return rmsAnalysisId;
    }

    public void setRmsAnalysisId(int rmsAnalysisId) {
        this.rmsAnalysisId = rmsAnalysisId;
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
    @Column(name = "rdmId", nullable = true, precision = 7)
    public BigDecimal getRdmId() {
        return rdmId;
    }

    public void setRdmId(BigDecimal rdmId) {
        this.rdmId = rdmId;
    }

    @Basic
    @Column(name = "rmdName", nullable = true, length = 255)
    public String getRmdName() {
        return rmdName;
    }

    public void setRmdName(String rmdName) {
        this.rmdName = rmdName;
    }

    @Basic
    @Column(name = "analysisId", nullable = true, precision = 7)
    public BigDecimal getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(BigDecimal analysisId) {
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
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "defaultGrain", nullable = true, length = 255)
    public String getDefaultGrain() {
        return defaultGrain;
    }

    public void setDefaultGrain(String defaultGrain) {
        this.defaultGrain = defaultGrain;
    }

    @Basic
    @Column(name = "exposureType", nullable = true, length = 255)
    public String getExposureType() {
        return exposureType;
    }

    public void setExposureType(String exposureType) {
        this.exposureType = exposureType;
    }

    @Basic
    @Column(name = "exposureTypeCode", nullable = true)
    public Integer getExposureTypeCode() {
        return exposureTypeCode;
    }

    public void setExposureTypeCode(Integer exposureTypeCode) {
        this.exposureTypeCode = exposureTypeCode;
    }

    @Basic
    @Column(name = "edmNameSourceLink", nullable = true, length = 255)
    public String getEdmNameSourceLink() {
        return edmNameSourceLink;
    }

    public void setEdmNameSourceLink(String edmNameSourceLink) {
        this.edmNameSourceLink = edmNameSourceLink;
    }

    @Basic
    @Column(name = "exposureId", nullable = true, precision = 7)
    public BigDecimal getExposureId() {
        return exposureId;
    }

    public void setExposureId(BigDecimal exposureId) {
        this.exposureId = exposureId;
    }

    @Basic
    @Column(name = "anakysisCurrency", nullable = true, length = 255)
    public String getAnakysisCurrency() {
        return anakysisCurrency;
    }

    public void setAnakysisCurrency(String anakysisCurrency) {
        this.anakysisCurrency = anakysisCurrency;
    }

    @Basic
    @Column(name = "rmsExchangeRate", nullable = true, precision = 7)
    public BigDecimal getRmsExchangeRate() {
        return rmsExchangeRate;
    }

    public void setRmsExchangeRate(BigDecimal rmsExchangeRate) {
        this.rmsExchangeRate = rmsExchangeRate;
    }

    @Basic
    @Column(name = "typeCode", nullable = true)
    public Integer getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(Integer typeCode) {
        this.typeCode = typeCode;
    }

    @Basic
    @Column(name = "analysisType", nullable = true, length = 255)
    public String getAnalysisType() {
        return analysisType;
    }

    public void setAnalysisType(String analysisType) {
        this.analysisType = analysisType;
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
    @Column(name = "region", nullable = true, length = 255)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
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
    @Column(name = "rpCode", nullable = true, length = 255)
    public String getRpCode() {
        return rpCode;
    }

    public void setRpCode(String rpCode) {
        this.rpCode = rpCode;
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
    @Column(name = "lossAmplification", nullable = true, length = 255)
    public String getLossAmplification() {
        return lossAmplification;
    }

    public void setLossAmplification(String lossAmplification) {
        this.lossAmplification = lossAmplification;
    }

    @Basic
    @Column(name = "status", nullable = true, precision = 7)
    public BigDecimal getStatus() {
        return status;
    }

    public void setStatus(BigDecimal status) {
        this.status = status;
    }

    @Basic
    @Column(name = "analysisMode", nullable = true)
    public Integer getAnalysisMode() {
        return analysisMode;
    }

    public void setAnalysisMode(Integer analysisMode) {
        this.analysisMode = analysisMode;
    }

    @Basic
    @Column(name = "engineTypeCode", nullable = true)
    public Integer getEngineTypeCode() {
        return engineTypeCode;
    }

    public void setEngineTypeCode(Integer engineTypeCode) {
        this.engineTypeCode = engineTypeCode;
    }

    @Basic
    @Column(name = "engineType", nullable = true, length = 255)
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Basic
    @Column(name = "engineVersion", nullable = true, length = 255)
    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    @Basic
    @Column(name = "engineVersionMajor", nullable = true, length = 255)
    public String getEngineVersionMajor() {
        return engineVersionMajor;
    }

    public void setEngineVersionMajor(String engineVersionMajor) {
        this.engineVersionMajor = engineVersionMajor;
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
    @Column(name = "profileKey", nullable = true, length = 255)
    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    @Basic
    @Column(name = "purePremium", nullable = true, precision = 7)
    public BigDecimal getPurePremium() {
        return purePremium;
    }

    public void setPurePremium(BigDecimal purePremium) {
        this.purePremium = purePremium;
    }

    @Basic
    @Column(name = "exposureTiv", nullable = true, precision = 7)
    public BigDecimal getExposureTiv() {
        return exposureTiv;
    }

    public void setExposureTiv(BigDecimal exposureTiv) {
        this.exposureTiv = exposureTiv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RmsAnalysisNewEntity that = (RmsAnalysisNewEntity) o;
        return rmsAnalysisId == that.rmsAnalysisId &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(rdmId, that.rdmId) &&
                Objects.equals(rmdName, that.rmdName) &&
                Objects.equals(analysisId, that.analysisId) &&
                Objects.equals(analysisName, that.analysisName) &&
                Objects.equals(description, that.description) &&
                Objects.equals(defaultGrain, that.defaultGrain) &&
                Objects.equals(exposureType, that.exposureType) &&
                Objects.equals(exposureTypeCode, that.exposureTypeCode) &&
                Objects.equals(edmNameSourceLink, that.edmNameSourceLink) &&
                Objects.equals(exposureId, that.exposureId) &&
                Objects.equals(anakysisCurrency, that.anakysisCurrency) &&
                Objects.equals(rmsExchangeRate, that.rmsExchangeRate) &&
                Objects.equals(typeCode, that.typeCode) &&
                Objects.equals(analysisType, that.analysisType) &&
                Objects.equals(runDate, that.runDate) &&
                Objects.equals(region, that.region) &&
                Objects.equals(peril, that.peril) &&
                Objects.equals(rpCode, that.rpCode) &&
                Objects.equals(subPeril, that.subPeril) &&
                Objects.equals(lossAmplification, that.lossAmplification) &&
                Objects.equals(status, that.status) &&
                Objects.equals(analysisMode, that.analysisMode) &&
                Objects.equals(engineTypeCode, that.engineTypeCode) &&
                Objects.equals(engineType, that.engineType) &&
                Objects.equals(engineVersion, that.engineVersion) &&
                Objects.equals(engineVersionMajor, that.engineVersionMajor) &&
                Objects.equals(profileName, that.profileName) &&
                Objects.equals(profileKey, that.profileKey) &&
                Objects.equals(purePremium, that.purePremium) &&
                Objects.equals(exposureTiv, that.exposureTiv);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rmsAnalysisId, projectId, rdmId, rmdName, analysisId, analysisName, description, defaultGrain, exposureType, exposureTypeCode, edmNameSourceLink, exposureId, anakysisCurrency, rmsExchangeRate, typeCode, analysisType, runDate, region, peril, rpCode, subPeril, lossAmplification, status, analysisMode, engineTypeCode, engineType, engineVersion, engineVersionMajor, profileName, profileKey, purePremium, exposureTiv);
    }
}
