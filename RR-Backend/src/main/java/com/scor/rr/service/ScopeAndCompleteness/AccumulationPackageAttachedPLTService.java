package com.scor.rr.service.ScopeAndCompleteness;

import com.scor.rr.domain.PltHeaderEntity;
import com.scor.rr.domain.Response.ScopeAndCompleteness.AccumulationPackageResponse;
import com.scor.rr.domain.Response.ScopeAndCompleteness.AttachedPLTsInfo;
import com.scor.rr.domain.Response.ScopeAndCompleteness.ScopeAndCompletenessResponse;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackage;
import com.scor.rr.domain.entities.ScopeAndCompleteness.AccumulationPackageAttachedPLT;
import com.scor.rr.domain.requests.ScopeAndCompleteness.AttachPLTRequest;
import com.scor.rr.domain.requests.ScopeAndCompleteness.PLTAttachingInfo;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.ScopeAndCompleteness.AccumulationPackageNotFoundException;
import com.scor.rr.exceptions.inuring.InputPLTNotFoundException;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageAttachedPLTRepository;
import com.scor.rr.repository.ScopeAndCompleteness.AccumulationPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccumulationPackageAttachedPLTService {

    @Autowired
    private AccumulationPackageAttachedPLTRepository accumulationPackageAttachedPLTRepository;
    @Autowired
    private AccumulationPackageRepository accumulationPackageRepository;
    @Autowired
    private AccumulationPackageService accumulationPackageService;
    @Autowired
    private PltHeaderRepository pltHeaderRepository;
    @Autowired
    private AccumulationPackageOverrideSectionService accumulationPackageOverrideSectionService;

    public AccumulationPackageResponse attachSelectedPlts(AttachPLTRequest request) throws RRException {
        AccumulationPackageResponse response = new AccumulationPackageResponse();
        if(!request.getPltList().isEmpty()){
            AccumulationPackage accumulationPackage = getTheAccumulationPackage(request.getAccumulationPackageId(),request.getWorkspaceId());
            List<AccumulationPackageAttachedPLT> listToSave = new ArrayList<>();
            for(PLTAttachingInfo row : request.getPltList()){
                PltHeaderEntity plt = pltHeaderRepository.findByPltHeaderId(row.getPltHeaderId());
                if(plt == null) throw new InputPLTNotFoundException(row.getPltHeaderId());
                AccumulationPackageAttachedPLT accumulationPackageAttachedPLT = new AccumulationPackageAttachedPLT();
                accumulationPackageAttachedPLT.setAccumulationPackageId(accumulationPackage.getAccumulationPackageId());
                accumulationPackageAttachedPLT.setContractSectionId(String.valueOf(row.getContractSectionId()));
                accumulationPackageAttachedPLT.setPLTHeaderId(row.getPltHeaderId());
                listToSave.add(accumulationPackageAttachedPLT);
            }
            accumulationPackageAttachedPLTRepository.saveAll(listToSave);

            response.setScopeObject(accumulationPackageService.getScopeOnly(request.getWorkspaceName(),request.getUwYear()));
            response.setAttachedPLTs(getAttachedPLTs(accumulationPackage.getAccumulationPackageId()));
            response.setOverriddenSections(accumulationPackageOverrideSectionService.getOverriddenSections(accumulationPackage.getAccumulationPackageId()));



        }
        return response;
    }


    public List<AttachedPLTsInfo> getAttachedPLTs(long accumulationPackageId) throws RRException {
        AccumulationPackage accumulationPackage = accumulationPackageRepository.findByAccumulationPackageId(accumulationPackageId);
        if(accumulationPackage == null) throw new AccumulationPackageNotFoundException(accumulationPackageId);

        List<AttachedPLTsInfo> pltReturnList = new ArrayList<>();
        List<AccumulationPackageAttachedPLT> attachedPLTS = accumulationPackageAttachedPLTRepository.findByAccumulationPackageId(accumulationPackageId);
        if(!attachedPLTS.isEmpty()){
            for(AccumulationPackageAttachedPLT plt : attachedPLTS){
                AttachedPLTsInfo pltInfo = new AttachedPLTsInfo();
                pltInfo.setAttachedPLT(pltHeaderRepository.findByPltHeaderId(plt.getPLTHeaderId()));
                pltInfo.setContractSectionId(plt.getContractSectionId());
                pltReturnList.add(pltInfo);
            }
        }
        return pltReturnList;

    }

    public void deleteSelectedAttachedPLTs(List<Long> listPlts){
        if(!listPlts.isEmpty()){
            for(Long id: listPlts){
                accumulationPackageAttachedPLTRepository.deleteById(id);
            }

        }
    }

    public AccumulationPackage getTheAccumulationPackage(long accumulationPackageId, long workspaceId) throws RRException {
        if(accumulationPackageId != 0) {
            AccumulationPackage accumulationPackage = accumulationPackageRepository.findByAccumulationPackageId(accumulationPackageId);
            if(accumulationPackage == null ) throw new AccumulationPackageNotFoundException(accumulationPackageId);
            return accumulationPackage;


        }else{
            return  accumulationPackageRepository.saveAndFlush(new AccumulationPackage(workspaceId));
        }
    }
}
