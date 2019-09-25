package com.scor.rr.service;

import com.scor.rr.entity.InuringEdge;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.enums.InuringNodeType;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringEdgeNodeFoundException;
import com.scor.rr.exceptions.inuring.InuringIllegalRequestException;
import com.scor.rr.exceptions.inuring.InuringNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringEdgeRepository;
import com.scor.rr.repository.InuringFinalNodeRepository;
import com.scor.rr.repository.InuringInputNodeRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.request.InuringEdgeCreationRequest;
import com.scor.rr.request.InuringEdgeUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by u004602 on 16/09/2019.
 */
@Service
public class InuringEdgeService {
    @Autowired
    private InuringPackageRepository inuringPackageRepository;
    @Autowired
    private InuringEdgeRepository inuringEdgeRepository;
    @Autowired
    private InuringInputNodeRepository inuringInputNodeRepository;
    @Autowired
    private InuringFinalNodeRepository inuringFinalNodeRepository;

    public void createInuringEdge(InuringEdgeCreationRequest request) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(request.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());
        if (! checkInuringNodeExisting(request.getSourceNodeType(), request.getSourceNodeId()))
            throw new InuringNodeNotFoundException(request.getSourceNodeType().toString(), request.getSourceNodeId());
        if (! checkInuringNodeExisting(request.getTargetNodeType(), request.getTargetNodeId()))
            throw new InuringNodeNotFoundException(request.getTargetNodeType().toString(), request.getTargetNodeId());
        if (InuringNodeType.InputNode.equals(request.getTargetNodeType()))
            throw new InuringIllegalRequestException("Illegal request: could not create an edge to an Input Node");
        if (InuringNodeType.FinalNode.equals(request.getSourceNodeType()))
            throw new InuringIllegalRequestException("Illegal request: could not create an edge from an Final Node");
        InuringEdge inuringEdge = new InuringEdge(request.getInuringPackageId(),
                request.getSourceNodeId(),
                request.getSourceNodeType(),
                request.getTargetNodeId(),
                request.getTargetNodeType());
        inuringEdgeRepository.save(inuringEdge);
    }

    public void updateInuringEdge(InuringEdgeUpdateRequest request) throws RRException {
        InuringEdge inuringEdge = inuringEdgeRepository.findByInuringEdgeId(request.getInuringEdgeId());
        if (inuringEdge == null) throw new InuringEdgeNodeFoundException(request.getInuringEdgeId());
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

    private boolean checkInuringNodeExisting(InuringNodeType nodeType, int nodeId) {
        switch (nodeType) {
            case InputNode:
                return inuringInputNodeRepository.findByInuringInputNodeId(nodeId) != null;
            case ContractNode:
            case FinalNode:
                return inuringFinalNodeRepository.findByInuringFinalNodeId(nodeId) != null;
            default:
                return false;
        }

    }
}
