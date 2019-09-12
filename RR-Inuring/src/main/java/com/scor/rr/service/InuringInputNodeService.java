package com.scor.rr.service;

import com.scor.rr.entity.InuringInputAttachedPLT;
import com.scor.rr.entity.InuringInputNode;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InputPLTNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringInputNodeRepository;
import com.scor.rr.repository.InuringInputAttachedPLTRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.repository.ScorpltheaderRepository;
import com.scor.rr.request.InuringInputNodeCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void createInuringInputNode(InuringInputNodeCreationRequest request) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findById(request.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());

        if (request.getAttachedPLTs() != null && ! request.getAttachedPLTs().isEmpty()) {
            for (Integer pltId : request.getAttachedPLTs()) {
                if (scorpltheaderRepository.findByPkScorPltHeaderId(pltId) == null) throw new InputPLTNotFoundException(pltId);
            }
        }

        InuringInputNode inuringInputNode = saveOrUpdateInuringInputNode(new InuringInputNode(request.getInuringPackageId(), request.getInputNodeName()));
        if (request.getAttachedPLTs() != null && ! request.getAttachedPLTs().isEmpty()) {
            for (Integer pltId : request.getAttachedPLTs()) {
                inuringInputAttachedPLTRepository.saveAndFlush(new InuringInputAttachedPLT(inuringInputNode.getInuringInputNodeId(), pltId));
            }
        }
    }

    public InuringInputNode saveOrUpdateInuringInputNode(InuringInputNode inuringInputNode) {
        return inuringInputNodeRepository.saveAndFlush(inuringInputNode);
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
    }
}
