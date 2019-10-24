package com.scor.rr.service;

import com.scor.rr.entity.InuringContractNode;
import com.scor.rr.entity.InuringFinalNode;
import com.scor.rr.entity.InuringInputNode;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.enums.InuringNodeStatus;
import com.scor.rr.enums.InuringNodeType;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.*;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringPackageCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.scor.rr.enums.InuringNodeType.*;

/**
 * Created by u004602 on 16/09/2019.
 */
@Service
@Transactional
public class InuringPackageService {
    @Autowired
    private InuringPackageRepository inuringPackageRepository;
    @Autowired
    private InuringInputNodeRepository inuringInputNodeRepository;
    @Autowired
    private InuringFinalNodeRepository inuringFinalNodeRepository;
    @Autowired
    private InuringContractNodeRepository inuringContractNodeRepository;
    @Autowired
    private InuringFinalNodeService inuringFinalNodeService;

    public void createInuringPackage(InuringPackageCreationRequest request) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.saveAndFlush(new InuringPackage(request.getPackageName(),
                request.getPackageDescription(),
                request.getWorkspaceId(),
                request.getCreatedBy()));
        inuringFinalNodeService.createInuringFinalNodeForPackage(inuringPackage.getInuringPackageId());
    }

    public void deleteInuringPackage(int inuringPackageId) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(inuringPackageId);
        if (inuringPackage == null) throw new InuringPackageNotFoundException(inuringPackageId);
        if (inuringPackage.isLocked()) throw new InuringIllegalModificationException(inuringPackageId);
        inuringPackageRepository.deleteById(inuringPackageId);
    }

    public InuringPackage findByInuringPackageId(int inuringPackageId){
        return inuringPackageRepository.findByInuringPackageId(inuringPackageId);
    }

    public void invalidateNode(InuringNodeType nodeType, int nodeId) throws RRException {
        switch (nodeType) {
            case InputNode:
                InuringInputNode inputNode = inuringInputNodeRepository.findByInuringInputNodeId(nodeId);
                if (inputNode == null) throw new InuringInputNodeNotFoundException(nodeId);
                inputNode.setInputInuringNodeStatus(InuringNodeStatus.Invalid);
                inuringInputNodeRepository.save(inputNode);
                break;
            case ContractNode:
                InuringContractNode inuringContractNode = inuringContractNodeRepository.findByInuringContractNodeId(nodeId);
                if(inuringContractNode == null ) throw new InuringContractNodeNotFoundException(nodeId);
                inuringContractNode.setContractNodeStatus(InuringNodeStatus.Invalid);
                inuringContractNodeRepository.save(inuringContractNode);
                break;
            case FinalNode:
                InuringFinalNode finalNode = inuringFinalNodeRepository.findByInuringFinalNodeId(nodeId);
                if (finalNode == null) throw new InuringFinalNodeNotFoundException(nodeId);
                finalNode.setFinalNodeStatus(InuringNodeStatus.Invalid);
                inuringFinalNodeRepository.save(finalNode);
                break;
            default:
                throw  new InuringNodeNotFoundException(nodeType.name(), nodeId);
        }
    }
}
