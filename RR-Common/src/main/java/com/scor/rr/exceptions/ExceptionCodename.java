package com.scor.rr.exceptions;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExceptionCodename {
    UNKNOWN("ERROR.UNKNOWN"),
    PLT_NOT_FOUND("PLT NOT FOUND"),
    NODE_NOT_FOUND("NODE NOT FOUND"),
    TYPE_NOT_FOUND("ADJUSTMENT TYPE NOT FOUND"),
    BASIS_NOT_FOUND("ADJUSTMENT BASIS NOT FOUND"),
    STATE_NOT_FOUND("ADJUSTMENT STATE NOT FOUND"),
    THREAD_NOT_FOUND("ADJUSTMENT THREAD NOT FOUND"),
    CATEGORY_NOT_FOUND("ADJUSTMENT CATEGORY NOT FOUND"),
    PARAMETER_NOT_FOUND("ADJUSTMENT PARAMETER NOT FOUND"),
    BIN_FILE_EXCEPTION("BIN FILE NOT SAVED"),
    NODE_PROCESSING_NOT_FOUND("NODE PROCESSING NOT FOUND"),
    ORDER_EXIST("ORDER EXIST"),
    PLT_TYPE_NOT_CORRECT("PLT Type Not correct"),

    /*
        This section is deligated to PLT File Exceptions
     */
    EVENT_DATE_FORMAT_WRONG("Event Date format wrong"),
    NUMBER_FORMAT_WRONG("Number Format wrong"),
    PLT_DATA_NULL("PLT Data is null"),
    PLT_FILE_CORRUPTED("PLT file was corrupted or truncated"),
    PLT_FILE_EXT_NOT_SUPPORTED("PLT file extension not supported"),
    PLT_FILE_NOT_FOUND("PLT file not found"),
    PLT_FILE_WRITE_ERROR("Error while writing to file"),

    ;
    private final String exception;

    ExceptionCodename(final String exception) {
        this.exception = exception;
    }

    @JsonValue
    public String exception() {
        return this.exception;
    }
}
