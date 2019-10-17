package com.scor.rr.domain.entities.references.inuring;

import lombok.Data;

import javax.persistence.*;

/**
 * The persistent class for the ContractTypeAttribute database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ContractTypeAttribute")
@Data
public class ContractTypeAttribute {
    @Id
    @Column(name = "ContractTypeAttributeId")
    private Long contractTypeAttributeId;
    @Column(name = "ContractTypeName")
    private String contractTypeName;
    @Column(name = "AttributeName")
    private String attributeName;
    @Column(name = "Visible")
    private Boolean visible;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AttributeId")
    private InuringAttribute inuringAttribute;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ContractTypeId")
    private InuringContractType inuringContractType;
}
