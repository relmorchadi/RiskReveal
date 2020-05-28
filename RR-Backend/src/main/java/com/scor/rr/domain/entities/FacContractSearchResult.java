package com.scor.rr.domain.entities;

import com.scor.rr.domain.entities.Project.ProjectCardView;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@Table(name = "vw_FacContractSearchResult")
@Data
public class FacContractSearchResult {

    @Column(name = "ClientName")
    private String clientName;

    @Column(name = "ClientCode")
    private String client;

    @Column(name = "WorkspaceContextCode", length = 55)
    private String workspaceContextCode;

    @Column(name = "UWYear")
    private Integer uwYear;

    @Column(name = "WorkspaceName")
    private String workspaceName;

    @Column(name = "UwAnalysis")
    private String uwAnalysis;

    @Column(name = "CARequestId", length = 30)
    private String carequestId;

    @Column(name = "CARStatus", length = 15)
    private String carStatus;

    @Column(name = "AssignedTo")
    private Long assignedTo;

    @Id
    @Column(name = "Plt")
    private Long plt;

    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "UserName")
    private String userName;

    public FacContractSearchResult(String client, Integer uwYear, String workspaceContextCode, String workspaceName, String uwAnalysis, String carequestId, String carStatus
            , BigInteger assignedUser, String userName) {
        this.client = client;
        this.workspaceContextCode = workspaceContextCode;
        this.uwYear = uwYear;
        this.userName = userName;
        this.workspaceName = workspaceName;
        this.uwAnalysis = uwAnalysis;
        this.carequestId = carequestId;
        this.carStatus = carStatus;
        this.assignedTo = assignedUser != null ? assignedUser.longValue() : null;
    }
}
