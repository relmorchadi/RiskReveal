package com.scor.rr.domain.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vw_FacContractCurrency")
@Data
public class FacContractCurrency {

    @Id
    @Column(name = "WorkspaceId")
    private Long workspaceId;

    @Column(name = "WorkspaceContextCode", length = 55)
    private String workspaceContextCode;

    @Column(name = "WorkspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "Currency", length = 4)
    private String currency;

}
