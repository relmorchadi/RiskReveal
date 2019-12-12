package com.scor.rr.domain.enums;


import lombok.Getter;

// @TODO
public enum ScanLevelEnum {
    Detailed(1), Basic(0);

    @Getter
    private int code;

    ScanLevelEnum(int code) {
        this.code = code;
    }

    public static ScanLevelEnum get(int code) {
        switch (code) {
            case 1:
                return Detailed;
            case 0:
                return Basic;
            default:
                return null;
        }
    }
}
