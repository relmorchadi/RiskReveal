package com.scor.rr.request;

import com.scor.rr.enums.InuringElementType;

public class InuringFilterCriteriaCreationRequest {

    private int inuringPackageId;

    private InuringElementType inuringObjectType;

    private int inuringObjectId;

    private String filterKey;

    private String filterValue;

    private boolean including;

    public InuringFilterCriteriaCreationRequest(int inuringPackageId, InuringElementType inuringObjectType, int inuringObjectId, String filterKey, String filterValue, boolean including) {
        this.inuringPackageId = inuringPackageId;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.filterKey = filterKey;
        this.filterValue = filterValue;
        this.including = including;
    }

    public int getInuringPackageId() {
        return inuringPackageId;
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
