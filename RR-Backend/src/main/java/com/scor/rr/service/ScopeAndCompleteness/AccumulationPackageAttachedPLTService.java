package com.scor.rr.service.ScopeAndCompleteness;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.Response.ScopeAndCompleteness.*;
import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackage;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageAttachedPLT;
import com.scor.rr.domain.entities.ScopeAndCompleteness.Views.PricedScopeAndCompletenessView;
import com.scor.rr.domain.requests.ScopeAndCompleteness.AttachPLTRequest;
import com.scor.rr.domain.requests.ScopeAndCompleteness.PLTAttachingInfo;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.ScopeAndCompleteness.AccumulationPackageNotFoundException;
import com.scor.rr.exceptions.ScopeAndCompleteness.WorkspaceNotFoundException;
import com.scor.rr.exceptions.inuring.InputPLTNotFoundException;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageAttachedPLTRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageRepository;
import com.scor.rr.repository.ScopeAndCompleteness.PricedScopeAndCompletenessViewRepository;
import com.scor.rr.repository.WorkspaceEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccumulationPackageAttachedPLTService {

    @Autowired
    private AccumulationPackageAttachedPLTRepository accumulationPackageAttachedPLTRepository;
    @Autowired
    private AccumulationPackageRepository accumulationPackageRepository;
    @Autowired
    private AccumulationPackageService accumulationPackageService;
    @Autowired
    private PricedScopeAndCompletenessViewRepository pricedScopeAndCompletenessViewRepository;
    @Autowired
    private AccumulationPackageOverrideSectionService accumulationPackageOverrideSectionService;
    @Autowired
    private WorkspaceEntityRepository workspaceEntityRepository;

    public AccumulationPackageResponse attachSelectedPlts(AttachPLTRequest request) throws RRException {
        AccumulationPackageResponse response = new AccumulationPackageResponse();
        WorkspaceEntity ws = workspaceEntityRepository.findByWorkspaceContextCodeAndWorkspaceUwYear(request.getWorkspaceName(),request.getUwYear());

        if(ws == null ) throw new WorkspaceNotFoundException(request.getWorkspaceName(),request.getUwYear());

        if (!request.getPltList().isEmpty()) {
            AccumulationPackage accumulationPackage = getTheAccumulationPackage(request.getAccumulationPackageId(),ws.getWorkspaceId(),request.getProjectId());
            accumulationPackageAttachedPLTRepository.deleteByAccumulationPackageId(request.getAccumulationPackageId());
            List<AccumulationPackageAttachedPLT> listToSave = new ArrayList<>();
            for (PLTAttachingInfo row : request.getPltList()) {
                PricedScopeAndCompletenessView plt = pricedScopeAndCompletenessViewRepository.findByPLTHeaderId(row.getPltHeaderId());
                if (plt == null) throw new InputPLTNotFoundException(row.getPltHeaderId());
                AccumulationPackageAttachedPLT accumulationPackageAttachedPLT = new AccumulationPackageAttachedPLT();
                accumulationPackageAttachedPLT.setAccumulationPackageId(accumulationPackage.getAccumulationPackageId());
                accumulationPackageAttachedPLT.setContractSectionId(String.valueOf(row.getContractSectionId()));
                accumulationPackageAttachedPLT.setPLTHeaderId(row.getPltHeaderId());
                accumulationPackageAttachedPLT.setEntity(1);
                listToSave.add(accumulationPackageAttachedPLT);
            }
            accumulationPackageAttachedPLTRepository.saveAll(listToSave);

            response.setScopeObject(accumulationPackageService.getScopeOnly(request.getWorkspaceName(), request.getUwYear(),request.getProjectId(),accumulationPackage.getAccumulationPackageId()));
            response.setAttachedPLTs(getAttachedPLTs(accumulationPackage.getAccumulationPackageId()));
            response.setOverriddenSections(accumulationPackageOverrideSectionService.getOverriddenSections(accumulationPackage.getAccumulationPackageId()));


        }
        return response;
    }

    public List<AttachedPLTsInfo> getAttachedPLTs(long accumulationPackageId) throws RRException {
        AccumulationPackage accumulationPackage = accumulationPackageRepository.findByAccumulationPackageId(accumulationPackageId);
        if (accumulationPackage == null) throw new AccumulationPackageNotFoundException(accumulationPackageId);

        List<AttachedPLTsInfo> pltReturnList = new ArrayList<>();
        List<AccumulationPackageAttachedPLT> attachedPLTS = accumulationPackageAttachedPLTRepository.findByAccumulationPackageId(accumulationPackageId);
        if (!attachedPLTS.isEmpty()) {
            for (AccumulationPackageAttachedPLT plt : attachedPLTS) {
                AttachedPLTsInfo pltInfo = new AttachedPLTsInfo();
                pltInfo.setAttachedPLT(pricedScopeAndCompletenessViewRepository.findByPLTHeaderId(plt.getPLTHeaderId()));
                pltInfo.setContractSectionId(plt.getContractSectionId());
                pltReturnList.add(pltInfo);
            }
        }
        return pltReturnList;

    }

    public void deleteSelectedAttachedPLTs(List<Long> listPlts) {
        if (!listPlts.isEmpty()) {
            for (Long id : listPlts) {
                accumulationPackageAttachedPLTRepository.deleteById(id);
            }

        }
    }

    public AccumulationPackage getTheAccumulationPackage(long accumulationPackageId, long workspaceId,long projectId) throws RRException {
        if (accumulationPackageId != 0) {
            AccumulationPackage accumulationPackage = accumulationPackageRepository.findByAccumulationPackageId(accumulationPackageId);
            if (accumulationPackage == null) throw new AccumulationPackageNotFoundException(accumulationPackageId);
            return accumulationPackage;

        } else {
            return accumulationPackageRepository.saveAndFlush(new AccumulationPackage(workspaceId,projectId,1));
        }
    }

    public List<PopUpPLTsResponse> getPLTs(long accumulationPackageId, long projectId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setFieldMatchingEnabled(true);
        return accumulationPackageAttachedPLTRepository.getPLTsData(accumulationPackageId, projectId)
                .stream()
                .map(exScope -> mapper.map(exScope, PopUpPLTsResponse.class))
                .collect(Collectors.toList());
    }


}
