package com.scor.rr.domain.entities.omega;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

/**
 * The persistent class for the ContractSectionLastScopeImpacted database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ContractSectionLastScopeImpacted")
@Data
public class ContractSectionLastScopeImpacted {
    @Id
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "UWYear")
    private Integer uwYear;
    @Column(name = "UWOrder")
    private Integer uwOrder;
    @Column(name = "SectionId")
    private Integer sectionId;
    @Column(name = "EndorsmentNumber")
    private Integer endorsmentNumber;
    @Column(name = "LastSyncRunCatDomain")
    private Date lastSyncRunCatDomain;
}
