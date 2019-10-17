package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.entities.references.cat.Insured;
import com.scor.rr.domain.entities.references.omega.Client;
import com.scor.rr.domain.entities.references.omega.Currency;
import com.scor.rr.domain.entities.references.omega.Subsidiary;
import com.scor.rr.domain.entities.references.omega.SubsidiaryLedger;
import com.scor.rr.domain.entities.workspace.workspaceContext.WorkspaceContext;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * The persistent class for the Contract database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "Contract")
@Data
public class Contract {
    @Id
    @Column(name = "ContractId")
    private String contractId;
    @Column(name = "ContractSourceTypeName")
    private String contractSourceTypeName;
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "UWYear")
    private Integer uwYear;
    @Column(name = "UWOrder")
    private Integer uwOrder;
    @Column(name = "EndorsementNumber")
    private Integer endorsementNumber;
    @Column(name = "TreatyLabel")
    private String treatyLabel;
    @Column(name = "InceptionDate")
    private Date inceptionDate;
    @Column(name = "ExpiryDate")
    private Date expiryDate;
    @Column(name = "ProgramId")
    private String programId;
    @Column(name = "ProgramName")
    private String programName;
    @Column(name = "BouquetId")
    private String bouquetId;
    @Column(name = "BouquetName")
    private String bouquetName;
    @Column(name = "UnderwriterId")
    private String underwriterId;
    @Column(name = "UnderwriterFirstName")
    private String underwriterFirstName;
    @Column(name = "UnderwriterLastName")
    private String underwriterLastName;
    @Column(name = "AnnualLimitAmmount")
    private String annualLimitAmmount;
    @Column(name = "EventLimitAmount")
    private String eventLimitAmount;
    @Column(name = "IsActive")
    private Boolean active;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UWUnitId")
    private UWUnit uwUnit;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ClientId")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "InsuredId")
    private Insured insured;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubsidiaryId")
    private Subsidiary subsidiary;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CurrencyId")
    private Currency liabilityCurrency;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SubsidiaryLedgerId")
    private SubsidiaryLedger subsidiaryLedger;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WorkspaceContextId")
    private WorkspaceContext workspaceContext;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ContractSourceTypeId")
    private ContractSourceType contractSourceType;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TreatySectionStatusId")
    private TreatySectionStatus treatySectionStatus;
}
