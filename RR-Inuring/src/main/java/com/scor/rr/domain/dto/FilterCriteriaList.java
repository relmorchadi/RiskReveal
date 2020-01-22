package com.scor.rr.domain.dto;

import lombok.Data;

@Data
public class FilterCriteriaList {
    private String filterKey;
    private String filterValue;
    private boolean including;

    public FilterCriteriaList(String filterKey, String filterValue, boolean including) {
        this.filterKey = filterKey;
        this.filterValue = filterValue;
        this.including = including;
    }

    public FilterCriteriaList() {
    }
}
