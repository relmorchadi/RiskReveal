package com.scor.rr.request;

import com.scor.rr.enums.InuringElementType;

public class InuringFilterCriteriaUpdateRequest {

    private int inuringFilterCriteriaId;

    private InuringElementType inuringObjectType;

    private int inuringObjectId;

    private String filterKey;

    private String filterValue;

    private boolean including;

    public InuringFilterCriteriaUpdateRequest(int inuringFilterCriteriaId, InuringElementType inuringObjectType, int inuringObjectId, String filterKey, String filterValue, boolean including) {
        this.inuringFilterCriteriaId = inuringFilterCriteriaId;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.filterKey = filterKey;
        this.filterValue = filterValue;
        this.including = including;
    }

    public InuringFilterCriteriaUpdateRequest() {
    }

    public int getInuringFilterCriteriaId() {
        return inuringFilterCriteriaId;
    }

    public InuringElementType getInuringObjectType() {
        return inuringObjectType;
    }

    public int getInuringObjectId() {
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
