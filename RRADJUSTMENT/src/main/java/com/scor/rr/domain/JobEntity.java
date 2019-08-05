package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Job", schema = "dbo", catalog = "RiskReveal")
public class JobEntity {
    private int jobId;
    private Integer submittedByUser;
    private Timestamp submittedDate;
    private String jobType;
    private String jobParams;
    private String status;
    private Timestamp startedDate;
    private Timestamp finishedDate;

    @Id
    @Column(name = "jobId", nullable = false)
    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "submittedByUser")
    public Integer getSubmittedByUser() {
        return submittedByUser;
    }

    public void setSubmittedByUser(Integer submittedByUser) {
        this.submittedByUser = submittedByUser;
    }

    @Basic
    @Column(name = "submittedDate")
    public Timestamp getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Timestamp submittedDate) {
        this.submittedDate = submittedDate;
    }

    @Basic
    @Column(name = "jobType", length = 255,insertable = false ,updatable = false)
    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    @Basic
    @Column(name = "jobParams", length = 255,insertable = false ,updatable = false)
    public String getJobParams() {
        return jobParams;
    }

    public void setJobParams(String jobParams) {
        this.jobParams = jobParams;
    }

    @Basic
    @Column(name = "status", length = 255,insertable = false ,updatable = false)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "startedDate")
    public Timestamp getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Timestamp startedDate) {
        this.startedDate = startedDate;
    }

    @Basic
    @Column(name = "finishedDate")
    public Timestamp getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Timestamp finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobEntity jobEntity = (JobEntity) o;
        return jobId == jobEntity.jobId &&
                Objects.equals(submittedByUser, jobEntity.submittedByUser) &&
                Objects.equals(submittedDate, jobEntity.submittedDate) &&
                Objects.equals(jobType, jobEntity.jobType) &&
                Objects.equals(jobParams, jobEntity.jobParams) &&
                Objects.equals(status, jobEntity.status) &&
                Objects.equals(startedDate, jobEntity.startedDate) &&
                Objects.equals(finishedDate, jobEntity.finishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jobId, submittedByUser, submittedDate, jobType, jobParams, status, startedDate, finishedDate);
    }
}
