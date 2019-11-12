package com.scor.rr.domain.TargetBuild.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PinnedWorkspace", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PinnedWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pinnedWorkspaceId")
    private Integer pinnedWorkspaceId;

    @Column(name = "workspaceId")
    private Long workspaceId;

    @Column(name = "userId")
    private Integer userId;

    @CreatedDate
    @Column(name = "createdDate", nullable = false, updatable = false)
    private Date createdDate;

}
