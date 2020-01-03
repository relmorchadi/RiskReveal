package com.scor.rr.domain.enums;

public enum XLTOT {
    ORIGINAL("O"), TARGET("T");

    final String value;

    XLTOT(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
