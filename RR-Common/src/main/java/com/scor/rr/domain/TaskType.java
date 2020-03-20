package com.scor.rr.domain;

public enum TaskType {
    IMPORT_ANALYSIS("IMPORT_ANALYSIS"), IMPORT_PORTFOLIO("IMPORT_PORTFOLIO");

    String code;

    TaskType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
