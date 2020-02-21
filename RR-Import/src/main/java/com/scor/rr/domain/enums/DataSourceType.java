package com.scor.rr.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum DataSourceType {
    EDM("EDM"), RDM("RDM");

    @Getter
    private String value;
}
