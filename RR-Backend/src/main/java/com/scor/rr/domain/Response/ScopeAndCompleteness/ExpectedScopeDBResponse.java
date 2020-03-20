package com.scor.rr.domain.Response.ScopeAndCompleteness;

import lombok.Data;

@Data
public class ExpectedScopeDBResponse {

    private int contractSectionID;

    private String minimumGrainRegionPerilCode;

    private String accumulationRapCode;

    private boolean expectedRegionPeril;

    private String accumulationRapDesc;

    public ExpectedScopeDBResponse() {
    }
}
