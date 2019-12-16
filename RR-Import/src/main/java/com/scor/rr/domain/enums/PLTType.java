package com.scor.rr.domain.enums;

import lombok.Getter;

/**
 * @author Hamiani Mohammed
 * creation date  18/09/2019 at 15:58
 **/
public enum PLTType {

    Pure("PURE"), Interim("INTERIM"), Thread("THREAD"), PostInured("POST_INURED");

    @Getter
    private String code;

    PLTType(String code){
        this.code= code;
    }

}
