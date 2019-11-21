package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;


@Entity
@Table(schema = "poc")
@Data
@NoArgsConstructor
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractSearchResult {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "WorkSpaceId")
    private String workSpaceId;
    @Column(name = "WorkspaceName")
    private String workspaceName;
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

    public ContractSearchResult(String countryName, String workSpaceId, String workspaceName, String cedantCode, String cedantName, Integer uwYear) {
        this.countryName = countryName;
        this.workSpaceId = workSpaceId;
        this.workspaceName = workspaceName;
        this.cedantName = cedantName;
        this.cedantCode = cedantCode;
        this.uwYear = uwYear;
    }
}
