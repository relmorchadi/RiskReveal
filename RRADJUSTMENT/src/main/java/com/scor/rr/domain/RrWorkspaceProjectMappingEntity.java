package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RR-WorkspaceProjectMapping", schema = "dbo", catalog = "RiskReveal")
public class RrWorkspaceProjectMappingEntity {
    private String workspace;
    private String project;

    @Id
    @Basic
    @Column(name = "workspace", length = 255,insertable = false ,updatable = false)
    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    @Basic
    @Column(name = "project", length = 255,insertable = false ,updatable = false)
    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RrWorkspaceProjectMappingEntity that = (RrWorkspaceProjectMappingEntity) o;
        return Objects.equals(workspace, that.workspace) &&
                Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspace, project);
    }
}
