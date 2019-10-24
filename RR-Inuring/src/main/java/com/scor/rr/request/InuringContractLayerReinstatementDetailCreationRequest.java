package com.scor.rr.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class InuringContractLayerReinstatementDetailCreationRequest {

    private int inuringContractLayerId;

    private int reinstatementsRank;

    private int reinstatementsNumber;

    private BigDecimal reinstatatementsCharge;




}
