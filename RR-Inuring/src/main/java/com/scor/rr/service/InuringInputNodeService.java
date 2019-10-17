package com.scor.rr.service;

import com.scor.rr.entity.InuringInputAttachedPLT;
import com.scor.rr.entity.InuringInputNode;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.enums.InuringNodeType;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InputPLTNotFoundException;
import com.scor.rr.exceptions.inuring.InuringInputNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringInputNodeBulkDeleteRequest;
import com.scor.rr.request.InuringInputNodeCreationRequest;
import com.scor.rr.request.InuringInputNodeUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by u004602 on 10/09/2019.
 */
@Service
public class InuringInputNodeService {
    @Autowired
    private InuringInputNodeRepository inuringInputNodeRepository;
    @Autowired
    private InuringInputAttachedPLTRepository inuringInputAttachedPLTRepository;
    @Autowired
    private InuringPackageRepository inuringPackageRepository;
    @Autowired
    private ScorpltheaderRepository scorpltheaderRepository;
    @Autowired
    private InuringEdgeService inuringEdgeService;

    public void createInuringInputNode(InuringInputNodeCreationRequest request) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(request.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());

        if (request.getAttachedPLTs() != null && ! request.getAttachedPLTs().isEmpty()) {
            for (Integer pltId : request.getAttachedPLTs()) {
                if (scorpltheaderRepository.findByPkScorPltHeaderId(pltId) == null) throw new InputPLTNotFoundException(pltId);
            }
        }

        InuringInputNode inuringInputNode = inuringInputNodeRepository.saveAndFlush(new InuringInputNode(request.getInuringPackageId(), request.getInputNodeName()));
        if (request.getAttachedPLTs() != null && ! request.getAttachedPLTs().isEmpty()) {
            for (Integer pltId : request.getAttachedPLTs()) {
                inuringInputAttachedPLTRepository.saveAndFlush(new InuringInputAttachedPLT(inuringInputNode.getInuringInputNodeId(), pltId));
            }
        }
    }

    public void  updateInuringInputNode(InuringInputNodeUpdateRequest request) throws RRException {
        InuringInputNode inuringInputNode = inuringInputNodeRepository.findByInuringInputNodeId(request.getInuringInputNodeId());
        if (inuringInputNode == null) throw new InuringInputNodeNotFoundException(request.getInuringInputNodeId());
        if (request.getAttachedPLTs() != null) {
            Set<Integer> newPLTIds = new HashSet<>();
            for (Integer id : request.getAttachedPLTs()) {
                if (scorpltheaderRepository.findByPkScorPltHeaderId(id) == null) throw new InputPLTNotFoundException(id);
                newPLTIds.add(id);
            }
            List<InuringInputAttachedPLT> inuringInputAttachedPLTs = inuringInputAttachedPLTRepository.findByInuringInputNodeId(request.getInuringInputNodeId());
            for (InuringInputAttachedPLT inuringInputAttachedPLT : inuringInputAttachedPLTs) {
                if (! request.getAttachedPLTs().contains(inuringInputAttachedPLT.getPltHeaderId())) {
                    inuringInputAttachedPLTRepository.deleteByInuringInputAttachedPLTId(inuringInputAttachedPLT.getInuringInputAttachedPLTId());
                } else {
                    newPLTIds.remove(inuringInputAttachedPLT.getPltHeaderId());
                }
            }
            if (! newPLTIds.isEmpty()) {
                for (Integer pltId : newPLTIds) {
                    inuringInputAttachedPLTRepository.saveAndFlush(new InuringInputAttachedPLT(inuringInputNode.getInuringInputNodeId(), pltId));
                }
            }
        }
        if (request.getInputNodeName() != null) {
            inuringInputNode.setInputNodeName(request.getInputNodeName());
        }
        inuringInputNodeRepository.save(inuringInputNode);

    }

    public InuringInputNode findByInuringInputNodeId(int inuringInputNodeId) {
        return inuringInputNodeRepository.findByInuringInputNodeId(inuringInputNodeId);
    }

    public List<InuringInputNode> findInputNodesByInuringPackageId(int inuringPackageId) {
        return inuringInputNodeRepository.findByInuringPackageId(inuringPackageId);
    }

    public List<InuringInputAttachedPLT> findAttachedPLTByInuringInputNodeId(int inuringInputNodeId) {
        return inuringInputAttachedPLTRepository.findByInuringInputNodeId(inuringInputNodeId);
    }

    public void deleteInuringInputNode(int inuringInputNodeId) {
        inuringInputNodeRepository.deleteByInuringInputNodeId(inuringInputNodeId);
        inuringInputAttachedPLTRepository.deleteByInuringInputNodeId(inuringInputNodeId);
        inuringEdgeService.deleteByRelatedNode(InuringNodeType.InputNode, inuringInputNodeId);
    }

    /**Added by soufiane, requires Testing**/
    public void deleteInuringInputNodes(InuringInputNodeBulkDeleteRequest request){
        for(InuringInputNode inputNode: request.getInuringInputNodes()){
            inuringInputNodeRepository.deleteById(inputNode.getInuringInputNodeId());
        }
        /**Still have to test if the Cascade delete works**/
    }
}
