package com.scor.rr.domain.dto;

import com.scor.rr.domain.TreatyTableNames;
import org.springframework.data.domain.Page;

public class SearchCountResult <T> {
    public Page<?> result;
    public T mappingTable;

    public SearchCountResult(Page<?> result, T mappingTable) {
        this.result= result;
        this.mappingTable= mappingTable;
    }
}
