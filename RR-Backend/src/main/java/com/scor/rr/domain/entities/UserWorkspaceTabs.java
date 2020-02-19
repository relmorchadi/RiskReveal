package com.scor.rr.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "ZZ_UserWorkspaceTabs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserWorkspaceTabs {

    @Id
    @Column(name = "UserWorkspaceTabsId")
    private Long userWorkspaceTabsId;

    @Column(name = "WorkspaceId")
    private Long workspaceId;

    @Column(name = "UserCode", length = 10)
    private String userCode;

    @Column(name = "OpenedDate")
    private Date openedDate;

}
