package com.scor.rr.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCodename {
    UNKNOWN("ERROR.UNKNOWN");
    private final String exception;

    ExceptionCodename(final String exception) {
        this.exception = exception;
    }

    @JsonValue
    public String exception() {
        return this.exception;
    }
}
