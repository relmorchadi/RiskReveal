package com.scor.rr.domain;

import javax.persistence.*;
import javax.persistence.Entity;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "Step")
public class StepEntity {
    private int stepId;
    private Integer taskId;
    private String stepName;
    private Integer stepOrder;
    private String stepParams;
    private String status;
    private Timestamp startedDate;
    private Timestamp finishedDate;

    @Id
    @Column(name = "stepId", nullable = false)
    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    @Basic
    @Column(name = "taskId", nullable = true)
    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    @Basic
    @Column(name = "stepName", nullable = true, length = 255)
    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
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
    @Column(name = "stepParams", nullable = true, length = 255)
    public String getStepParams() {
        return stepParams;
    }

    public void setStepParams(String stepParams) {
        this.stepParams = stepParams;
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
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(stepName, that.stepName) &&
                Objects.equals(stepOrder, that.stepOrder) &&
                Objects.equals(stepParams, that.stepParams) &&
                Objects.equals(status, that.status) &&
                Objects.equals(startedDate, that.startedDate) &&
                Objects.equals(finishedDate, that.finishedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stepId, taskId, stepName, stepOrder, stepParams, status, startedDate, finishedDate);
    }
}
