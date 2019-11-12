package com.scor.rr.domain.TargetBuild.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RecentWorkspaceView", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentWorkspaceView {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "workspaceId")
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

    @CreatedDate
    @Column(name = "lastOpened")
    private Date lastOpened;
}
