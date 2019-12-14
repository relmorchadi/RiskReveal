package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Workspace")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkspaceEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WorkspaceId")
    private Long workspaceId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "WorkspaceContext", length = 40)
    private String workspaceContext;

    @Column(name = "WorkspaceContextCode", length = 55)
    private String workspaceContextCode;

    @Column(name = "WorkspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "WorkspaceMarketChannel")
    private Long workspaceMarketChannel;

    @Column(name = "WorkspaceName")
    private String workspaceName;

    @Column(name = "ClientName")
    private String clientName;

    public WorkspaceEntity(String workspaceContextCode, Integer workspaceUwYear,Long workspaceMarketChannel, String workspaceName, String clientName) {
        this.workspaceContextCode = workspaceContextCode;
        this.entity = 1;
        this.workspaceUwYear = workspaceUwYear;
        this.workspaceMarketChannel= workspaceMarketChannel;
        this.workspaceName = workspaceName;
        this.clientName = clientName;
    }
}