package com.scor.rr.request;

import com.scor.rr.enums.InuringElementType;
import lombok.Data;


public class InuringFilterCriteriaUpdateRequest {

    private long inuringFilterCriteriaId;

    private InuringElementType inuringObjectType;

    private long inuringObjectId;

    private String filterKey;

    private String filterValue;

    private boolean including;

    public InuringFilterCriteriaUpdateRequest(long inuringFilterCriteriaId, InuringElementType inuringObjectType, long inuringObjectId, String filterKey, String filterValue, boolean including) {
        this.inuringFilterCriteriaId = inuringFilterCriteriaId;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.filterKey = filterKey;
        this.filterValue = filterValue;
        this.including = including;
    }

    public InuringFilterCriteriaUpdateRequest() {
    }

    public long getInuringFilterCriteriaId() {
        return inuringFilterCriteriaId;
    }

    public InuringElementType getInuringObjectType() {
        return inuringObjectType;
    }

    public long getInuringObjectId() {
        return inuringObjectId;
    }

    public String getFilterKey() {
        return filterKey;
    }

    public String getFilterValue() {
        return filterValue;
    }

    public boolean isIncluding() {
        return including;
    }
}
