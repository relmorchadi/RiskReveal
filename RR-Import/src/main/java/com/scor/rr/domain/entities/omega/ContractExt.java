package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.enums.ExpectedFrequency;
import com.scor.rr.domain.enums.FinacialShareBasis;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ContractExtension database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ContractExtension")
@Data
public class ContractExt {
    @Id
    @Column(name = "TreatyId")
    private String treatyId;
    @Column(name = "UWYear")
    private Integer uwYear;
    @Column(name = "UWOrder")
    private Integer uwOrder;
    @Column(name = "MGA")
    private Boolean isMGA;
    @Column(name = "EndorsmentNumber")
    private Integer endorsmentNumber;
    @Column(name = "DefaultExtractLocSum")
    private Boolean defaultExtractLocSum;
    @Column(name = "ExpectedFrequency")
    private ExpectedFrequency expectedFrequency;
    @Column(name = "FinacialShareBasis")
    private FinacialShareBasis finacialShareBasis;

}
