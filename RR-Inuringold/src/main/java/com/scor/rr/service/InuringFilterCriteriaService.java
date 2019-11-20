package com.scor.rr.service;

import com.scor.rr.entity.*;
import com.scor.rr.enums.InuringElementType;
import com.scor.rr.enums.InuringNodeStatus;
import com.scor.rr.enums.InuringNodeType;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.*;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringFilterCriteriaCreationRequest;
import com.scor.rr.request.InuringFilterCriteriaUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.Map.Entry;

@Service
@Transactional
public class InuringFilterCriteriaService {

    @Autowired
    private InuringFilterCriteriaRepository inuringFilterCriteriaRepository;

    @Autowired
    private InuringContractLayerRepository inuringContractLayerRepository;

    @Autowired
    private InuringEdgeRepository inuringEdgeRepository;

    @Autowired
    private InuringFinalNodeRepository inuringFinalNodeRepository;

    @Autowired
    private InuringContractNodeRepository inuringContractNodeRepository;
    @Autowired
    private InuringPackageRepository inuringPackageRepository;

    public void createInuringFilterCriteria(InuringFilterCriteriaCreationRequest request) throws RRException{

        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(request.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());

        if(! checkInuringElementExisting(request.getInuringObjectType(),request.getInuringObjectId())) throw new InuringNodeNotFoundException(request.getInuringObjectType().toString(),request.getInuringObjectId());

        InuringFilterCriteria inuringFilterCriteria = new InuringFilterCriteria(request.getInuringObjectType(),
                request.getInuringObjectId(),
                request.getFilterKey(),
                request.getFilterValue(),
                request.isIncluding());

        inuringFilterCriteriaRepository.save(inuringFilterCriteria);
    }

    public void deleteInuringFilterCriteriaById(int inuringFilterCriteriaId){
        inuringFilterCriteriaRepository.deleteByInuringFilterCriteriaId(inuringFilterCriteriaId);
    }


    public void updateInuringFilterCriteria(InuringFilterCriteriaUpdateRequest request) throws RRException{
        InuringFilterCriteria inuringFilterCriteria = inuringFilterCriteriaRepository.findByInuringFilterCriteriaId(request.getInuringFilterCriteriaId());
        if(inuringFilterCriteria == null ) throw new InuringFilterCriteriaNotFoundException(request.getInuringFilterCriteriaId());

        inuringFilterCriteria.setFilterKey(request.getFilterKey());
        inuringFilterCriteria.setIncluding(request.isIncluding());
        inuringFilterCriteria.setFilterValue(request.getFilterValue());

        inuringFilterCriteriaRepository.save(inuringFilterCriteria);

        if(request.getInuringObjectType() == InuringElementType.Edge){

            InuringEdge inuringEdge = inuringEdgeRepository.findByInuringEdgeId(request.getInuringObjectId());
            if(inuringEdge == null ) throw new InuringEdgeNodeFoundException(request.getInuringObjectId());

            treatTheTargetNode(inuringEdge.getInuringEdgeId());

        }
        if(request.getInuringObjectType() == InuringElementType.ContractLayer){

            InuringContractLayer inuringContractLayer = inuringContractLayerRepository.findByInuringContractLayerId(request.getInuringObjectId());
            if(inuringContractLayer == null ) throw new InuringContractLayerNotFoundException(request.getInuringObjectId());

            updateTheInuringContractNode(inuringContractLayer.getInuringContractNodeId());

        }
    }

    private void updateTheInuringContractNode(int inuringContractNodeId) throws RRException {
        InuringContractNode inuringContractNode = inuringContractNodeRepository.findByInuringContractNodeId(inuringContractNodeId);
        if(inuringContractNode == null ) throw new InuringContractNodeNotFoundException(inuringContractNodeId);

        inuringContractNode.setContractNodeStatus(InuringNodeStatus.Invalid);
        inuringContractNodeRepository.save(inuringContractNode);

        List<InuringEdge> listOfEdges = getListOfEdges(inuringContractNode.getInuringContractNodeId(),InuringNodeType.ContractNode);

        for (InuringEdge inuringEdge: listOfEdges) {
            treatTheTargetNode(inuringEdge.getInuringEdgeId());
        }
    }

    private void treatTheTargetNode(int inuringEdgeId) throws RRException{
        Map<Integer,InuringNodeType> map = getTargetNode(inuringEdgeId);

        Set<Entry<Integer, InuringNodeType>> setHm = map.entrySet();
        Iterator<Entry<Integer, InuringNodeType>> it = setHm.iterator();
        while(it.hasNext()){
            Entry<Integer, InuringNodeType> e = it.next();

            if(e.getValue() == InuringNodeType.FinalNode){
                InuringFinalNode inuringFinalNode = inuringFinalNodeRepository.findByInuringFinalNodeId(e.getKey());
                if(inuringFinalNode == null ) throw new InuringFinalNodeNotFoundException(e.getKey());

                inuringFinalNode.setFinalNodeStatus(InuringNodeStatus.Invalid);
                inuringFinalNodeRepository.save(inuringFinalNode);
            }

            if(e.getValue() == InuringNodeType.ContractNode){
                updateTheInuringContractNode(e.getKey());
            }
        }
    }

    private boolean checkInuringElementExisting(InuringElementType elementType, int elementId) {
        switch (elementType) {
            case ContractLayer:
                return inuringContractLayerRepository.findByInuringContractLayerId(elementId) != null;
            case Edge:
                return inuringEdgeRepository.findByInuringEdgeId(elementId) != null;
            default:
                return false;
        }

    }
    /**the sourceNodeType should always be ContractNodeType**/
    private List<InuringEdge> getListOfEdges(int sourceNodeId, InuringNodeType sourceNodeType){
        return inuringEdgeRepository.findAllBySourceNodeIdAndSourceNodeType(sourceNodeId,sourceNodeType);
    }

    private Map<Integer, InuringNodeType> getTargetNode(int inuringEdgeId) throws RRException{
        Map<Integer,InuringNodeType> hm = new HashMap<>();
        InuringEdge inuringEdge = inuringEdgeRepository.findByInuringEdgeId(inuringEdgeId);
        if(inuringEdge == null ) throw new InuringEdgeNodeFoundException(inuringEdgeId);
        hm.put(inuringEdge.getTargetNodeId(),inuringEdge.getTargetNodeType());
        return hm;
    }

    public List<InuringFilterCriteria> readInuringFilterCriteria(int inuringObjectId,  InuringElementType inuringElementType){
        List<InuringFilterCriteria> inuringFilterCriterias = inuringFilterCriteriaRepository.findByInuringObjectIdAndInuringObjectType(inuringObjectId,inuringElementType);
        return inuringFilterCriterias;
    }
}
