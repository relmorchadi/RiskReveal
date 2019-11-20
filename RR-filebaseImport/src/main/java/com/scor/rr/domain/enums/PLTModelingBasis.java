package com.scor.rr.domain.enums;

import lombok.Getter;

public enum PLTModelingBasis {
    AM("AM"), PM("PM");

    @Getter
    private String code;

    PLTModelingBasis(String code){
        this.code=code;
    }

}
