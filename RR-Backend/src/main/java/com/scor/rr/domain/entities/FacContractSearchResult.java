package com.scor.rr.domain.entities;

import com.scor.rr.domain.entities.Project.ProjectCardView;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;

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

    @Column(name = "AssignedTo")
    private Long assignedTo;

    public FacContractSearchResult(String client, Integer uwYear, String workspaceContextCode, String workspaceName, String uwAnalysis, String carequestId, String carStatus, BigInteger assignedUser) {
        this.client = client;
        this.workspaceContextCode = workspaceContextCode;
        this.uwYear = uwYear;
        this.workspaceName = workspaceName;
        this.uwAnalysis = uwAnalysis;
        this.carequestId = carequestId;
        this.carStatus = carStatus;
        this.assignedTo = assignedUser != null ? assignedUser.longValue() : null;
    }
}
