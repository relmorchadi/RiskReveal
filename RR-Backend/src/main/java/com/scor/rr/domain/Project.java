package com.scor.rr.domain;

import com.scor.rr.util.StringPrefixedSequenceIdGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Project", schema = "search_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Project {

  @Id
  @GeneratedValue
  private Long projectId;
  private Integer entity;

  // FKs
  private Long workspaceId;
  private Long projectImportRunId;
  private Long rmsModelDataSourceId;
  //

  private String projectName;
  private String projectDescription;


  private Boolean masterFlag;
  private Boolean linkFlag;
  private Boolean publishFlag;
  private Boolean clonedFlag;
  private Boolean postInuredFlag;
  private Boolean mgaFlag;

  private String assignedTo;
  private Date creationDate=new Date();
  private Date receptionDate=new Date();
  private Date dueDate;
  private String createdBy;

  private Integer linkedSourceProjectId;
  private Integer cloneSourceProjectId;
  private Boolean deleted;
  private Date deletedOn;
  private String deletedDue;
  private String deletedBy;

}
