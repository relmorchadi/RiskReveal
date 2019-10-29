package com.scor.rr.domain.enums;

import lombok.Getter;

@Getter
public enum FinancialPerspectiveCodeEnum {

    TY("TY"), UP("UP");

    private String code;

    FinancialPerspectiveCodeEnum(String code) {
        this.code = code;
    }
}
