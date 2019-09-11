package com.scor.rr.service;

import com.scor.rr.entity.InuringInputAttachedPLT;
import com.scor.rr.entity.InuringInputNode;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.InuringInputNodeRepository;
import com.scor.rr.repository.InuringInputAttachedPLTRepository;
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

    public void createInuringInputNode(InuringInputNodeCreationRequest request) throws RRException {

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
}
