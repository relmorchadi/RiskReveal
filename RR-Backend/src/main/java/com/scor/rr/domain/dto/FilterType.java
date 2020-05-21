package com.scor.rr.domain.dto;

public enum FilterType {
    DATE("date"),
    BOOLEAN("boolean"),
    NUMBER("number"),
    TEXT("text");

    FilterType(String v) {
        this.v =v;
    }

    String v;

    String getValue() { return this.v;}
}
