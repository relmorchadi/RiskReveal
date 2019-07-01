package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ContractSearchResult", schema = "dbo", catalog = "RiskReveal")
public class ContractSearchResultEntity {
    private String workSpaceId;
    private String workspaceName;
    private String id;
    private Integer sectionid;
    private String sectionLabel;
    private Boolean isActive;
    private Integer uwYear;
    private String treatyid;
    private String treatyName;
    private String contractStatusid;
    private String contractStatus;
    private String sectionStatusid;
    private String sectionStatus;
    private String accumulationPublicationStatus;
    private String bouquetid;
    private String bouquetName;
    private String programid;
    private String programName;
    private String countryCode;
    private String countryName;
    private String cedantName;
    private String cedantCode;
    private Boolean isEq;
    private Boolean isWs;
    private Boolean isFl;
    private Timestamp inceptionDate;
    private Timestamp expiryDate;
    private String earliestDueDate;
    private String uwUnitid;
    private String uwUnitLabel;
    private String contractNatureType;
    private String underwriterid;
    private String underwriterName;
    private Integer subsidiaryid;
    private String subsidiaryName;
    private String subsidiaryLedgerid;
    private String subsidiaryLedgerName;
    private String loBid;
    private String lobName;
    private String soBid;
    private String sobName;
    private String typeOfPolicyid;
    private String typeOfPolicyName;
    private String natureid;
    private String natureName;
    private String liabilityCurrencyid;
    private String contractSourceTypeName;
    private Timestamp lastScopeImpactDate;
    private Timestamp lastSyncRunCatDomain;

    @Id
    @Basic
    @Column(name = "WorkSpaceId", nullable = true, length = 255)
    public String getWorkSpaceId() {
        return workSpaceId;
    }

    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }

    @Basic
    @Column(name = "WorkspaceName", nullable = true, length = 255)
    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    @Basic
    @Column(name = "id", nullable = true, length = 255)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Sectionid", nullable = true)
    public Integer getSectionid() {
        return sectionid;
    }

    public void setSectionid(Integer sectionid) {
        this.sectionid = sectionid;
    }

    @Basic
    @Column(name = "SectionLabel", nullable = true, length = 255)
    public String getSectionLabel() {
        return sectionLabel;
    }

    public void setSectionLabel(String sectionLabel) {
        this.sectionLabel = sectionLabel;
    }

    @Basic
    @Column(name = "IsActive", nullable = true)
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @Basic
    @Column(name = "UwYear", nullable = true)
    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
    }

    @Basic
    @Column(name = "Treatyid", nullable = true, length = 255)
    public String getTreatyid() {
        return treatyid;
    }

    public void setTreatyid(String treatyid) {
        this.treatyid = treatyid;
    }

    @Basic
    @Column(name = "TreatyName", nullable = true, length = 255)
    public String getTreatyName() {
        return treatyName;
    }

    public void setTreatyName(String treatyName) {
        this.treatyName = treatyName;
    }

    @Basic
    @Column(name = "ContractStatusid", nullable = true, length = 255)
    public String getContractStatusid() {
        return contractStatusid;
    }

    public void setContractStatusid(String contractStatusid) {
        this.contractStatusid = contractStatusid;
    }

    @Basic
    @Column(name = "ContractStatus", nullable = true, length = 255)
    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    @Basic
    @Column(name = "SectionStatusid", nullable = true, length = 255)
    public String getSectionStatusid() {
        return sectionStatusid;
    }

    public void setSectionStatusid(String sectionStatusid) {
        this.sectionStatusid = sectionStatusid;
    }

    @Basic
    @Column(name = "SectionStatus", nullable = true, length = 255)
    public String getSectionStatus() {
        return sectionStatus;
    }

    public void setSectionStatus(String sectionStatus) {
        this.sectionStatus = sectionStatus;
    }

    @Basic
    @Column(name = "AccumulationPublicationStatus", nullable = true, length = 1)
    public String getAccumulationPublicationStatus() {
        return accumulationPublicationStatus;
    }

    public void setAccumulationPublicationStatus(String accumulationPublicationStatus) {
        this.accumulationPublicationStatus = accumulationPublicationStatus;
    }

    @Basic
    @Column(name = "Bouquetid", nullable = true, length = 255)
    public String getBouquetid() {
        return bouquetid;
    }

    public void setBouquetid(String bouquetid) {
        this.bouquetid = bouquetid;
    }

    @Basic
    @Column(name = "BouquetName", nullable = true, length = 255)
    public String getBouquetName() {
        return bouquetName;
    }

    public void setBouquetName(String bouquetName) {
        this.bouquetName = bouquetName;
    }

    @Basic
    @Column(name = "Programid", nullable = true, length = 255)
    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }

    @Basic
    @Column(name = "ProgramName", nullable = true, length = 255)
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    @Basic
    @Column(name = "CountryCode", nullable = true, length = 255)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Basic
    @Column(name = "CountryName", nullable = true, length = 255)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "CedantName", nullable = true, length = 255)
    public String getCedantName() {
        return cedantName;
    }

    public void setCedantName(String cedantName) {
        this.cedantName = cedantName;
    }

    @Basic
    @Column(name = "CedantCode", nullable = true, length = 255)
    public String getCedantCode() {
        return cedantCode;
    }

    public void setCedantCode(String cedantCode) {
        this.cedantCode = cedantCode;
    }

    @Basic
    @Column(name = "IsEQ", nullable = true)
    public Boolean getEq() {
        return isEq;
    }

    public void setEq(Boolean eq) {
        isEq = eq;
    }

    @Basic
    @Column(name = "IsWS", nullable = true)
    public Boolean getWs() {
        return isWs;
    }

    public void setWs(Boolean ws) {
        isWs = ws;
    }

    @Basic
    @Column(name = "IsFL", nullable = true)
    public Boolean getFl() {
        return isFl;
    }

    public void setFl(Boolean fl) {
        isFl = fl;
    }

    @Basic
    @Column(name = "InceptionDate", nullable = true)
    public Timestamp getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(Timestamp inceptionDate) {
        this.inceptionDate = inceptionDate;
    }

    @Basic
    @Column(name = "ExpiryDate", nullable = true)
    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Basic
    @Column(name = "EarliestDueDate", nullable = true, length = 10)
    public String getEarliestDueDate() {
        return earliestDueDate;
    }

    public void setEarliestDueDate(String earliestDueDate) {
        this.earliestDueDate = earliestDueDate;
    }

    @Basic
    @Column(name = "UwUnitid", nullable = true, length = 255)
    public String getUwUnitid() {
        return uwUnitid;
    }

    public void setUwUnitid(String uwUnitid) {
        this.uwUnitid = uwUnitid;
    }

    @Basic
    @Column(name = "UwUnitLabel", nullable = true, length = 255)
    public String getUwUnitLabel() {
        return uwUnitLabel;
    }

    public void setUwUnitLabel(String uwUnitLabel) {
        this.uwUnitLabel = uwUnitLabel;
    }

    @Basic
    @Column(name = "ContractNatureType", nullable = true, length = 255)
    public String getContractNatureType() {
        return contractNatureType;
    }

    public void setContractNatureType(String contractNatureType) {
        this.contractNatureType = contractNatureType;
    }

    @Basic
    @Column(name = "Underwriterid", nullable = true, length = 255)
    public String getUnderwriterid() {
        return underwriterid;
    }

    public void setUnderwriterid(String underwriterid) {
        this.underwriterid = underwriterid;
    }

    @Basic
    @Column(name = "UnderwriterName", nullable = true, length = 511)
    public String getUnderwriterName() {
        return underwriterName;
    }

    public void setUnderwriterName(String underwriterName) {
        this.underwriterName = underwriterName;
    }

    @Basic
    @Column(name = "Subsidiaryid", nullable = true)
    public Integer getSubsidiaryid() {
        return subsidiaryid;
    }

    public void setSubsidiaryid(Integer subsidiaryid) {
        this.subsidiaryid = subsidiaryid;
    }

    @Basic
    @Column(name = "SubsidiaryName", nullable = true, length = 255)
    public String getSubsidiaryName() {
        return subsidiaryName;
    }

    public void setSubsidiaryName(String subsidiaryName) {
        this.subsidiaryName = subsidiaryName;
    }

    @Basic
    @Column(name = "SubsidiaryLedgerid", nullable = true, length = 255)
    public String getSubsidiaryLedgerid() {
        return subsidiaryLedgerid;
    }

    public void setSubsidiaryLedgerid(String subsidiaryLedgerid) {
        this.subsidiaryLedgerid = subsidiaryLedgerid;
    }

    @Basic
    @Column(name = "SubsidiaryLedgerName", nullable = true, length = 255)
    public String getSubsidiaryLedgerName() {
        return subsidiaryLedgerName;
    }

    public void setSubsidiaryLedgerName(String subsidiaryLedgerName) {
        this.subsidiaryLedgerName = subsidiaryLedgerName;
    }

    @Basic
    @Column(name = "LOBid", nullable = true, length = 255)
    public String getLoBid() {
        return loBid;
    }

    public void setLoBid(String loBid) {
        this.loBid = loBid;
    }

    @Basic
    @Column(name = "LOBName", nullable = true, length = 255)
    public String getLobName() {
        return lobName;
    }

    public void setLobName(String lobName) {
        this.lobName = lobName;
    }

    @Basic
    @Column(name = "SOBid", nullable = true, length = 255)
    public String getSoBid() {
        return soBid;
    }

    public void setSoBid(String soBid) {
        this.soBid = soBid;
    }

    @Basic
    @Column(name = "SOBName", nullable = true, length = 255)
    public String getSobName() {
        return sobName;
    }

    public void setSobName(String sobName) {
        this.sobName = sobName;
    }

    @Basic
    @Column(name = "TypeOfPolicyid", nullable = true, length = 255)
    public String getTypeOfPolicyid() {
        return typeOfPolicyid;
    }

    public void setTypeOfPolicyid(String typeOfPolicyid) {
        this.typeOfPolicyid = typeOfPolicyid;
    }

    @Basic
    @Column(name = "TypeOfPolicyName", nullable = true, length = 255)
    public String getTypeOfPolicyName() {
        return typeOfPolicyName;
    }

    public void setTypeOfPolicyName(String typeOfPolicyName) {
        this.typeOfPolicyName = typeOfPolicyName;
    }

    @Basic
    @Column(name = "Natureid", nullable = true, length = 255)
    public String getNatureid() {
        return natureid;
    }

    public void setNatureid(String natureid) {
        this.natureid = natureid;
    }

    @Basic
    @Column(name = "NatureName", nullable = true, length = 255)
    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
    }

    @Basic
    @Column(name = "LiabilityCurrencyid", nullable = true, length = 255)
    public String getLiabilityCurrencyid() {
        return liabilityCurrencyid;
    }

    public void setLiabilityCurrencyid(String liabilityCurrencyid) {
        this.liabilityCurrencyid = liabilityCurrencyid;
    }

    @Basic
    @Column(name = "contractSourceTypeName", nullable = true, length = 255)
    public String getContractSourceTypeName() {
        return contractSourceTypeName;
    }

    public void setContractSourceTypeName(String contractSourceTypeName) {
        this.contractSourceTypeName = contractSourceTypeName;
    }

    @Basic
    @Column(name = "LastScopeImpactDate", nullable = true)
    public Timestamp getLastScopeImpactDate() {
        return lastScopeImpactDate;
    }

    public void setLastScopeImpactDate(Timestamp lastScopeImpactDate) {
        this.lastScopeImpactDate = lastScopeImpactDate;
    }

    @Basic
    @Column(name = "LastSyncRunCatDomain", nullable = true)
    public Timestamp getLastSyncRunCatDomain() {
        return lastSyncRunCatDomain;
    }

    public void setLastSyncRunCatDomain(Timestamp lastSyncRunCatDomain) {
        this.lastSyncRunCatDomain = lastSyncRunCatDomain;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractSearchResultEntity that = (ContractSearchResultEntity) o;
        return Objects.equals(workSpaceId, that.workSpaceId) &&
                Objects.equals(workspaceName, that.workspaceName) &&
                Objects.equals(id, that.id) &&
                Objects.equals(sectionid, that.sectionid) &&
                Objects.equals(sectionLabel, that.sectionLabel) &&
                Objects.equals(isActive, that.isActive) &&
                Objects.equals(uwYear, that.uwYear) &&
                Objects.equals(treatyid, that.treatyid) &&
                Objects.equals(treatyName, that.treatyName) &&
                Objects.equals(contractStatusid, that.contractStatusid) &&
                Objects.equals(contractStatus, that.contractStatus) &&
                Objects.equals(sectionStatusid, that.sectionStatusid) &&
                Objects.equals(sectionStatus, that.sectionStatus) &&
                Objects.equals(accumulationPublicationStatus, that.accumulationPublicationStatus) &&
                Objects.equals(bouquetid, that.bouquetid) &&
                Objects.equals(bouquetName, that.bouquetName) &&
                Objects.equals(programid, that.programid) &&
                Objects.equals(programName, that.programName) &&
                Objects.equals(countryCode, that.countryCode) &&
                Objects.equals(countryName, that.countryName) &&
                Objects.equals(cedantName, that.cedantName) &&
                Objects.equals(cedantCode, that.cedantCode) &&
                Objects.equals(isEq, that.isEq) &&
                Objects.equals(isWs, that.isWs) &&
                Objects.equals(isFl, that.isFl) &&
                Objects.equals(inceptionDate, that.inceptionDate) &&
                Objects.equals(expiryDate, that.expiryDate) &&
                Objects.equals(earliestDueDate, that.earliestDueDate) &&
                Objects.equals(uwUnitid, that.uwUnitid) &&
                Objects.equals(uwUnitLabel, that.uwUnitLabel) &&
                Objects.equals(contractNatureType, that.contractNatureType) &&
                Objects.equals(underwriterid, that.underwriterid) &&
                Objects.equals(underwriterName, that.underwriterName) &&
                Objects.equals(subsidiaryid, that.subsidiaryid) &&
                Objects.equals(subsidiaryName, that.subsidiaryName) &&
                Objects.equals(subsidiaryLedgerid, that.subsidiaryLedgerid) &&
                Objects.equals(subsidiaryLedgerName, that.subsidiaryLedgerName) &&
                Objects.equals(loBid, that.loBid) &&
                Objects.equals(lobName, that.lobName) &&
                Objects.equals(soBid, that.soBid) &&
                Objects.equals(sobName, that.sobName) &&
                Objects.equals(typeOfPolicyid, that.typeOfPolicyid) &&
                Objects.equals(typeOfPolicyName, that.typeOfPolicyName) &&
                Objects.equals(natureid, that.natureid) &&
                Objects.equals(natureName, that.natureName) &&
                Objects.equals(liabilityCurrencyid, that.liabilityCurrencyid) &&
                Objects.equals(contractSourceTypeName, that.contractSourceTypeName) &&
                Objects.equals(lastScopeImpactDate, that.lastScopeImpactDate) &&
                Objects.equals(lastSyncRunCatDomain, that.lastSyncRunCatDomain);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workSpaceId, workspaceName, id, sectionid, sectionLabel, isActive, uwYear, treatyid, treatyName, contractStatusid, contractStatus, sectionStatusid, sectionStatus, accumulationPublicationStatus, bouquetid, bouquetName, programid, programName, countryCode, countryName, cedantName, cedantCode, isEq, isWs, isFl, inceptionDate, expiryDate, earliestDueDate, uwUnitid, uwUnitLabel, contractNatureType, underwriterid, underwriterName, subsidiaryid, subsidiaryName, subsidiaryLedgerid, subsidiaryLedgerName, loBid, lobName, soBid, sobName, typeOfPolicyid, typeOfPolicyName, natureid, natureName, liabilityCurrencyid, contractSourceTypeName, lastScopeImpactDate, lastSyncRunCatDomain);
    }
}
