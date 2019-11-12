package com.scor.rr.domain.TargetBuild;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Project", schema = "tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "WorkspaceId")
    private Long workspaceId;

    @Column(name = "ProjectImportRunId")
    private Integer projectImportRunId;

    @Column(name = "RMSModelDataSourceId")
    private Integer rmsModelDataSourceId;

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "ProjectDescription")
    private String projectDescription;

    @Column(name = "MasterFlag")
    private Boolean masterFlag;

    @Column(name = "LinkFlag")
    private Boolean linkFlag;

    @Column(name = "PublishFlag")
    private Boolean publishFlag;

    @Column(name = "ClonedFlag")
    private Boolean clonedFlag;

    @Column(name = "PostInuredFlag")
    private Boolean postInuredFlag;

    @Column(name = "MgaFlag")
    private Boolean mgaFlag;

    @Column(name = "AssignedTo")
    private String assignedTo;

    @Column(name = "CreationDate")
    private Date creationDate;

    @Column(name = "ReceptionDate")
    private Date receptionDate;

    @Column(name = "DueDate")
    private Date dueDate;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "LinkedSourceProjectId")
    private Integer linkedSourceProjectId;

    @Column(name = "CloneSourceProjectId")
    private Integer cloneSourceProjectId;

    @Column(name = "Deleted")
    private Boolean deleted;

    @Column(name = "DeletedOn")
    private Date deletedOn;

    @Column(name = "DeletedDue")
    private String deletedDue;

    @Column(name = "DeletedBy")
    private String deletedBy;

}
