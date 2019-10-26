package com.scor.rr.domain.enums;

import lombok.Getter;

@Getter
public enum FinancialPerspectiveCodeEnum {

    TY("TY");

    private String code;

    FinancialPerspectiveCodeEnum(String code) {
        this.code = code;
    }
}
