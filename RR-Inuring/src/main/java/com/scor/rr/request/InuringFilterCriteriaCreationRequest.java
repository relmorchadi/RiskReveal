package com.scor.rr.request;

import com.scor.rr.enums.InuringElementType;
import lombok.Data;


public class InuringFilterCriteriaCreationRequest {

    private long inuringPackageId;

    private InuringElementType inuringObjectType;

    private long inuringObjectId;

    private String filterKey;

    private String filterValue;

    private boolean including;

    public InuringFilterCriteriaCreationRequest(long inuringPackageId, InuringElementType inuringObjectType, long inuringObjectId, String filterKey, String filterValue, boolean including) {
        this.inuringPackageId = inuringPackageId;
        this.inuringObjectType = inuringObjectType;
        this.inuringObjectId = inuringObjectId;
        this.filterKey = filterKey;
        this.filterValue = filterValue;
        this.including = including;
    }

    public InuringFilterCriteriaCreationRequest() {
    }

    public long getInuringPackageId() {
        return inuringPackageId;
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
