package com.scor.rr.domain.entities;

import com.scor.rr.domain.entities.Project.ProjectCardView;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "vw_FacContractSearchResult")
@Data
public class FacContractSearchResult {

    @Column(name = "Client")
    private String client;

    @Column(name = "WorkspaceContextCode", length = 55)
    private String workspaceContextCode;

    @Column(name = "UWYear")
    private Integer uwYear;

    @Column(name = "WorkspaceName")
    private String workspaceName;

    @Column(name = "UwAnalysis")
    private String uwAnalysis;

    @Id
    @Column(name = "CARequestId", length = 30)
    private String carequestId;

    @Column(name = "CARStatus", length = 15)
    private String carStatus;

    @Column(name = "AssignedUser")
    private Long assignedUser;

}
