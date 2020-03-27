package com.scor.rr.domain.enums;

public enum StepStatus {
    RUNNING("RUNNING"), SUCCEEDED("SUCCEEDED"), FAILED("FAILED");

    String code;

    StepStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
