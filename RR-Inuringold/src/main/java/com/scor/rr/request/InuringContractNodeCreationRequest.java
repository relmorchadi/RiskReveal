package com.scor.rr.request;

import lombok.Data;

import java.util.Date;


public class InuringContractNodeCreationRequest {

    private int inuringPackageId;
    private String contractTypeCode;
    private Date inceptionDate;
    private Date expiationDate;

    public InuringContractNodeCreationRequest(int inuringPackageId, String contractTypeCode, Date inceptionDate, Date expiationDate) {
        this.inuringPackageId = inuringPackageId;
        this.contractTypeCode = contractTypeCode;
        this.inceptionDate = inceptionDate;
        this.expiationDate = expiationDate;
    }

    public InuringContractNodeCreationRequest() {
    }

    public int getInuringPackageId() {
        return inuringPackageId;
    }

    public String getContractTypeCode() {
        return contractTypeCode;
    }

    public Date getInceptionDate() {
        return inceptionDate;
    }

    public Date getExpiationDate() {
        return expiationDate;
    }
}
