package com.scor.rr.request;

import lombok.Data;

import java.math.BigDecimal;


public class InuringContractLayerPerilLimitCreationRequest {

    private String contractType;

    private int inuringContractLayerId;

    private String peril;

    private BigDecimal limit;

    public InuringContractLayerPerilLimitCreationRequest(int inuringContractLayerId, String peril, BigDecimal limit,String contractType) {
        this.inuringContractLayerId = inuringContractLayerId;
        this.peril = peril;
        this.limit = limit;
        this.contractType = contractType;
    }

    public InuringContractLayerPerilLimitCreationRequest() {
    }

    public int getInuringContractLayerId() {
        return inuringContractLayerId;
    }

    public String getContractType() {return contractType; }

    public String getPeril() {
        return peril;
    }

    public BigDecimal getLimit() {
        return limit;
    }
}
