package com.scor.rr.request;

import java.math.BigDecimal;

public class InuringContractLayerReinstatementDetailCreationRequest {

    private int inuringContractLayerId;

    private int reinstatementsRank;

    private int reinstatementsNumber;

    private BigDecimal reinstatatementsCharge;

    public InuringContractLayerReinstatementDetailCreationRequest(int inuringContractLayerId, int reinstatementsRank, int reinstatementsNumber, BigDecimal reinstatatementsCharge) {
        this.inuringContractLayerId = inuringContractLayerId;
        this.reinstatementsRank = reinstatementsRank;
        this.reinstatementsNumber = reinstatementsNumber;
        this.reinstatatementsCharge = reinstatatementsCharge;
    }

    public InuringContractLayerReinstatementDetailCreationRequest() {
    }

    public int getInuringContractLayerId() {
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
