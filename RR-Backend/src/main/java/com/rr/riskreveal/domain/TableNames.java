package com.rr.riskreveal.domain;

public enum TableNames {
    CEDANT("cedant"),
    COUNTRY("country"),
    TREATY("treaty"),
    YEAR("treaty");

    private String value;
    TableNames(String value) {
        this.value= value;
    }
    public String getValue() {
        return value;
    }
}
