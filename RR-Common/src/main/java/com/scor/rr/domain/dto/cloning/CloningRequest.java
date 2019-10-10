package com.scor.rr.domain.dto.cloning;

import com.scor.rr.domain.InuringPackageEntity;
import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.ProjectEntity;
import com.scor.rr.domain.WorkspaceEntity;

import java.util.List;

public class CloningRequest {
    private WorkspaceEntity workspace;
    private List<ProjectEntity> projects;
    private List<InuringPackageEntity> inuringPackages;
    private List<PltHeaderEntity> scorPltHeaders;

    public WorkspaceEntity getWorkspace() {
        return workspace;
    }

    public void setWorkspace(WorkspaceEntity workspace) {
        this.workspace = workspace;
    }

    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }

    public List<InuringPackageEntity> getInuringPackages() {
        return inuringPackages;
    }

    public void setInuringPackages(List<InuringPackageEntity> inuringPackages) {
        this.inuringPackages = inuringPackages;
    }

    public List<PltHeaderEntity> getScorPltHeaders() {
        return scorPltHeaders;
    }

    public void setScorPltHeaders(List<PltHeaderEntity> scorPltHeaders) {
        this.scorPltHeaders = scorPltHeaders;
    }
}
