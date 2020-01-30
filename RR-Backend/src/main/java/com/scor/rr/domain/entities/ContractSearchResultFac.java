package com.scor.rr.domain.entities;

import com.scor.rr.domain.entities.Project.ProjectCardView;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "vw_ContractSearchResultFac")
@Data
public class ContractSearchResultFac {

    @Id
    @Column(name = "Id")
    private Long id;

    @Column(name = "WorkspaceId")
    private Long workspaceId;

    @Column(name = "WorkspaceContextCode", length = 55)
    private String workspaceContextCode;

    @Column(name = "WorkspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "WorkspaceMarketChannel", length = 5)
    private String workspaceMarketChannel;

    @Column(name = "WorkspaceName")
    private String workspaceName;

    @Column(name = "ClientName")
    private String clientName;

    @Column(name = "Subsidiary", length = 25)
    private String subsidiary;

    @Column(name = "ledgerName")
    private String ledgerName;

    @Column(name = "subsidiaryName")
    private String subsidiaryName;

    @Column(name = "expiryDate")
    private String expiryDate;

    @Column(name = "inceptionDate")
    private String inceptionDate;

    @Column(name = "contractDatasource")
    private String contractDatasource;
}
