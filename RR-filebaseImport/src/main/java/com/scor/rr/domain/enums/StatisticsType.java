package com.scor.rr.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum StatisticsType {

    ELT("ELT"),
    PLT("PLT"),
    SCOR_PLT("SCOR PLT");

    @Getter
    private String code;
}
