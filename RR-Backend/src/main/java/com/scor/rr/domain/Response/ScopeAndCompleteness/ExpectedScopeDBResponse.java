package com.scor.rr.domain.Response.ScopeAndCompleteness;

import lombok.Data;

@Data
public class ExpectedScopeDBResponse {

    private int contractSectionID;

    private String minimumGrainRegionPerilCode;

    private String accumulationRapCode;

    private String expectedRegionPerilCode;

    private String accumulationRapDesc;

    private boolean isExpected;

    public ExpectedScopeDBResponse() {
    }
}
