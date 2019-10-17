package com.scor.rr.domain.entities.references.inuring;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the InuringContractTypeContractNodeDisplay database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "InuringContractTypeContractNodeDisplay")
@Data
public class InuringContractTypeContractNodeDisplay {
    @Id
    @Column(name = "ContractTypeCode")
    private String contractTypeCode;
    @Column(name = "ContractTypeName")
    private String contractTypeName;
    @Column(name = "UiContractNodeAttributeCount")
    private Integer uiContractNodeAttributeCount;
    @Column(name = "UiContractNodeAttributeCodes")
    private String uiContractNodeAttributeCodes;
}
