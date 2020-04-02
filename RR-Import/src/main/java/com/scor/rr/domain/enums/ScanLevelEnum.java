package com.scor.rr.domain.enums;


import lombok.Getter;

// @TODO
public enum ScanLevelEnum {
    Basic(0), Detailed(1) ;

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

    public int getCode() {
        return code;
    }
}
