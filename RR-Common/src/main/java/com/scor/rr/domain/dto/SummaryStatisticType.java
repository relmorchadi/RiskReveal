package com.scor.rr.domain.dto;

public enum SummaryStatisticType {
    coefOfVariance("coef of variance"), stdDev("stddev"), averageAnnualLoss("average Annual Loss");
    String value;

    SummaryStatisticType(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
