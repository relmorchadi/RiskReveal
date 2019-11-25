package com.scor.rr.domain.riskReveal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Workspace")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Workspace implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "WorkspaceId")
  private Long workspaceId;

  @Column(name = "RREntity")
  private Integer entity;

  @Column(name = "WorkspaceContextCode")
  private String workspaceContextCode;

  @Column(name = "WorkspaceUwYear")
  private Integer workspaceUwYear;

  @Column(name = "WorkspaceName")
  private String workspaceName;

  @Column(name = "CedantName")
  private String cedantName;

  @OneToMany
  @JoinColumn(name = "workspaceId")
  private List<Project> projects;

//  public Workspace(ContractSearchResult c) {
//    this.entity = 1;
//    this.workspaceContextCode = c.getWorkSpaceId();
//    this.workspaceUwYear = c.getUwYear();
//    this.workspaceName = c.getWorkspaceName();
//    this.cedantName = c.getCedantName();
//  }
}

