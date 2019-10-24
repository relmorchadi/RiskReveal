package com.scor.rr.request;

import lombok.Data;

import java.util.Date;

@Data
public class InuringContractNodeCreationRequest {

    private int inuringPackageId;
    private String contractTypeCode;
    private Date inceptionDate;
    private Date expiationDate;

}
