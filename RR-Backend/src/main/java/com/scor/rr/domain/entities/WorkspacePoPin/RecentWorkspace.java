package com.scor.rr.domain.entities.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "RecentWorkspace")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecentWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recentWorkspaceId")
    private Long recentWorkspaceId;

    @Column(name = "workspaceContextCode")
    private String workspaceContextCode;

    @Column(name = "workspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "userId")
    private Integer userId;

    @CreatedDate
    @Column(name = "lastOpened", nullable = false)
    private Date lastOpened;
}
