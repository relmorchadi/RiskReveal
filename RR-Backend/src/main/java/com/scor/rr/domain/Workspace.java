package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "[RR-Workspace]", schema = "dbo")
@Data
public class Workspace implements Serializable{

  @Embeddable
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  static public class WorkspaceId implements Serializable {
    @Column(name = "workspaceContextCode")
    public String workspaceContextCode;
    @Column(name = "workspaceUwYear")
    public Integer workspaceUwYear;
  }
  @EmbeddedId
  WorkspaceId workspaceId;
  private String id;
  // private String audit;
  private String cedantName;
  private String contractId;
  private String workspaceContextFlag;
  private String workspaceName;


}
