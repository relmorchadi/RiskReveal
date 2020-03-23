package com.scor.rr.domain.Response.ScopeAndCompleteness;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageOverrideSection;
import lombok.Data;

import java.util.List;

@Data
public class AccumulationPackageResponse {

    private List<ScopeAndCompletenessResponse> scopeObject;
    private List<AttachedPLTsInfo> attachedPLTs;
    private List<AccumulationPackageOverrideSection> overriddenSections;
}
