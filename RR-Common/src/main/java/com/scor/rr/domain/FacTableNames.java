package com.scor.rr.domain;

public enum FacTableNames {
    CLIENT("client"),
    UW_YEAR("uw_year"),
    CONTRACT_CODE("contract_code"),
    CONTRACT_NAME("contract_name"),
    UW_ANALYSIS("uw_analysis"),
    CAREQUEST_ID("carequest_id"),
    CAR_STATUS("car_status"),
    ASSIGNED_TO("assigned_to");

    private String value;
    FacTableNames(String value) {
        this.value= value;
    }
    public String getValue() {
        return value;
    }
}
