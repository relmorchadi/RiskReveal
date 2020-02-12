package com.scor.rr.domain;

public enum TreatyTableNames {
    CLIENT_CODE("cedant_code"),
    CLIENT_NAME("cedant_name"),
    COUNTRY_NAME("country"),
    CONTRACT_CODE("workspace_id"),
    CONTRACT_NAME("workspace_name"),
//    TREATY("treaty"),
    UW_YEAR("year");

    private String value;
    TreatyTableNames(String value) {
        this.value= value;
    }
    public String getValue() {
        return value;
    }
}
