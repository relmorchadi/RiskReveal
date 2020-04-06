package com.scor.rr.service.ScopeAndCompleteness;

import com.scor.rr.domain.Response.ScopeAndCompleteness.AccumulationPackageResponse;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackage;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageOverrideSection;
import com.scor.rr.domain.requests.ScopeAndCompleteness.OverrideSectionRequest;
import com.scor.rr.domain.requests.ScopeAndCompleteness.OverrideStructure;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.ScopeAndCompleteness.AccumulationPackageNotFoundException;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageOverrideSectionRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageRepository;
import com.scor.rr.repository.WorkspaceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccumulationPackageOverrideSectionService {

    @Autowired
    private AccumulationPackageOverrideSectionRepository accumulationPackageOverrideSectionRepository;

    @Autowired
    private AccumulationPackageAttachedPLTService accumulationPackageAttachedPLTService;
    @Autowired
    private AccumulationPackageService accumulationPackageService;
    @Autowired
    private WorkspaceEntityRepository workspaceEntityRepository;

    public AccumulationPackageResponse overrideChosenSections(OverrideSectionRequest request) throws RRException {
        AccumulationPackageResponse response = new AccumulationPackageResponse();
        WorkspaceEntity ws = workspaceEntityRepository.findByWorkspaceNameAndWorkspaceUwYear(request.getWorkspaceName(),request.getUwYear());

        AccumulationPackage accumulationPackage = accumulationPackageAttachedPLTService.getTheAccumulationPackage(request.getAccumulationPackageId(),ws.getWorkspaceId());

        if(!request.getListOfOverrides().isEmpty()){
            List<AccumulationPackageOverrideSection> listToSave = new ArrayList<>();
            for(OverrideStructure row: request.getListOfOverrides()){
                AccumulationPackageOverrideSection overrideSection = new AccumulationPackageOverrideSection();
                overrideSection.setAccumulationPackageId(accumulationPackage.getAccumulationPackageId());
                overrideSection.setAccumulationPackageOverrideSectionId(row.getContractSectionId());
                overrideSection.setMinimumGrainRegionPerilCode(row.getMinimumGrainRegionPerilCode());
                overrideSection.setAccumulationRAPCode(row.getAccumulationRAPCode());
                overrideSection.setOverrideBasisCode(request.getOverrideBasisCode());
                overrideSection.setOverrideBasisNarrative(request.getOverrideBasisNarrative());
                listToSave.add(overrideSection);
            }
            accumulationPackageOverrideSectionRepository.saveAll(listToSave);

            response.setScopeObject(accumulationPackageService.getScopeOnly(request.getWorkspaceName(),request.getUwYear()));
            response.setAttachedPLTs(accumulationPackageAttachedPLTService.getAttachedPLTs(accumulationPackage.getAccumulationPackageId()));
            response.setOverriddenSections(getOverriddenSections(accumulationPackage.getAccumulationPackageId()));
        }

        return  response;
    }

    public List<AccumulationPackageOverrideSection> getOverriddenSections(long accumulationPackageId){
        return accumulationPackageOverrideSectionRepository.findByAccumulationPackageId(accumulationPackageId);
    }

    public void deleteOverride(List<AccumulationPackageOverrideSection> overriddenSections){
        if(!overriddenSections.isEmpty()){
            accumulationPackageOverrideSectionRepository.deleteAll(overriddenSections);
        }
    }
}
