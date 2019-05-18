package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractSearchResult {
    @Column(name = "WorkSpaceId")
    private String workSpaceId;
    @Column(name = "WorkspaceName")
    private String workspaceName;
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "Sectionid")
    private Integer sectionid;
    @Column(name = "SectionLabel")
    private String sectionLabel;
    @Column(name = "IsActive")
    private Boolean isActive;
    @Column(name = "UwYear")
    private Integer uwYear;
    @Column(name = "Treatyid")
    private String treatyid;
    @Column(name = "TreatyName")
    private String treatyName;
    @Column(name = "ContractStatusid")
    private String contractStatusid;
    @Column(name = "ContractStatus")
    private String contractStatus;
    @Column(name = "SectionStatusid")
    private String sectionStatusid;
    @Column(name = "SectionStatus")
    private String sectionStatus;
    @Column(name = "AccumulationPublicationStatus")
    private String accumulationPublicationStatus;
    @Column(name = "Bouquetid")
    private String bouquetid;
    @Column(name = "BouquetName")
    private String bouquetName;
    @Column(name = "Programid")
    private String programid;
    @Column(name = "ProgramName")
    private String programName;
    @Column(name = "CountryCode")
    private String countryCode;
    @Column(name = "CountryName")
    private String countryName;
    @Column(name = "CedantName")
    private String cedantName;
    @Column(name = "CedantCode")
    private String cedantCode;
    @Column(name = "IsEQ")
    private Boolean isEq;
    @Column(name = "IsWS")
    private Boolean isWs;
    @Column(name = "IsFL")
    private Boolean isFl;
    @Column(name = "InceptionDate")
    private Timestamp inceptionDate;
    @Column(name = "ExpiryDate")
    private Timestamp expiryDate;
    @Column(name = "EarliestDueDate")
    private String earliestDueDate;
    @Column(name = "UwUnitid")
    private String uwUnitid;
    @Column(name = "UwUnitLabel")
    private String uwUnitLabel;
    @Column(name = "ContractNatureType")
    private String contractNatureType;
    @Column(name = "Underwriterid")
    private String underwriterid;
    @Column(name = "UnderwriterName")
    private String underwriterName;
    @Column(name = "Subsidiaryid")
    private Integer subsidiaryid;
    @Column(name = "SubsidiaryName")
    private String subsidiaryName;
    @Column(name = "SubsidiaryLedgerid")
    private String subsidiaryLedgerid;
    @Column(name = "SubsidiaryLedgerName")
    private String subsidiaryLedgerName;
    @Column(name = "LOBid")
    private String loBid;
    @Column(name = "LOBName")
    private String lobName;
    @Column(name = "SOBid")
    private String soBid;
    @Column(name = "SOBName")
    private String sobName;
    @Column(name = "TypeOfPolicyid")
    private String typeOfPolicyid;
    @Column(name = "TypeOfPolicyName")
    private String typeOfPolicyName;
    @Column(name = "Natureid")
    private String natureid;
    @Column(name = "NatureName")
    private String natureName;
    @Column(name = "LiabilityCurrencyid")
    private String liabilityCurrencyid;
    @Column(name = "contractSourceTypeName")
    private String contractSourceTypeName;
    @Column(name = "LastScopeImpactDate")
    private Timestamp lastScopeImpactDate;
    @Column(name = "LastSyncRunCatDomain")
    private Timestamp lastSyncRunCatDomain;

    public ContractSearchResult() {
    }

    public ContractSearchResult(String countryName, String workSpaceId, String workspaceName, String cedantCode, String cedantName, Integer uwYear) {
        this.countryName = countryName;
        this.workSpaceId = workSpaceId;
        this.workspaceName = workspaceName;
        this.cedantName = cedantName;
        this.cedantCode = cedantCode;
        this.uwYear = uwYear;
    }
    public String getWorkSpaceId() {
        return workSpaceId;
    }

    public void setWorkSpaceId(String workSpaceId) {
        this.workSpaceId = workSpaceId;
    }
    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Integer getSectionid() {
        return sectionid;
    }

    public void setSectionid(Integer sectionid) {
        this.sectionid = sectionid;
    }
    public String getSectionLabel() {
        return sectionLabel;
    }

    public void setSectionLabel(String sectionLabel) {
        this.sectionLabel = sectionLabel;
    }
    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
    }
    public String getTreatyid() {
        return treatyid;
    }

    public void setTreatyid(String treatyid) {
        this.treatyid = treatyid;
    }
    public String getTreatyName() {
        return treatyName;
    }

    public void setTreatyName(String treatyName) {
        this.treatyName = treatyName;
    }
    public String getContractStatusid() {
        return contractStatusid;
    }

    public void setContractStatusid(String contractStatusid) {
        this.contractStatusid = contractStatusid;
    }
    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }
    public String getSectionStatusid() {
        return sectionStatusid;
    }

    public void setSectionStatusid(String sectionStatusid) {
        this.sectionStatusid = sectionStatusid;
    }
    public String getSectionStatus() {
        return sectionStatus;
    }

    public void setSectionStatus(String sectionStatus) {
        this.sectionStatus = sectionStatus;
    }
    public String getAccumulationPublicationStatus() {
        return accumulationPublicationStatus;
    }

    public void setAccumulationPublicationStatus(String accumulationPublicationStatus) {
        this.accumulationPublicationStatus = accumulationPublicationStatus;
    }
    public String getBouquetid() {
        return bouquetid;
    }

    public void setBouquetid(String bouquetid) {
        this.bouquetid = bouquetid;
    }
    public String getBouquetName() {
        return bouquetName;
    }

    public void setBouquetName(String bouquetName) {
        this.bouquetName = bouquetName;
    }
    public String getProgramid() {
        return programid;
    }

    public void setProgramid(String programid) {
        this.programid = programid;
    }
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
    public String getCedantName() {
        return cedantName;
    }

    public void setCedantName(String cedantName) {
        this.cedantName = cedantName;
    }
    public String getCedantCode() {
        return cedantCode;
    }

    public void setCedantCode(String cedantCode) {
        this.cedantCode = cedantCode;
    }
    public Boolean getEq() {
        return isEq;
    }

    public void setEq(Boolean eq) {
        isEq = eq;
    }
    public Boolean getWs() {
        return isWs;
    }

    public void setWs(Boolean ws) {
        isWs = ws;
    }
    public Boolean getFl() {
        return isFl;
    }

    public void setFl(Boolean fl) {
        isFl = fl;
    }
    public Timestamp getInceptionDate() {
        return inceptionDate;
    }

    public void setInceptionDate(Timestamp inceptionDate) {
        this.inceptionDate = inceptionDate;
    }
    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getEarliestDueDate() {
        return earliestDueDate;
    }

    public void setEarliestDueDate(String earliestDueDate) {
        this.earliestDueDate = earliestDueDate;
    }
    public String getUwUnitid() {
        return uwUnitid;
    }

    public void setUwUnitid(String uwUnitid) {
        this.uwUnitid = uwUnitid;
    }
    public String getUwUnitLabel() {
        return uwUnitLabel;
    }

    public void setUwUnitLabel(String uwUnitLabel) {
        this.uwUnitLabel = uwUnitLabel;
    }
    public String getContractNatureType() {
        return contractNatureType;
    }

    public void setContractNatureType(String contractNatureType) {
        this.contractNatureType = contractNatureType;
    }
    public String getUnderwriterid() {
        return underwriterid;
    }

    public void setUnderwriterid(String underwriterid) {
        this.underwriterid = underwriterid;
    }
    public String getUnderwriterName() {
        return underwriterName;
    }

    public void setUnderwriterName(String underwriterName) {
        this.underwriterName = underwriterName;
    }
    public Integer getSubsidiaryid() {
        return subsidiaryid;
    }

    public void setSubsidiaryid(Integer subsidiaryid) {
        this.subsidiaryid = subsidiaryid;
    }
    public String getSubsidiaryName() {
        return subsidiaryName;
    }

    public void setSubsidiaryName(String subsidiaryName) {
        this.subsidiaryName = subsidiaryName;
    }
    public String getSubsidiaryLedgerid() {
        return subsidiaryLedgerid;
    }

    public void setSubsidiaryLedgerid(String subsidiaryLedgerid) {
        this.subsidiaryLedgerid = subsidiaryLedgerid;
    }
    public String getSubsidiaryLedgerName() {
        return subsidiaryLedgerName;
    }

    public void setSubsidiaryLedgerName(String subsidiaryLedgerName) {
        this.subsidiaryLedgerName = subsidiaryLedgerName;
    }
    public String getLoBid() {
        return loBid;
    }

    public void setLoBid(String loBid) {
        this.loBid = loBid;
    }
    public String getLobName() {
        return lobName;
    }

    public void setLobName(String lobName) {
        this.lobName = lobName;
    }
    public String getSoBid() {
        return soBid;
    }

    public void setSoBid(String soBid) {
        this.soBid = soBid;
    }
    public String getSobName() {
        return sobName;
    }

    public void setSobName(String sobName) {
        this.sobName = sobName;
    }
    public String getTypeOfPolicyid() {
        return typeOfPolicyid;
    }

    public void setTypeOfPolicyid(String typeOfPolicyid) {
        this.typeOfPolicyid = typeOfPolicyid;
    }
    public String getTypeOfPolicyName() {
        return typeOfPolicyName;
    }

    public void setTypeOfPolicyName(String typeOfPolicyName) {
        this.typeOfPolicyName = typeOfPolicyName;
    }
    public String getNatureid() {
        return natureid;
    }

    public void setNatureid(String natureid) {
        this.natureid = natureid;
    }
    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
    }
    public String getLiabilityCurrencyid() {
        return liabilityCurrencyid;
    }

    public void setLiabilityCurrencyid(String liabilityCurrencyid) {
        this.liabilityCurrencyid = liabilityCurrencyid;
    }
    public String getContractSourceTypeName() {
        return contractSourceTypeName;
    }

    public void setContractSourceTypeName(String contractSourceTypeName) {
        this.contractSourceTypeName = contractSourceTypeName;
    }
    public Timestamp getLastScopeImpactDate() {
        return lastScopeImpactDate;
    }

    public void setLastScopeImpactDate(Timestamp lastScopeImpactDate) {
        this.lastScopeImpactDate = lastScopeImpactDate;
    }
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
        ContractSearchResult that = (ContractSearchResult) o;
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
