package com.scor.rr.request;

import java.util.Date;

public class InuringContractNodeCreationRequest {

    private int inuringPackageId;
    private String contractTypeCode;
    private Date inceptionDate;
    private Date expiationDate;

    public InuringContractNodeCreationRequest(int inuringPackageId,String contractTypeCode,Date inceptionDate,Date expirationDate) {
        this.inuringPackageId = inuringPackageId;
        this.contractTypeCode = contractTypeCode;
        this.expiationDate = expirationDate;
        this.inceptionDate = inceptionDate;
    }

    public InuringContractNodeCreationRequest() {
    }

    public String getContractTypeCode() {
        return contractTypeCode;
    }

    public int getInuringPackageId() {
        return inuringPackageId;
    }

    public Date getInceptionDate() {
        return inceptionDate;
    }

    public Date getExpiationDate() {
        return expiationDate;
    }

}
