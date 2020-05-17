package com.scor.rr.domain.enums;

public enum NumberOperator {
    EQUAL("EQUAL"),
    LT("LESS THAN"),
    GT("GREATER THAN");

    String val;

    NumberOperator(String v) {
        this.val = v;
    }

    public String getNumberOperator() {
        return this.val;
    }
}
