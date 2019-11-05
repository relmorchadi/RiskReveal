package com.scor.rr.domain.TargetBuild.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "AssignedWorkspaceView", schema = "dr")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssignedWorkspaceView {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "WorkspaceId")
    private Long workspaceId;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "WorkspaceContextCode")
    private String workspaceContextCode;

    @Column(name = "WorkspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "WorkspaceName")
    private String workspaceName;

    @Column(name = "CedantName")
    private String cedantName;
}
