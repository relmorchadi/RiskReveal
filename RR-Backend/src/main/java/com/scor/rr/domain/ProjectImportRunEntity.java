package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "ProjectImportRun", schema = "dbo", catalog = "RiskReveal")
public class ProjectImportRunEntity {
    private int projectImportRunId;
    private Integer runId;
    private Integer projectId;
    private String status;
    private Timestamp startDate;
    private Timestamp endDate;
    private Timestamp lossImportEndDate;
    private String importedBy;
    private String sourceConfigVendor;

    @Id
    @Column(name = "projectImportRunId", nullable = false)
    public int getProjectImportRunId() {
        return projectImportRunId;
    }

    public void setProjectImportRunId(int projectImportRunId) {
        this.projectImportRunId = projectImportRunId;
    }

    @Basic
    @Column(name = "runId", nullable = true)
    public Integer getRunId() {
        return runId;
    }

    public void setRunId(Integer runId) {
        this.runId = runId;
    }

    @Basic
    @Column(name = "projectId", nullable = true)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "startDate", nullable = true)
    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    @Basic
    @Column(name = "endDate", nullable = true)
    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    @Basic
    @Column(name = "lossImportEndDate", nullable = true)
    public Timestamp getLossImportEndDate() {
        return lossImportEndDate;
    }

    public void setLossImportEndDate(Timestamp lossImportEndDate) {
        this.lossImportEndDate = lossImportEndDate;
    }

    @Basic
    @Column(name = "importedBy", nullable = true, length = 255)
    public String getImportedBy() {
        return importedBy;
    }

    public void setImportedBy(String importedBy) {
        this.importedBy = importedBy;
    }

    @Basic
    @Column(name = "sourceConfigVendor", nullable = true, length = 255)
    public String getSourceConfigVendor() {
        return sourceConfigVendor;
    }

    public void setSourceConfigVendor(String sourceConfigVendor) {
        this.sourceConfigVendor = sourceConfigVendor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectImportRunEntity that = (ProjectImportRunEntity) o;
        return projectImportRunId == that.projectImportRunId &&
                Objects.equals(runId, that.runId) &&
                Objects.equals(projectId, that.projectId) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate) &&
                Objects.equals(lossImportEndDate, that.lossImportEndDate) &&
                Objects.equals(importedBy, that.importedBy) &&
                Objects.equals(sourceConfigVendor, that.sourceConfigVendor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectImportRunId, runId, projectId, status, startDate, endDate, lossImportEndDate, importedBy, sourceConfigVendor);
    }
}
