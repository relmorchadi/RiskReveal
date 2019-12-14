package com.scor.rr.domain.entities.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "vw_PinnedWorkspace")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinnedWorkspaceView {

    @Id
    @Column(name = "id")
    private Long id;

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

    @Column(name = "createdDate")
    private Date createdDate;

}
