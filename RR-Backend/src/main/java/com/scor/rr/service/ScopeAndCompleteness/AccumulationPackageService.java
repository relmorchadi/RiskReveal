package com.scor.rr.service.ScopeAndCompleteness;

import com.scor.rr.domain.Response.ScopeAndCompleteness.ScopeAndCompletenessResponse;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackage;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageAttachedPLT;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageOverrideSection;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.ScopeAndCompleteness.AccumulationPackageNotFoundException;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageAttachedPLTRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageOverrideSectionRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AccumulationPackageService {

    @Autowired
    private AccumulationPackageRepository accumulationPackageRepository;
    @Autowired
    private AccumulationPackageAttachedPLTRepository accumulationPackageAttachedPLTRepository;
    @Autowired
    private AccumulationPackageOverrideSectionRepository accumulationPackageOverrideSectionRepository;


    public void getScopeOnly(String workspaceName, int uwYear ){
        List<ScopeAndCompletenessResponse> response = new ArrayList<>();
        List<Map<String,Object>> expectedScope = accumulationPackageRepository.getExpectedScopeOnly(workspaceName,uwYear);
        if(expectedScope!= null && !expectedScope.isEmpty()){
            for(Map<String,Object> row : expectedScope){

            }
        }


    }

    public List<AccumulationPackageAttachedPLT> getAttachedPLTs(long accumulationPackageId) throws RRException {

        AccumulationPackage accumulationPackage = accumulationPackageRepository.findByAccumulationPackageId(accumulationPackageId);
        if(accumulationPackage == null) throw new AccumulationPackageNotFoundException(accumulationPackageId);

        return accumulationPackageAttachedPLTRepository.findByAccumulationPackageId(accumulationPackageId);
    }




}
