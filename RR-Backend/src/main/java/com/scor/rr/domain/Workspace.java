package com.scor.rr.domain;

import com.scor.rr.util.StringPrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

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
  @Embedded
  WorkspaceId workspaceId;
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workspace_seq")
  @GenericGenerator(
          name = "workspace_seq",
          strategy = "com.scor.rr.util.StringPrefixedSequenceIdGenerator",
          parameters = {
                  @org.hibernate.annotations.Parameter(name = SequenceStyleGenerator.INITIAL_PARAM,value = "18891"),
                  @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "1"),
                  @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "W-"),
                  @org.hibernate.annotations.Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%09d") })
  private String id;
  // private String audit;
  private String cedantName;
  private String contractId;
  private String workspaceContextFlag;
  private String workspaceName;


}
