package com.scor.rr.request;

import lombok.Data;

import java.math.BigDecimal;


public class InuringContractLayerReinstatementDetailCreationRequest {

    private long inuringContractLayerId;

    private int reinstatementsRank;

    private int reinstatementsNumber;

    private BigDecimal reinstatatementsCharge;

    public InuringContractLayerReinstatementDetailCreationRequest(long inuringContractLayerId, int reinstatementsRank, int reinstatementsNumber, BigDecimal reinstatatementsCharge) {
        this.inuringContractLayerId = inuringContractLayerId;
        this.reinstatementsRank = reinstatementsRank;
        this.reinstatementsNumber = reinstatementsNumber;
        this.reinstatatementsCharge = reinstatatementsCharge;
    }

    public InuringContractLayerReinstatementDetailCreationRequest() {
    }

    public long getInuringContractLayerId() {
        return inuringContractLayerId;
    }

    public int getReinstatementsRank() {
        return reinstatementsRank;
    }

    public int getReinstatementsNumber() {
        return reinstatementsNumber;
    }

    public BigDecimal getReinstatatementsCharge() {
        return reinstatatementsCharge;
    }
}
