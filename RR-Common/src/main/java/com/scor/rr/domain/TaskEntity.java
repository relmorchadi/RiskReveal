package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Task")
@AllArgsConstructor
@NoArgsConstructor
public class TaskEntity {

    private Long taskId;
    private Long jobExecutionId;
    private JobEntity job;
    private String taskType;
    private String taskParams;
    private String status;
    private Integer priority;
    private Date submittedDate;
    private Date startedDate;
    private Date finishedDate;

    private List<StepEntity> steps;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER)
    @JsonManagedReference
    public List<StepEntity> getSteps() {
        return steps;
    }

    public void setSteps(List<StepEntity> steps) {
        this.steps = steps;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taskId", nullable = false)
    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jobId")
    @JsonBackReference
    public JobEntity getJob() {
        return job;
    }

    public void setJob(JobEntity job) {
        this.job = job;
    }

    @Column(name = "jobExecutionId", nullable = true, length = 255)
    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public void setJobExecutionId(Long jobExecutionId) {
        this.jobExecutionId = jobExecutionId;
    }

    @Basic
    @Column(name = "taskType", nullable = true, length = 255)
    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    @Basic
    @Column(name = "startedDate", nullable = true)
    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    @Basic
    @Column(name = "finishedDate", nullable = true)
    public Date getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(Date finishedDate) {
        this.finishedDate = finishedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return taskId == that.taskId &&
                Objects.equals(job, that.job) &&
                Objects.equals(taskType, that.taskType) &&
                Objects.equals(taskParams, that.taskParams) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startedDate, that.startedDate) &&
                Objects.equals(finishedDate, that.finishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskId, job, taskType, taskParams, status, startedDate, finishedDate);
    }
}
