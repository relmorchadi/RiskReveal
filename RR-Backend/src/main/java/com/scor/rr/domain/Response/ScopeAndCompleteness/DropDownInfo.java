package com.scor.rr.domain.Response.ScopeAndCompleteness;

import lombok.Data;

@Data
public class DropDownInfo {

    private long accumulationPackageId;
    private String accumulationPackageStatus;

    public DropDownInfo() {
    }
}
