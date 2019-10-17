package com.scor.rr.domain.entities.references.inuring;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the InuringContractType database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "InuringContractType")
@Data
public class InuringContractType {
    @Id
    @Column(name = "ContractTypeId")
    private Integer contractTypeId;
    @Column(name = "ContractTypeCode")
    private String contractTypeCode;
    @Column(name = "ContractTypeName")
    private String contractTypeName;
    @Column(name = "ExistingInRR")
    private Boolean existingInRR;
    @Column(name = "Sequence")
    private Integer sequence;
    @Column(name = "ExistingInARC")
    private Boolean existingInARC;
    @Column(name = "UiLabel")
    private String uiLabel;
    @Column(name = "UiHover")
    private String uiHover;
    @Column(name = "MultipleLayer")
    private Boolean multipleLayer;
//    @Column(name = "ContractClass")
//    private ContractClass contractClass;
//    @Column(name = "MainDistributionChannel")
//    private MainDistributionChannel mainDistributionChannel;
}
