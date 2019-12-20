package com.scor.rr.domain.entities.WorkspacePoPin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ZZ_FavoriteWorkspace")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteWorkspace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "favoriteWorkspaceId")
    private Long favoriteWorkspaceId;

    @Column(name = "workspaceContextCode")
    private String workspaceContextCode;

    @Column(name = "workspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "userId")
    private Integer userId;

    @CreatedDate
    @Column(name = "createdDate", nullable = false, updatable = false)
    private Date createdDate;

}
