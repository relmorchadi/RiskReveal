package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Task", schema = "dbo", catalog = "RiskReveal")
public class TaskEntity {
    private int taskId;
    private Integer jobId;
    private String taskType;
    private String taskParams;
    private String status;
    private Timestamp startedDate;
    private Timestamp finishedDate;

    @Id
    @Column(name = "taskId", nullable = false)
    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "jobId")
    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "taskType", length = 255)
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Basic
    @Column(name = "taskParams", length = 255)
    public String getTaskParams() {
        return taskParams;
    }

    public void setTaskParams(String taskParams) {
        this.taskParams = taskParams;
    }

    @Basic
    @Column(name = "status", length = 255)
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
        TaskEntity that = (TaskEntity) o;
        return taskId == that.taskId &&
                Objects.equals(jobId, that.jobId) &&
                Objects.equals(taskType, that.taskType) &&
                Objects.equals(taskParams, that.taskParams) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startedDate, that.startedDate) &&
                Objects.equals(finishedDate, that.finishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, jobId, taskType, taskParams, status, startedDate, finishedDate);
    }
}
