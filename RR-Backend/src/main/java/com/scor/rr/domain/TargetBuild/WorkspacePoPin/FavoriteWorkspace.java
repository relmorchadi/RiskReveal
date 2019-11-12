package com.scor.rr.domain.TargetBuild.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "FavoriteWorkspace", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoriteWorkspaceId")
    private Integer favoriteWorkspaceId;

    @Column(name = "workspaceId")
    private Long workspaceId;

    @Column(name = "userId")
    private Integer userId;

    @CreatedDate
    @Column(name = "createdDate", nullable = false, updatable = false)
    private Date createdDate;

}
