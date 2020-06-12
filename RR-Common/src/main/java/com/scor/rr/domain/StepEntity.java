package com.scor.rr.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Step")
public class StepEntity {

    private Long stepId;
    private TaskEntity task;
    private String stepCode;
    private Integer stepOrder;
    private String status;
    private Timestamp submittedDate;
    private Timestamp startedDate;
    private Timestamp finishedDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stepId", nullable = false)
    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "taskId")
    @JsonBackReference
    public TaskEntity getTask() {
        return task;
    }

    public void setTask(TaskEntity task) {
        this.task = task;
    }

    @Basic
    @Column(name = "stepName", nullable = true, length = 255)
    public String getStepName() {
        return stepCode;
    }

    public void setStepName(String stepName) {
        this.stepCode = stepName;
    }

    @Basic
    @Column(name = "stepOrder", nullable = true)
    public Integer getStepOrder() {
        return stepOrder;
    }

    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
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
        StepEntity that = (StepEntity) o;
        return stepId == that.stepId &&
                Objects.equals(task, that.task) &&
                Objects.equals(stepCode, that.stepCode) &&
                Objects.equals(stepOrder, that.stepOrder) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startedDate, that.startedDate) &&
                Objects.equals(finishedDate, that.finishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepId, task, stepCode, stepOrder, status, startedDate, finishedDate);
    }
}
