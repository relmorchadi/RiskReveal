package com.scor.rr.domain.enums;

public enum Operator {
    AND("AND"),
    OR("OR"),
    LIKE("LIKE"),
    EQUAL("EQUAL");

    public final String value;

    Operator(String value) {
        this.value = value;
    }

    public String getValue() { return this.value;}
}
