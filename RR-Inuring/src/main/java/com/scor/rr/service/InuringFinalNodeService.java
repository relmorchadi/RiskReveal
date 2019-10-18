package com.scor.rr.service;

import com.scor.rr.entity.InuringFinalNode;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringFinalNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringFinalNodeRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.request.InuringFinalNodeUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by u004602 on 16/09/2019.
 */
@Service
public class InuringFinalNodeService {
    @Autowired
    private InuringPackageRepository inuringPackageRepository;

    @Autowired
    private InuringFinalNodeRepository inuringFinalNodeRepository;

    public void createInuringFinalNodeForPackage(int inuringPackageId) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(inuringPackageId);
        if (inuringPackage == null) throw new InuringPackageNotFoundException(inuringPackageId);
        InuringFinalNode finalNode = new InuringFinalNode(inuringPackageId);
        inuringFinalNodeRepository.save(finalNode);
    }

    public void deleteByInuringPackageId(int inuringPackageId) {
        inuringFinalNodeRepository.deleteByInuringPackageId(inuringPackageId);
    }

    public void updateInuringFinalNode(InuringFinalNodeUpdateRequest request) throws RRException {
        InuringFinalNode inuringFinalNode = inuringFinalNodeRepository.findByInuringPackageId(request.getInuringFinalNodeId());
        if (inuringFinalNode == null) throw new InuringFinalNodeNotFoundException(request.getInuringFinalNodeId());
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(inuringFinalNode.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(inuringFinalNode.getInuringPackageId());
        inuringFinalNode.setInuringOutputGrain(request.getInuringOutputGrain());
        inuringFinalNodeRepository.save(inuringFinalNode);
    }

    public InuringFinalNode readInuringFinalNodeByPackageId(int id) throws RRException{
        InuringFinalNode inuringFinalNode =  inuringFinalNodeRepository.findByInuringPackageId(id);
        if(inuringFinalNode == null) throw new InuringFinalNodeNotFoundException(id);
        return inuringFinalNode;
    }
}
