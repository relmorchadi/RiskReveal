package com.scor.rr.domain.dto;

import java.util.Date;

public class DashBoardFilter {

    private Long id;

    private String carRequestId;

    private Date lastUpdateDate;

    private Integer lastUpdatedBy;

    private String requestedByFirstName;

    private String requestedByLastName;

    private String requestedByFullName;

    private Date creationDate;

    private String cedantName;

    private String uwAnalysis;

    private String facSource;

    private String Lob;

    private String businessType;

    private Integer endorsementNumber;

    private String sector;

    private String subsidiary;

    private Integer uwYear;

    /*uwAnalysisContractDate*/

    private String assignedAnalyst;

    private Integer uwOrder;

    private String label;

    private String facNumber;

    private Long projectId;

    private String contractId;

    private String contractName;

    /* Insured ??*/

    private String carStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCarRequestId() {
        return carRequestId;
    }

    public void setCarRequestId(String carRequestId) {
        this.carRequestId = carRequestId;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public void setLastUpdateDate(Date lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    public Integer getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(Integer lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getRequestedByFirstName() {
        return requestedByFirstName;
    }

    public void setRequestedByFirstName(String requestedByFirstName) {
        this.requestedByFirstName = requestedByFirstName;
    }

    public String getRequestedByLastName() {
        return requestedByLastName;
    }

    public void setRequestedByLastName(String requestedByLastName) {
        this.requestedByLastName = requestedByLastName;
    }

    public String getRequestedByFullName() {
        return requestedByFullName;
    }

    public void setRequestedByFullName(String requestedByFullName) {
        this.requestedByFullName = requestedByFullName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getCedantName() {
        return cedantName;
    }

    public void setCedantName(String cedantName) {
        this.cedantName = cedantName;
    }

    public String getUwAnalysis() {
        return uwAnalysis;
    }

    public void setUwAnalysis(String uwAnalysis) {
        this.uwAnalysis = uwAnalysis;
    }

    public String getFacSource() {
        return facSource;
    }

    public void setFacSource(String facSource) {
        this.facSource = facSource;
    }

    public String getLob() {
        return Lob;
    }

    public void setLob(String lob) {
        Lob = lob;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public Integer getEndorsementNumber() {
        return endorsementNumber;
    }

    public void setEndorsementNumber(Integer endorsementNumber) {
        this.endorsementNumber = endorsementNumber;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSubsidiary() {
        return subsidiary;
    }

    public void setSubsidiary(String subsidiary) {
        this.subsidiary = subsidiary;
    }

    public Integer getUwYear() {
        return uwYear;
    }

    public void setUwYear(Integer uwYear) {
        this.uwYear = uwYear;
    }

    public String getAssignedAnalyst() {
        return assignedAnalyst;
    }

    public void setAssignedAnalyst(String assignedAnalyst) {
        this.assignedAnalyst = assignedAnalyst;
    }

    public Integer getUwOrder() {
        return uwOrder;
    }

    public void setUwOrder(Integer uwOrder) {
        this.uwOrder = uwOrder;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFacNumber() {
        return facNumber;
    }

    public void setFacNumber(String facNumber) {
        this.facNumber = facNumber;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }
}
