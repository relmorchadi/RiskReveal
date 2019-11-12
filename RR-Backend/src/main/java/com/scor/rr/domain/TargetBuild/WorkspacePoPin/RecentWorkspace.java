package com.scor.rr.domain.TargetBuild.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RecentWorkspace", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recentWorkspaceId")
    private Integer recentWorkspaceId;

    @Column(name = "workspaceId")
    private Long workspaceId;

    @Column(name = "userId")
    private Integer userId;

    @CreatedDate
    @Column(name = "lastOpened", nullable = false)
    private Date lastOpened;
}
