package com.scor.rr.domain.enums;

public enum JobPriority {
    HIGH(3), MEDIUM(2), LOW(1);

    private int code;

    JobPriority(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static String getStringValue(int code) {
        switch (code) {
            case 1:
                return "LOW";
            case 2:
                return "MEDIUM";
            case 3:
                return "HIGH";
            default:
                return "";
        }
    }
}
