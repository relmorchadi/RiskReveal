package com.scor.rr.domain.Response.ScopeAndCompleteness;

import com.scor.rr.domain.entities.ScopeAndCompleteness.Views.PricedScopeAndCompletenessView;
import lombok.Data;

import java.util.List;

@Data
public class PricedScopeAndCompletenessResponse {

    private List<ScopeAndCompletenessResponse> scopeObject;
    private List<PricedScopeAndCompletenessView> listOfImportedPLTs;

}
