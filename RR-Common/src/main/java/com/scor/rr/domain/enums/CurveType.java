package com.scor.rr.domain.enums;

public enum CurveType {

    AEP("AEP"),
    AEPTVAR("AEP-TVAR"),
    OEP("OEP"),
    OEPTVAR("OEP-TVAR");

    private String curveType;

    CurveType(String curveType) {
        this.curveType = curveType != null ? curveType : "OEP";
    }

    public String getCurveType() {
        return curveType;
    }

}
