package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

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

    @Column(name = "RREntity")
    private Integer entity;

    @Column(name = "WorkspaceContextCode")
    private String workspaceContextCode;

    @Column(name = "WorkspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "WorkspaceName")
    private String workspaceName;

    @Column(name = "CedantName")
    private String cedantName;

    public WorkspaceEntity(String workspaceContextCode, Integer workspaceUwYear, String workspaceName, String cedantName) {
        this.workspaceContextCode = workspaceContextCode;
        this.entity = 1;
        this.workspaceUwYear = workspaceUwYear;
        this.workspaceName = workspaceName;
        this.cedantName = cedantName;
    }
}