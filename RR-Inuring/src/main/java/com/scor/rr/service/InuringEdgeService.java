package com.scor.rr.service;

import com.scor.rr.entity.InuringEdge;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.enums.InuringFinancialPerspective;
import com.scor.rr.enums.InuringNodeType;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.*;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringEdgeCreationRequest;
import com.scor.rr.request.InuringEdgeUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by u004602 on 16/09/2019.
 */
@Service
@Transactional
public class InuringEdgeService {
    @Autowired
    private InuringPackageRepository inuringPackageRepository;
    @Autowired
    private InuringEdgeRepository inuringEdgeRepository;
    @Autowired
    private InuringInputNodeRepository inuringInputNodeRepository;
    @Autowired
    private InuringFinalNodeRepository inuringFinalNodeRepository;
    @Autowired
    private InuringContractNodeRepository inuringContractNodeRepository;

    public void createInuringEdge(InuringEdgeCreationRequest request) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(request.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());
        if (! checkInuringNodeExisting(request.getSourceNodeType(), request.getSourceNodeId()))
            throw new InuringNodeNotFoundException(request.getSourceNodeType().toString(), request.getSourceNodeId());
        if (! checkInuringNodeExisting(request.getTargetNodeType(), request.getTargetNodeId()))
            throw new InuringNodeNotFoundException(request.getTargetNodeType().toString(), request.getTargetNodeId());
        if(InuringNodeType.InputNode.equals(request.getTargetNodeType()) || InuringNodeType.FinalNode.equals(request.getSourceNodeType()) || (request.getSourceNodeId() == request.getTargetNodeId() && request.getSourceNodeType() == request.getTargetNodeType())) {
            throw new IllogicalEdgeCreationException(request.getTargetNodeId());
        }
        InuringEdge inuringEdge = new InuringEdge(request.getInuringPackageId(),
                request.getSourceNodeId(),
                request.getSourceNodeType(),
                request.getTargetNodeId(),
                request.getTargetNodeType());
        inuringEdgeRepository.save(inuringEdge);
    }

    /**changes so that the edges coming from input nodes always have a NET output perspective**/
    public void updateInuringEdge(InuringEdgeUpdateRequest request) throws RRException {
        InuringEdge inuringEdge = inuringEdgeRepository.findByInuringEdgeId(request.getInuringEdgeId());
        if (inuringEdge == null) throw new InuringEdgeNodeFoundException(request.getInuringEdgeId());

        if(InuringNodeType.InputNode.equals(inuringEdge.getSourceNodeType()) && !request.getOutputPerspective().equals(InuringFinancialPerspective.Net)){
            throw new UnreasonableUpdateException(request.getInuringEdgeId());
        }

        inuringEdge.setFinancialTreatment(request.getFinancialTreatment());
        inuringEdge.setOutputPerspective(request.getOutputPerspective());
        if (InuringNodeType.FinalNode.equals(inuringEdge.getTargetNodeType())) {
            inuringEdge.setOutputAtLayerLevel(request.isOutputAtLayerLevel());
        }
        inuringEdgeRepository.save(inuringEdge);
    }

    public void deleteInuringEdgeById(int inuringEdgeId) {
        inuringEdgeRepository.deleteByInuringEdgeId(inuringEdgeId);
    }

    public void deleteByRelatedNode(InuringNodeType nodeType, int nodeId) {
        inuringEdgeRepository.deleteBySourceNodeTypeAndSourceNodeId(nodeType, nodeId);
        inuringEdgeRepository.deleteByTargetNodeTypeAndTargetNodeId(nodeType, nodeId);
    }

    public InuringEdge readInuringEdge(int inuringEdgeId) throws RRException{
        InuringEdge inuringEdge = inuringEdgeRepository.findByInuringEdgeId(inuringEdgeId);
        if(inuringEdge == null) throw new InuringEdgeNodeFoundException(inuringEdgeId);
        return inuringEdge;
    }

    private boolean checkInuringNodeExisting(InuringNodeType nodeType, int nodeId) {
        switch (nodeType) {
            case InputNode:
                return inuringInputNodeRepository.findByInuringInputNodeId(nodeId) != null;
            case ContractNode:
                return inuringContractNodeRepository.findByInuringContractNodeId(nodeId) != null;
            case FinalNode:
                return inuringFinalNodeRepository.findByInuringFinalNodeId(nodeId) != null;
            default:
                return false;
        }

    }
}
