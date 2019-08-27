package com.scor.rr.domain.TagsManager;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "TmProject", schema = "PRD", catalog = "RR")
public class TestProject {
    private int projectId;
    private String projectIdMongo;
    private int workspaceId;
    private String workspaceIdMongo;
    private String projectName;
    private String projectDescription;
    private String projectAssigned;
    private String clonedProject;
    private String cloneSourceProject;
    private String projectSourceId;
    private String projectMgaId;
    private String masterProject;
    private String linkedProject;
    private String publishedProject;
    private String projectCreatedBy;
    private Timestamp projectCreated;

    @Id
    @Column(name = "ProjectId")
    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "ProjectIdMongo")
    public String getProjectIdMongo() {
        return projectIdMongo;
    }

    public void setProjectIdMongo(String projectIdMongo) {
        this.projectIdMongo = projectIdMongo;
    }

    @Basic
    @Column(name = "WorkspaceId")
    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Basic
    @Column(name = "WorkspaceIdMongo")
    public String getWorkspaceIdMongo() {
        return workspaceIdMongo;
    }

    public void setWorkspaceIdMongo(String workspaceIdMongo) {
        this.workspaceIdMongo = workspaceIdMongo;
    }

    @Basic
    @Column(name = "ProjectName")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Basic
    @Column(name = "ProjectDescription")
    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    @Basic
    @Column(name = "ProjectAssigned")
    public String getProjectAssigned() {
        return projectAssigned;
    }

    public void setProjectAssigned(String projectAssigned) {
        this.projectAssigned = projectAssigned;
    }

    @Basic
    @Column(name = "ClonedProject")
    public String getClonedProject() {
        return clonedProject;
    }

    public void setClonedProject(String clonedProject) {
        this.clonedProject = clonedProject;
    }

    @Basic
    @Column(name = "cloneSourceProject")
    public String getCloneSourceProject() {
        return cloneSourceProject;
    }

    public void setCloneSourceProject(String cloneSourceProject) {
        this.cloneSourceProject = cloneSourceProject;
    }

    @Basic
    @Column(name = "ProjectSourceId")
    public String getProjectSourceId() {
        return projectSourceId;
    }

    public void setProjectSourceId(String projectSourceId) {
        this.projectSourceId = projectSourceId;
    }

    @Basic
    @Column(name = "ProjectMgaId")
    public String getProjectMgaId() {
        return projectMgaId;
    }

    public void setProjectMgaId(String projectMgaId) {
        this.projectMgaId = projectMgaId;
    }

    @Basic
    @Column(name = "MasterProject")
    public String getMasterProject() {
        return masterProject;
    }

    public void setMasterProject(String masterProject) {
        this.masterProject = masterProject;
    }

    @Basic
    @Column(name = "LinkedProject")
    public String getLinkedProject() {
        return linkedProject;
    }

    public void setLinkedProject(String linkedProject) {
        this.linkedProject = linkedProject;
    }

    @Basic
    @Column(name = "PublishedProject")
    public String getPublishedProject() {
        return publishedProject;
    }

    public void setPublishedProject(String publishedProject) {
        this.publishedProject = publishedProject;
    }

    @Basic
    @Column(name = "ProjectCreatedBy")
    public String getProjectCreatedBy() {
        return projectCreatedBy;
    }

    public void setProjectCreatedBy(String projectCreatedBy) {
        this.projectCreatedBy = projectCreatedBy;
    }

    @Basic
    @Column(name = "ProjectCreated")
    public Timestamp getProjectCreated() {
        return projectCreated;
    }

    public void setProjectCreated(Timestamp projectCreated) {
        this.projectCreated = projectCreated;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectIdMongo, workspaceId, workspaceIdMongo, projectName, projectDescription, projectAssigned, clonedProject, cloneSourceProject, projectSourceId, projectMgaId, masterProject, linkedProject, publishedProject, projectCreatedBy, projectCreated);
    }
}
