package com.scor.rr.domain.entities.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "vw_RecentWorkspace")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentWorkspaceView {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "workspaceContextCode")
    private String workspaceContextCode;

    @Column(name = "workspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "userId")
    private Long userId;

    @Column(name = "WorkspaceName")
    private String workspaceName;

    @Column(name = "CedantName")
    private String cedantName;

    @CreatedDate
    @Column(name = "lastOpened")
    private Date lastOpened;
}
