package com.scor.rr.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InuringContractLayerPerilLimitCreationRequest {

    private int inuringContractLayerId;

    private String peril;

    private BigDecimal limit;


}
