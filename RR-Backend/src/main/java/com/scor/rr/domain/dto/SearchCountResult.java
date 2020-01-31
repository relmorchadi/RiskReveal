package com.scor.rr.domain.dto;

import com.scor.rr.domain.TreatyTableNames;
import org.springframework.data.domain.Page;

public class SearchCountResult {
    public Page<?> result;
    public TreatyTableNames mappingTable;

    public SearchCountResult(Page<?> result, TreatyTableNames mappingTable) {
        this.result= result;
        this.mappingTable= mappingTable;
    }
}
