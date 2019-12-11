package com.scor.rr.domain.enums;

import lombok.Getter;

public enum TargetCurrencyEnum {

    MAIN_LIABILITY("Main Liability Currency (MLC)"),
    ANALYSIS_CURRENCY("Analysis Currency"),
    USER_DEFINED_CURRENCY("User Defined Currency");

    public static String[] currencyChoices= new String[]{
            MAIN_LIABILITY.getValue(),
            ANALYSIS_CURRENCY.getValue(),
            USER_DEFINED_CURRENCY.getValue()
    };

    @Getter
    private String value;

    TargetCurrencyEnum(String value) {
        this.value = value;
    }

}
