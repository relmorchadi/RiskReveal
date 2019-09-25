package com.scor.rr.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCodename {
    UNKNOWN("ERROR.UNKNOWN"),PLTNOTFOUNT("PLT NOT FOUND"),NODENOTFOUND("NODE NOT FOUND"),TYPENOTFOUND("ADJUSTMENT TYPE NOT FOUND"),BASISNOTFOUND("ADJUSTMENT BASIS NOT FOUND"),
    STATENOTFOUND("ADJUSTMENT STATE NOT FOUND"),THREADNOTFOUND("ADJUSTMENT THREAD NOT FOUND"),CATEGORYNOTFOUND("ADJUSTMENT CATEGORY NOT FOUND"),
    PARAMETERNOTFOUND("ADJUSTMENT PARAMETER NOT FOUND"),BINFILEEXCEPTION("BIN FILE NOT SAVED"),NODEPROCESSINGNOTFOUND("NODE PROCESSING NOT FOUND"),ORDEREXIST("ORDER EXIST"),
    PLTTYPENOTCORRECT("PLT Type Not correct"),LMFMUSTBEPOSITIVE("LMF MUST BE POSITIVE");
    private final String exception;

    ExceptionCodename(final String exception) {
        this.exception = exception;
    }

    @JsonValue
    public String exception() {
        return this.exception;
    }
}
