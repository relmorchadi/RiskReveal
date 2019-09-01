package com.scor.rr.domain.TagsManager;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "TmWorkspace", schema = "PRD", catalog = "RR")
public class TestWorkspace {
    private int workspaceId;
    private String workspaceIdMongo;
    private String workspaceContext;
    private String workspaceCode;
    private Integer workspaceYear;

    @Id
    @Column(name = "WorkspaceID")
    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    @Basic
    @Column(name = "WorkspaceIDMongo")
    public String getWorkspaceIdMongo() {
        return workspaceIdMongo;
    }

    public void setWorkspaceIdMongo(String workspaceIdMongo) {
        this.workspaceIdMongo = workspaceIdMongo;
    }

    @Basic
    @Column(name = "WorkspaceContext")
    public String getWorkspaceContext() {
        return workspaceContext;
    }

    public void setWorkspaceContext(String workspaceContext) {
        this.workspaceContext = workspaceContext;
    }

    @Basic
    @Column(name = "WorkspaceCode")
    public String getWorkspaceCode() {
        return workspaceCode;
    }

    public void setWorkspaceCode(String workspaceCode) {
        this.workspaceCode = workspaceCode;
    }

    @Basic
    @Column(name = "WorkspaceYear")
    public Integer getWorkspaceYear() {
        return workspaceYear;
    }

    public void setWorkspaceYear(Integer workspaceYear) {
        this.workspaceYear = workspaceYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(workspaceId, workspaceIdMongo, workspaceContext, workspaceCode, workspaceYear);
    }
}
