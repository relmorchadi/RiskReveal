package com.scor.rr.domain.enums;

public enum PLTPublishStatus {
    PURE("P"), POST_GROUP("G"), POST_INURED("I");

    final String value;

    PLTPublishStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
