package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "CONTRACT")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContractEntity {

    @Id
    @Column(name = "CONTRACT_ID", nullable = false, length = 255)
    private String id;
    @Basic
    @Column(name = "IS_ACTIVE", nullable = true)
    private Boolean isActive;
    @Basic
    @Column(name = "LAST_SYNCHRONIZED", nullable = true)
    private Timestamp lastSynchronized;
    @Basic
    @Column(name = "FK_SUBSIDIARY_LEDGER_CODE_ID", nullable = true, length = 255)
    private String fkSubsidiaryLedgerCodeId;
    @Basic
    @Column(name = "TREATY_ID", nullable = true, length = 255)
    private String treatyId;
    @Basic
    @Column(name = "UW_YEAR", nullable = true)
    private Integer uwYear;
    @Basic
    @Column(name = "UW_ORDER", nullable = true)
    private Integer uwOrder;
    @Basic
    @Column(name = "ENDORSEMENT_NUMBER", nullable = true)
    private Integer endorsementNumber;
    @Basic
    @Column(name = "TREATY_LABEL", nullable = true, length = 255)
    private String treatyLabel;
    @Basic
    @Column(name = "FK_STATUS_ID", nullable = true, length = 255)
    private String statusId;
    @Basic
    @Column(name = "SUBSIDIARY_CODE_ID", nullable = true)
    private Integer subsidiaryCodeId;
    @Basic
    @Column(name = "UW_UNIT_ID", nullable = true, length = 255)
    private String uwUnitId;
    @Basic
    @Column(name = "SUBSIDIARY_LEDGER_CODE", nullable = true, length = 255)
    private String subsidiaryLedgerCode;
    @Basic
    @Column(name = "INCEPTION_DATE", nullable = true)
    private Timestamp inceptionDate;
    @Basic
    @Column(name = "EXPIRY_DATE", nullable = true)
    private Timestamp expiryDate;
    @Basic
    @Column(name = "PROGRAM_ID", nullable = true, length = 255)
    private String programId;
    @Basic
    @Column(name = "PROGRAM_NAME", nullable = true, length = 255)
    private String programName;
    @Basic
    @Column(name = "BOUQUET_ID", nullable = true, length = 255)
    private String bouquetId;
    @Basic
    @Column(name = "BOUQUET_NAME", nullable = true, length = 255)
    private String bouquetName;
    @Basic
    @Column(name = "FK_CEDENT_ID", nullable = true, length = 255)
    private String cedentId;
    @Basic
    @Column(name = "UNDERWRITER_ID", nullable = true, length = 255)
    private String underWriterId;
    @Basic
    @Column(name = "UNDERWRITER_FIRST_NAME", nullable = true, length = 255)
    private String underWriterFirstName;
    @Basic
    @Column(name = "UNDERWRITER_LAST_NAME", nullable = true, length = 255)
    private String underWriterLastName;
    @Basic
    @Column(name = "FK_LIABILITY_CURRENCY_CODE", nullable = true, length = 255)
    private String liabilityCurrencyCodeId;
    @Basic
    @Column(name = "ANNUAL_LIMIT_AMOUNT", nullable = true, length = 255)
    private String annualLimitAmount;
    @Basic
    @Column(name = "EVENT_LIMIT_AMOUNT", nullable = true, length = 255)
    private String eventLimitAmount;
    @Basic
    @Column(name = "UPDATE_TIME", nullable = true)
    private Timestamp updateTime;
    @Basic
    @Column(name = "LEADER", nullable = true, length = 255)
    private String leader;
    @Basic
    @Column(name = "UNDERWRITING_UNIT_CODE", nullable = true, length = 255)
    private String underWritingUnitCode;
    @Basic
    @Column(name = "UNDERWRITING_UNIT_NAME_LL", nullable = true, length = 255)
    private String underWritingUnitNameLL;
    @Basic
    @Column(name = "UNDERWRITING_UNIT_NAME_LS", nullable = true, length = 255)
    private String underWritingUnitNameLS;
    @Basic
    @Column(name = "ULTIMATE_GROUP_CODE", nullable = true, length = 255)
    private String ultimateGroupCode;
    @Basic
    @Column(name = "ULTIMATE_GROUP_NAME", nullable = true, length = 255)
    private String ultimateGroupName;
    @Basic
    @Column(name = "GROUP_SEGMENT_NAME_LM", nullable = true, length = 255)
    private String groupSegmentNameLM;
    @Basic
    @Column(name = "GROUP_SEGMENT_CODE", nullable = true, length = 255)
    private String groupSegmentCode;
    @Basic
    @Column(name = "GROUP_SEGMENT_NAME_LS", nullable = true, length = 255)
    private String groupSegmentNameLS;
    @Basic
    @Column(name = "INTERMEDIARY_CODE", nullable = true, length = 255)
    private String intermediaryCode;
    @Basic
    @Column(name = "UW_Unit_Omega2", nullable = true, length = 255)
    private String uwUnitOmega2;
    @Basic
    @Column(name = "LAST_UPDATE_OMEGA", nullable = true)
    private Timestamp lastUpdateOmega;
    @Basic
    @Column(name = "LAST_EXTRACT_OMEGA", nullable = true)
    private Timestamp lastExtractOmega;
    @Basic
    @Column(name = "LAST_UPDATE_CAT_DOMAIN", nullable = true)
    private Timestamp lastUpdateCatDomain;
    @Basic
    @Column(name = "LAST_SYNC_RUN_CAT_DOMAIN", nullable = true)
    private Timestamp lastSyncRunCatDomain;
    @Basic
    @Column(name = "CONTRACT_SOURCE_TYPE__ID", nullable = true)
    private Long contractSourceTypeId;
}
