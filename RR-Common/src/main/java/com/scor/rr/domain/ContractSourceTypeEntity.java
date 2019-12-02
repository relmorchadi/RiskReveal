package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ContractSourceType")
@AllArgsConstructor
@NoArgsConstructor
public class ContractSourceTypeEntity {

    @Id
    @Column(name = "ContractSourceTypeId")
    private Long contractSourceTypeId;

    @Column(name = "ContractSourceName")
    private Long contractSourceTypeName;

    @Column(name = "PublishARCAdmin")
    private boolean publishARCAdmin;

    @Column(name = "PublishARCUser")
    private boolean publishARCUser;

    @Column(name = "PublishXACTAdmin")
    private boolean publishXACTAdmin;

    @Column(name = "PublishXACTUser")
    private boolean publishXACTUser;
}
