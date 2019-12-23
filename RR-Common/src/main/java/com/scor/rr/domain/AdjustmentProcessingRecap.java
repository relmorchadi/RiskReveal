package com.scor.rr.domain;

import javax.persistence.*;
import java.sql.Time;
import java.util.Date;

@javax.persistence.Entity
@Table(name = "AdjustmentProcessingRecap")
public class AdjustmentProcessingRecap {
    private Integer id;
    private Integer entity;
    private AdjustmentNode adjustmentNode;
    private String adjustmentTypeCode;
    private Boolean capped;
    private String adjustmentParamRecap;
    private String inputFile;
    private String outputFile;
    private Date submittedDate;
    private String submittedBy;
    private Time execTime;
    private String execStatus;

    @Id
    @Column(name = "AdjustmentProcessingRecapId", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "Entity")
    public Integer getEntity() {
        return entity;
    }

    public void setEntity(Integer entity) {
        this.entity = entity;
    }

    @ManyToOne
    @JoinColumn(name = "AdjustmentNodeId", referencedColumnName = "AdjustmentNodeId")
    public AdjustmentNode getAdjustmentNode() {
        return adjustmentNode;
    }

    public void setAdjustmentNode(AdjustmentNode adjustmentNode) {
        this.adjustmentNode = adjustmentNode;
    }

    @Column(name = "AdjustmentTypeCode")
    public String getAdjustmentTypeCode() {
        return adjustmentTypeCode;
    }

    public void setAdjustmentTypeCode(String adjustmentTypeCode) {
        this.adjustmentTypeCode = adjustmentTypeCode;
    }

    @Column(name = "Capped")
    public Boolean getCapped() {
        return capped;
    }

    public void setCapped(Boolean capped) {
        this.capped = capped;
    }

    @Column(name = "AdjustmentParamRecap")
    public String getAdjustmentParamRecap() {
        return adjustmentParamRecap;
    }

    public void setAdjustmentParamRecap(String adjustmentParamRecap) {
        this.adjustmentParamRecap = adjustmentParamRecap;
    }

    @Column(name = "InputFile")
    public String getInputFile() {
        return inputFile;
    }

    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }

    @Column(name = "OutputFile")
    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    @Column(name = "SubmittedDate")
    public Date getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }

    @Column(name = "SubmittedBy")
    public String getSubmittedBy() {
        return submittedBy;
    }

    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @Column(name = "ExecTime")
    public Time getExecTime() {
        return execTime;
    }

    public void setExecTime(Time execTime) {
        this.execTime = execTime;
    }

    @Column(name = "ExecStatus")
    public String getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }
}
