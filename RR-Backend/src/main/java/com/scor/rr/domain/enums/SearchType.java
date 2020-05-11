package com.scor.rr.domain.enums;

import lombok.Data;


public enum SearchType {
    TREATY("TTY"),
    FAC("FAC");

    String searchType;

    SearchType(String v) {
        this.searchType = v;
    }

    public String getSearchType(){ return this.searchType;}

}
