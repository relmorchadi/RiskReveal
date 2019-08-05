package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RmsAnalysis", schema = "dbo", catalog = "RiskReveal")
public class RmsAnalysisEntity {
    private String lobName;
    private String statusDescription;
    private String analysisCurrency;
    private int analysisId;
    private String analysisName;
    private String cedant;
    private String description;
    private String engineType;
    private String engineVersion;
    private String groupTypeName;
    private String grouping;
    private String lossAmplification;
    private String modeName;
    private String peril;
    private Integer rdmId;
    private String rdmName;
    private String region;
    private String regionName;
    private String runDate;
    private String subPeril;
    private String typeName;
    private String user1;
    private String user2;
    private String user3;
    private String user4;

    @Basic
    @Column(name = "LobName", length = 255,insertable = false ,updatable = false)
    public String getLobName() {
        return lobName;
    }

    public void setLobName(String lobName) {
        this.lobName = lobName;
    }

    @Basic
    @Column(name = "StatusDescription", length = 255,insertable = false ,updatable = false)
    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    @Basic
    @Column(name = "analysisCurrency", length = 255,insertable = false ,updatable = false)
    public String getAnalysisCurrency() {
        return analysisCurrency;
    }

    public void setAnalysisCurrency(String analysisCurrency) {
        this.analysisCurrency = analysisCurrency;
    }

    @Id
    @Column(name = "analysisId", nullable = false)
    public int getAnalysisId() {
        return analysisId;
    }

    public void setAnalysisId(int analysisId) {
        this.analysisId = analysisId;
    }

    @Basic
    @Column(name = "analysisName", length = 255,insertable = false ,updatable = false)
    public String getAnalysisName() {
        return analysisName;
    }

    public void setAnalysisName(String analysisName) {
        this.analysisName = analysisName;
    }

    @Basic
    @Column(name = "cedant", length = 255,insertable = false ,updatable = false)
    public String getCedant() {
        return cedant;
    }

    public void setCedant(String cedant) {
        this.cedant = cedant;
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
    @Column(name = "engineType", length = 255,insertable = false ,updatable = false)
    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    @Basic
    @Column(name = "engineVersion", length = 255,insertable = false ,updatable = false)
    public String getEngineVersion() {
        return engineVersion;
    }

    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    @Basic
    @Column(name = "groupTypeName", length = 255,insertable = false ,updatable = false)
    public String getGroupTypeName() {
        return groupTypeName;
    }

    public void setGroupTypeName(String groupTypeName) {
        this.groupTypeName = groupTypeName;
    }

    @Basic
    @Column(name = "grouping", length = 255,insertable = false ,updatable = false)
    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    @Basic
    @Column(name = "lossAmplification", length = 255,insertable = false ,updatable = false)
    public String getLossAmplification() {
        return lossAmplification;
    }

    public void setLossAmplification(String lossAmplification) {
        this.lossAmplification = lossAmplification;
    }

    @Basic
    @Column(name = "modeName", length = 255,insertable = false ,updatable = false)
    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    @Basic
    @Column(name = "peril", length = 255,insertable = false ,updatable = false)
    public String getPeril() {
        return peril;
    }

    public void setPeril(String peril) {
        this.peril = peril;
    }

    @Basic
    @Column(name = "rdmId")
    public Integer getRdmId() {
        return rdmId;
    }

    public void setRdmId(Integer rdmId) {
        this.rdmId = rdmId;
    }

    @Basic
    @Column(name = "rdmName", length = 255,insertable = false ,updatable = false)
    public String getRdmName() {
        return rdmName;
    }

    public void setRdmName(String rdmName) {
        this.rdmName = rdmName;
    }

    @Basic
    @Column(name = "region", length = 255,insertable = false ,updatable = false)
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "regionName", length = 255,insertable = false ,updatable = false)
    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Basic
    @Column(name = "runDate", length = 255,insertable = false ,updatable = false)
    public String getRunDate() {
        return runDate;
    }

    public void setRunDate(String runDate) {
        this.runDate = runDate;
    }

    @Basic
    @Column(name = "subPeril", length = 255,insertable = false ,updatable = false)
    public String getSubPeril() {
        return subPeril;
    }

    public void setSubPeril(String subPeril) {
        this.subPeril = subPeril;
    }

    @Basic
    @Column(name = "typeName", length = 255,insertable = false ,updatable = false)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Basic
    @Column(name = "user1", length = 255,insertable = false ,updatable = false)
    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    @Basic
    @Column(name = "user2", length = 255,insertable = false ,updatable = false)
    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    @Basic
    @Column(name = "user3", length = 255,insertable = false ,updatable = false)
    public String getUser3() {
        return user3;
    }

    public void setUser3(String user3) {
        this.user3 = user3;
    }

    @Basic
    @Column(name = "user4", length = 255,insertable = false ,updatable = false)
    public String getUser4() {
        return user4;
    }

    public void setUser4(String user4) {
        this.user4 = user4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RmsAnalysisEntity that = (RmsAnalysisEntity) o;
        return analysisId == that.analysisId &&
                Objects.equals(lobName, that.lobName) &&
                Objects.equals(statusDescription, that.statusDescription) &&
                Objects.equals(analysisCurrency, that.analysisCurrency) &&
                Objects.equals(analysisName, that.analysisName) &&
                Objects.equals(cedant, that.cedant) &&
                Objects.equals(description, that.description) &&
                Objects.equals(engineType, that.engineType) &&
                Objects.equals(engineVersion, that.engineVersion) &&
                Objects.equals(groupTypeName, that.groupTypeName) &&
                Objects.equals(grouping, that.grouping) &&
                Objects.equals(lossAmplification, that.lossAmplification) &&
                Objects.equals(modeName, that.modeName) &&
                Objects.equals(peril, that.peril) &&
                Objects.equals(rdmId, that.rdmId) &&
                Objects.equals(rdmName, that.rdmName) &&
                Objects.equals(region, that.region) &&
                Objects.equals(regionName, that.regionName) &&
                Objects.equals(runDate, that.runDate) &&
                Objects.equals(subPeril, that.subPeril) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(user1, that.user1) &&
                Objects.equals(user2, that.user2) &&
                Objects.equals(user3, that.user3) &&
                Objects.equals(user4, that.user4);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lobName, statusDescription, analysisCurrency, analysisId, analysisName, cedant, description, engineType, engineVersion, groupTypeName, grouping, lossAmplification, modeName, peril, rdmId, rdmName, region, regionName, runDate, subPeril, typeName, user1, user2, user3, user4);
    }
}
