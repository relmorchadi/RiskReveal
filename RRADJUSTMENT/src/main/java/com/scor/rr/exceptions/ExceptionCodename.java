package com.scor.rr.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCodename {
    UNKNOWN("ERROR.UNKNOWN"),PLTNOTFOUNT("PLT NOT FOUND"),NODENOTFOUND("NODE NOT FOUND"),TYPENOTFOUND("ADJUSTMENT TYPE NOT FOUND"),BASISNOTFOUND("ADJUSTMENT BASIS NOT FOUND"),
    STATENOTFOUND("ADJUSTMENT STATE NOT FOUND"),THREADNOTFOUND("ADJUSTMENT THREAD NOT FOUND");
    private final String exception;

    ExceptionCodename(final String exception) {
        this.exception = exception;
    }

    @JsonValue
    public String exception() {
        return this.exception;
    }
}
