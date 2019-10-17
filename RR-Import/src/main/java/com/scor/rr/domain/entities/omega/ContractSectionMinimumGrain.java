package com.scor.rr.domain.entities.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * The persistent class for the ContractSectionMinimumGrain database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ContractSectionMinimumGrain")
@Data
public class ContractSectionMinimumGrain {
    @Id
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "UWYear")
    private Integer uwYear;
    @Column(name = "UWOrder")
    private Integer uwOrder;
    @Column(name = "EndorsmentNumber")
    private Integer endorsmentNumber;
    @Column(name = "SectionId")
    private Integer sectionId;
    @Column(name = "MinimumGrainCodeISO2")
    private String minimumGrainCodeISO2;
    @Column(name = "MinimumGrainCodeISO3")
    private String minimumGrainCodeISO3;
    @Column(name = "MinimumGrainCodeRiskReveal")
    private String minimumGrainCodeRiskReveal;
    @Column(name = "LastUpdateOmega")
    private Date lastUpdateOmega;
    @Column(name = "LastUpdateCatDomain")
    private Date lastUpdateCatDomain;
    @Column(name = "LastSyncRunCatDomain")
    private Date lastSyncRunCatDomain;
    @Column(name = "Active")
    private Boolean isActive;
}
