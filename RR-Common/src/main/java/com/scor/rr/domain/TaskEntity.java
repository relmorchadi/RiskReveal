package com.scor.rr.domain;

import com.scor.rr.domain.enums.JobStatus;
import com.scor.rr.domain.enums.JobType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Task")
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    private Long taskId;
    private JobEntity jobId;
    private JobType taskCode;
    private String taskParams;
    private JobStatus status;
    private Integer priority;
    private Timestamp submittedDate;
    private Timestamp startedDate;
    private Timestamp finishedDate;

    @Id
    @Column(name = "taskId", nullable = false)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "jobId")
    public JobEntity getJobId() {
        return jobId;
    }

    public void setJobId(JobEntity jobId) {
        this.jobId = jobId;
    }

    @Basic
    @Column(name = "taskCode", nullable = true, length = 255)
    public JobType getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(JobType taskCode) {
        this.taskCode = taskCode;
    }

    @Basic
    @Column(name = "taskParams", nullable = true, length = 255)
    public String getTaskParams() {
        return taskParams;
    }

    public void setTaskParams(String taskParams) {
        this.taskParams = taskParams;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 255)
    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    @Basic
    @Column(name = "priority", nullable = true, length = 255)
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Basic
    @Column(name = "submittedDate", nullable = true)
    public Timestamp getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Timestamp submittedDate) {
        this.submittedDate = submittedDate;
    }

    @Basic
    @Column(name = "startedDate", nullable = true)
    public Timestamp getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Timestamp startedDate) {
        this.startedDate = startedDate;
    }

    @Basic
    @Column(name = "finishedDate", nullable = true)
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
                Objects.equals(taskCode, that.taskCode) &&
                Objects.equals(taskParams, that.taskParams) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startedDate, that.startedDate) &&
                Objects.equals(finishedDate, that.finishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, jobId, taskCode, taskParams, status, startedDate, finishedDate);
    }
}
