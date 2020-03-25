package com.scor.rr.domain.requests.ScopeAndCompleteness;

import lombok.Data;

import java.util.List;

@Data
public class OverrideSectionRequest {

    private String WorkspaceName;
    private int uwYear;
    private long workspaceId;
    private long accumulationPackageId;
    private String OverrideBasisCode;
    private String OverrideBasisNarrative;
    private List<OverrideStructure> listOfOverrides;
}
