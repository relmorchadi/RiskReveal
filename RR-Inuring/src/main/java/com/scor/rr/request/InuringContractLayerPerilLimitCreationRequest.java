package com.scor.rr.request;

import lombok.Data;

import java.math.BigDecimal;


public class InuringContractLayerPerilLimitCreationRequest {

    private int inuringContractLayerId;

    private String peril;

    private BigDecimal limit;

    public InuringContractLayerPerilLimitCreationRequest(int inuringContractLayerId, String peril, BigDecimal limit) {
        this.inuringContractLayerId = inuringContractLayerId;
        this.peril = peril;
        this.limit = limit;
    }

    public InuringContractLayerPerilLimitCreationRequest() {
    }

    public int getInuringContractLayerId() {
        return inuringContractLayerId;
    }

    public String getPeril() {
        return peril;
    }

    public BigDecimal getLimit() {
        return limit;
    }
}
