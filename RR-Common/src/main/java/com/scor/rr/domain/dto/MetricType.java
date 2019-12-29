package com.scor.rr.domain.dto;

public enum MetricType {
    AEP("AEP"), OEP("OEP"), AEPTvAR("AEPTvAR"), OEPTvAR("OEPTvAR");

    String value;

    MetricType(String value) {
        this.value=value;
    }

    public String getValue() {
        return value;
    }
}
