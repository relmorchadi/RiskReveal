package com.scor.rr.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "ZZ_UserWorkspaceTabs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkspaceTabs {

    @Id
    @Column(name = "UserWorkspaceTabsId")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userWorkspaceTabsId;

    @Column(name = "WorkspaceContextCode")
    private String workspaceContextCode;

    @Column(name = "WorkspaceUwYear")
    private Integer workspaceUwYear;

    @Column(name = "UserCode", length = 10)
    private String userCode;

    @Column(name = "OpenedDate")
    private Date openedDate;

    @Column(name = "Screen")
    private String screen;

}
