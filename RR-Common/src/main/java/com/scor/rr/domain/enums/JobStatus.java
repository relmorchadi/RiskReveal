package com.scor.rr.domain.enums;

public enum JobStatus {
    PENDING("PENDING"), RUNNING("RUNNING"), CANCELLED("CANCELLED"), SUCCEEDED("SUCCEEDED"), FAILED("FAILED");

    String code;

    JobStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    }
