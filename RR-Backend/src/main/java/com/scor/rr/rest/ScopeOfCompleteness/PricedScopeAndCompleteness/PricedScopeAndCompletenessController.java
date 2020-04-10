package com.scor.rr.rest.ScopeOfCompleteness.PricedScopeAndCompleteness;

import com.scor.rr.domain.Response.ScopeAndCompleteness.PricedScopeAndCompletenessResponse;
import com.scor.rr.service.ScopeAndCompleteness.PricedScopeAndCompletenessViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ScopeAndCompleteness/PricedScopeAndCompleteness")
public class PricedScopeAndCompletenessController {

    @Autowired
    private PricedScopeAndCompletenessViewService pricedScopeAndCompletenessViewService;

    @GetMapping("getPricedScope")
    public PricedScopeAndCompletenessResponse getPricedScope(long projectId,String WorkspaceName,int UWYear) {
        return pricedScopeAndCompletenessViewService.getListOfImportedPLTs(projectId,WorkspaceName,UWYear);
    }
}
