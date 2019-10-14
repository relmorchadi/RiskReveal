package com.scor.rr.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Workspace", schema = "search_tb")
@Data
@NoArgsConstructor
public class Workspace implements Serializable {

    @Id
    @GeneratedValue
    private Long workspaceId;
    private Integer entity;
    private String workspaceContextCode;
    private Integer workspaceUwYear;
    private String workspaceName;
    private String cedantName;

    @OneToMany
    @JoinColumn(name = "workspaceId")
    private List<Project> projects;

    public Workspace(ContractSearchResult c) {
        this.entity = 1;
        this.workspaceContextCode = c.getWorkSpaceId();
        this.workspaceUwYear = c.getUwYear();
        this.workspaceName = c.getWorkspaceName();
        this.cedantName = c.getCedantName();
    }

}
