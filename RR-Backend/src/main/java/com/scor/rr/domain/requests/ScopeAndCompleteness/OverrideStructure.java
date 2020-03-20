package com.scor.rr.domain.requests.ScopeAndCompleteness;

import lombok.Data;

@Data
public class OverrideStructure {

    private int ContractSectionId;
    private String MinimumGrainRegionPerilCode;
    private String AccumulationRAPCode;

}
