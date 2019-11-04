package com.scor.rr.domain.dto;

import com.scor.rr.domain.TableNames;
import org.springframework.data.domain.Page;

public class SearchCountResult {
    public Page<?> result;
    public TableNames mappingTable;

    public SearchCountResult(Page<?> result, TableNames mappingTable) {
        this.result= result;
        this.mappingTable= mappingTable;
    }
}
