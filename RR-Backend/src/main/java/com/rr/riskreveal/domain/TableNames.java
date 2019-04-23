package com.rr.riskreveal.domain;

public enum TableNames {
    CEDANT_CODE("cedant_code"),
    CEDANT_NAME("cedant_name"),
    COUNTRY("country"),
    WORKSPACE_ID("workspace_id"),
    WORKSPACE_NAME("workspace_name"),
//    TREATY("treaty"),
    YEAR("year");

    private String value;
    TableNames(String value) {
        this.value= value;
    }
    public String getValue() {
        return value;
    }
}
