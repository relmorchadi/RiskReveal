package com.scor.rr.domain.entities.omega;

import com.scor.rr.domain.enums.ContractSource;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the ContractSourceType database table
 *
 * @author HADDINI Zakariyae && HAMIANI Mohammed
 */
@Entity
@Table(name = "ContractSourceType")
@Data
public class ContractSourceType {
    @Id
    @Column(name = "ContractSourceTypeId")
    private Long contractSourceTypeId;
    @Column(name = "ContractSourceTypeName")
    private String contractSourceTypeName;
    @Column(name = "PublishARCAdmin")
    private Boolean publishARCAdmin;
    @Column(name = "PublishARCUser")
    private Boolean publishARCUser;
    @Column(name = "PublishXACTAdmin")
    private Boolean publishXACTAdmin;
    @Column(name = "PublishXACTUser")
    private Boolean publishXACTUser;
    @Column(name = "ContractSource")
    private ContractSource contractSource;
}
