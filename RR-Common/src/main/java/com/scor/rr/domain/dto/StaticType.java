package com.scor.rr.domain.dto;

public enum  StaticType {
    CoefOfVariance("coef of variance"),stdDev("stddev"),averageAnnualLoss("average Annual Loss");
    String value;

    StaticType(String value) {
        this.value=value;
    }


    public String getValue() {
        return value;
    }
}
