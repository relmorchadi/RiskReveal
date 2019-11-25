package com.scor.rr.domain.TargetBuild.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FavoriteWorkspaceView", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteWorkspaceView {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "workspaceContextCode")
    private String workspaceContextCode;

    @Column(name = "workspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "userId")
    private Integer userId;

    @Column(name = "WorkspaceName")
    private String workspaceName;

    @Column(name = "CedantName")
    private String cedantName;

    @Column(name = "createdDate")
    private Date createdDate;

}
