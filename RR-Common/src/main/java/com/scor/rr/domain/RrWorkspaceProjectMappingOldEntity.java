package com.scor.rr.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "RR-WorkspaceProjectMapping-old", schema = "dbo", catalog = "RiskReveal")
public class RrWorkspaceProjectMappingOldEntity {
    private String id;
    private String project;

    @Id
    @Basic
    @Column(name = "_id", nullable = false, length = 11)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Basic
    @Column(name = "project", nullable = false, length = 11)
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
        RrWorkspaceProjectMappingOldEntity that = (RrWorkspaceProjectMappingOldEntity) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(project, that.project);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, project);
    }
}
