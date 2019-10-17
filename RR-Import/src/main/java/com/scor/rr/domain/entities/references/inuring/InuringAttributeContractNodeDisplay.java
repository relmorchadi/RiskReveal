package com.scor.rr.domain.entities.references.inuring;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the InuringAttributeContractNodeDisplay database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "InuringAttributeContractNodeDisplay")
@Data
public class InuringAttributeContractNodeDisplay {

    @Id
    @Column(name = "InuringAttributeContractNodeDisplayId")
    private Long inuringAttributeContractNodeDisplayId;
    @Column(name = "Sequence")
    private Integer sequence;
    @Column(name = "Row")
    private Integer row;
    @Column(name = "AttributeName")
    private String attributeName;
    @Column(name = "AttributeCode")
    private String attributeCode;
}
