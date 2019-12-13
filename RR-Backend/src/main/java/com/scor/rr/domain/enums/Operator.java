package com.scor.rr.domain.enums;

public enum Operator {
    LIKE("LIKE"),
    EQUAL("EQUAL");

    public final String value;

    private Operator(String value) {
        this.value = value;
    }
}
