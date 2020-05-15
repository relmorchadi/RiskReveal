package com.scor.rr.service.ScopeAndCompleteness;

import com.scor.rr.domain.Response.ScopeAndCompleteness.PricedScopeAndCompletenessResponse;
import com.scor.rr.domain.entities.ScopeAndCompleteness.Views.PricedScopeAndCompletenessView;
import com.scor.rr.repository.ScopeAndCompleteness.PricedScopeAndCompletenessViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricedScopeAndCompletenessViewService {

    @Autowired
    private PricedScopeAndCompletenessViewRepository pricedScopeAndCompletenessViewRepository;
    @Autowired
    private AccumulationPackageService accumulationPackageService;

    public PricedScopeAndCompletenessResponse getListOfImportedPLTs(long projectId, String WorkspaceName, int UWYear) {

        PricedScopeAndCompletenessResponse response = new PricedScopeAndCompletenessResponse();
        response.setScopeObject(accumulationPackageService.getScopeOnly(WorkspaceName, UWYear,projectId,0));
        response.setListOfImportedPLTs(pricedScopeAndCompletenessViewRepository.findByProjectId(projectId));

        return response;
    }
}
