package com.scor.rr.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Project")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProjectId")
    private Long projectId;

    @Column(name = "Entity")
    private Integer entity;

    @Column(name = "WorkspaceId")
    private Long workspaceId;

    @Column(name = "ProjectImportRunId")
    private Long projectImportRunId;

    @Column(name = "RMSModelDataSourceId")
    private Integer rmsModelDataSourceId;

    @Column(name = "ProjectName")
    private String projectName;

    @Column(name = "ProjectDescription")
    private String projectDescription;

    @Column(name = "IsMaster")
    private Boolean isMaster;

    @Column(name = "IsLinked")
    private Boolean isLinked;

    @Column(name = "IsPublished")
    private Boolean isPublished;

    @Column(name = "IsCloned")
    private Boolean isCloned;

    @Column(name = "IsPostInured")
    private Boolean isPostInured;

    @Column(name = "IsMGA")
    private Boolean isMGA;

    @Column(name = "AssignedTo")
    private Long assignedTo;

    @Column(name = "CreationDate")
    private Date creationDate;

    @Column(name = "ReceptionDate")
    private Date receptionDate;

    @Column(name = "DueDate")
    private Date dueDate;

    @Column(name = "CreatedBy")
    private String createdBy;

    @Column(name = "LinkedSourceProjectId")
    private Long linkedSourceProjectId;

    @Column(name = "CloneSourceProjectId")
    private Long cloneSourceProjectId;

    @Column(name = "Deleted")
    private Boolean deleted;

    @Column(name = "DeletedOn")
    private Date deletedOn;

    @Column(name = "DeletedDue")
    private String deletedDue;

    @Column(name = "DeletedBy")
    private String deletedBy;

    public void initProject(Long workspaceId) {
        this.projectId = null;
        this.workspaceId = workspaceId;
        this.isMaster = false;
        this.isCloned = false;
        this.isLinked = false;
        this.isPostInured = false;
        this.isPublished = false;
        this.isMGA= false;
        this.deleted= false;
    }

    public void setWorkspaceByFkWorkspaceId(WorkspaceEntity workspaceEntity) {
        this.workspaceId = workspaceEntity.getWorkspaceId();
    }

    public void setFkCloneSourceProjectId(ProjectEntity projectEntity) {
        this.projectId = projectEntity.getProjectId();
    }
}
