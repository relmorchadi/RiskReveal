package com.scor.rr.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.scor.rr.JsonFormat.*;
import com.scor.rr.entity.*;
import com.scor.rr.enums.*;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.*;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringPackageCreationRequest;
import com.scor.rr.response.InuringPackageDetailsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private InuringCanvasLayoutRepository inuringCanvasLayoutRepository;
    @Autowired
    private InuringEdgeRepository inuringEdgeRepository;
    @Autowired
    private InuringFilterCriteriaRepository inuringFilterCriteriaRepository;
    @Autowired
    private InuringContractLayerRepository inuringContractLayerRepository;
    @Autowired
    private InuringContractLayerParamRepository inuringContractLayerParamRepository;
    @Autowired
    private InuringContractLayerReinstatementDetailRepository inuringContractLayerReinstatementDetailRepository;
    @Autowired
    private InuringContractLayerPerilLimitRepository inuringContractLayerPerilLimitRepository;

    private static  Map<String, Integer> nodeLevelMap;
    private static  Map<String, Integer> nodeIndexMap;

    public void createInuringPackage(InuringPackageCreationRequest request) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.saveAndFlush(new InuringPackage(request.getPackageName(),
                request.getPackageDescription(),
                request.getWorkspaceId(),
                request.getCreatedBy()));
        inuringFinalNodeService.createInuringFinalNodeForPackage(inuringPackage.getInuringPackageId());
    }

    public void deleteInuringPackage(long inuringPackageId) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(inuringPackageId);
        if (inuringPackage == null) throw new InuringPackageNotFoundException(inuringPackageId);
        if (inuringPackage.isLocked()) throw new InuringIllegalModificationException(inuringPackageId);
        inuringPackageRepository.deleteById(inuringPackageId);
    }

    public InuringPackage findByInuringPackageId(long inuringPackageId) {
        return inuringPackageRepository.findByInuringPackageId(inuringPackageId);
    }

    public InuringPackageDetailsResponse readInuringPackageDetail(long inuringPackageId) throws RRException {
        InuringPackageDetailsResponse inuringPackageDetailsResponse = new InuringPackageDetailsResponse();

        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(inuringPackageId);
        if (inuringPackage == null) throw new InuringPackageNotFoundException(inuringPackageId);
        inuringPackageDetailsResponse.setInuringPackage(inuringPackage);

        List<InuringInputNode> inuringInputNodes = inuringInputNodeRepository.findByInuringPackageId(inuringPackageId);
        inuringPackageDetailsResponse.setInputNodes(inuringInputNodes);

        List<InuringContractNode> inuringContractNodes = inuringContractNodeRepository.findByInuringPackageId(inuringPackageId);
        inuringPackageDetailsResponse.setInuringContractNodes(inuringContractNodes);

        List<InuringEdge> inuringEdges = inuringEdgeRepository.findByInuringPackageId(inuringPackageId);
        inuringPackageDetailsResponse.setEdges(inuringEdges);

        List<InuringCanvasLayout> inuringCanvasLayouts = inuringCanvasLayoutRepository.findByInuringPackageId(inuringPackageId);
        inuringPackageDetailsResponse.setInuringCanvasLayouts(inuringCanvasLayouts);

        InuringFinalNode inuringFinalNode = inuringFinalNodeRepository.findByInuringPackageId(inuringPackageId);
        if (inuringFinalNode == null) throw new InuringFinalNodeNotFoundException(inuringPackageId);
        inuringPackageDetailsResponse.setInuringFinalNode(inuringFinalNode);

        return inuringPackageDetailsResponse;


    }

    public void invalidateNode(InuringNodeType nodeType, long nodeId) throws RRException {
        switch (nodeType) {
            case InputNode:
                InuringInputNode inputNode = inuringInputNodeRepository.findByInuringInputNodeId(nodeId);
                if (inputNode == null) throw new InuringInputNodeNotFoundException(nodeId);
                inputNode.setInputInuringNodeStatus(InuringNodeStatus.Invalid);
                inuringInputNodeRepository.save(inputNode);
                break;
            case ContractNode:
                InuringContractNode inuringContractNode = inuringContractNodeRepository.findByInuringContractNodeId(nodeId);
                if (inuringContractNode == null) throw new InuringContractNodeNotFoundException(nodeId);
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
                throw new InuringNodeNotFoundException(nodeType.name(), nodeId);
        }
    }

    private void generateIndexMap(long inuringPackageId) {

        nodeIndexMap = new HashMap<>();
        int nodeCounter = 1;
        int edgeCounter = 1;
        List<InuringInputNode> inuringInputNodes = inuringInputNodeRepository.findByInuringPackageId(inuringPackageId);
        if(inuringInputNodes != null && !inuringInputNodes.isEmpty()){
            for(InuringInputNode inputNode: inuringInputNodes){
                nodeIndexMap.put(InuringNodeType.InputNode+"-"+inputNode.getInuringInputNodeId(),nodeCounter);
                nodeCounter++;
            }
        }
        List<InuringContractNode> inuringContractNodes = inuringContractNodeRepository.findByInuringPackageId(inuringPackageId);
        if(inuringContractNodes != null && !inuringContractNodes.isEmpty()){
            for(InuringContractNode contractNode: inuringContractNodes){
                nodeIndexMap.put(InuringNodeType.ContractNode+"-"+contractNode.getInuringContractNodeId(),nodeCounter);
                nodeCounter++;
            }
        }

        List<InuringEdge> inuringEdges = inuringEdgeRepository.findByInuringPackageId(inuringPackageId);
        if(inuringEdges != null && !inuringEdges.isEmpty()){
            for(InuringEdge inuringEdge : inuringEdges){
                nodeIndexMap.put("edge"+"-"+inuringEdge.getInuringEdgeId(),edgeCounter);
                edgeCounter++;
            }
        }
        InuringFinalNode finalNode = inuringFinalNodeRepository.findByInuringPackageId(inuringPackageId);
        nodeIndexMap.put(InuringNodeType.FinalNode+"-"+finalNode.getInuringFinalNodeId(),nodeCounter);
    }

    private void generateMapLevel(List<InuringInputNode> inputNodes) {
           nodeLevelMap = new HashMap<>();
        if (inputNodes != null && !inputNodes.isEmpty()) {
            for (InuringInputNode inputNode : inputNodes) {
                String nodeId = InuringNodeType.InputNode + "-" + inputNode.getInuringInputNodeId();
                nodeLevelMap.put(nodeId, 0);
                findNodeLevel(nodeLevelMap, inputNode.getInuringInputNodeId(),InuringNodeType.InputNode);
            }
        }
    }

    private void findNodeLevel(Map<String, Integer> nodeLevelMap, long sourceNodeId, InuringNodeType nodeType) {
        List<InuringEdge> edgesFromSourceNode = inuringEdgeRepository.findAllBySourceNodeIdAndSourceNodeType(sourceNodeId,nodeType);
        if (edgesFromSourceNode != null && !edgesFromSourceNode.isEmpty()) {
            for (InuringEdge inuringObjectEdge : edgesFromSourceNode) {
                String nodeId = inuringObjectEdge.getTargetNodeType() + "-" + inuringObjectEdge.getTargetNodeId();
                String sNodeId = nodeType + "-" + sourceNodeId;
                int level = nodeLevelMap.get(nodeId) != null ? nodeLevelMap.get(nodeId) : 0;
                if (level < nodeLevelMap.get(sNodeId) + 1) {
                    level = nodeLevelMap.get(sNodeId) + 1;
                }
                nodeLevelMap.put(nodeId, level);
                findNodeLevel(nodeLevelMap,inuringObjectEdge.getTargetNodeId(),inuringObjectEdge.getTargetNodeType());
            }
        }
    }

    private List<ContractNodeList> generateContractNodeList(long inuringPackageId, long inuringFinalNodeId) {
        List<ContractNodeList> contractNodeLists = new ArrayList<ContractNodeList>();
        List<InuringContractNode> inuringContractNodes = inuringContractNodeRepository.findByInuringPackageId(inuringPackageId);

        if (inuringContractNodes != null && !inuringContractNodes.isEmpty()) {
            for (InuringContractNode inuringContractNode : inuringContractNodes
            ) {
                ContractNodeList contractNodeList = new ContractNodeList();
                contractNodeList.setIndex(nodeIndexMap.get(InuringNodeType.ContractNode+"-"+inuringContractNode.getInuringContractNodeId()));

                contractNodeList.setId(inuringContractNode.getInuringContractNodeId());
                contractNodeList.setLevel(nodeLevelMap.get(InuringNodeType.ContractNode+"-"+inuringContractNode.getInuringContractNodeId()));
                contractNodeList.setContractType(inuringContractNode.getContractTypeCode());
                contractNodeList.setFinal(false);

                List<Integer> input = new ArrayList<Integer>();
                List<Integer> edge = new ArrayList<Integer>();
                List<InuringEdge> inuringEdges = inuringEdgeRepository.findAllByTargetNodeIdAndTargetNodeType(inuringContractNode.getInuringContractNodeId(),InuringNodeType.ContractNode);
                if(inuringEdges != null && !inuringEdges.isEmpty()){
                    for(InuringEdge inuringEdge: inuringEdges){
                        edge.add(nodeIndexMap.get("edge"+"-"+inuringEdge.getInuringEdgeId()));
                        if(inuringEdge.getSourceNodeType() == InuringNodeType.InputNode){
                            input.add(nodeIndexMap.get(InuringNodeType.InputNode+"-"+inuringEdge.getSourceNodeId()));
                        }
                    }


                }
                contractNodeList.setInputNodeToLoad(input);
                contractNodeList.setEdgeToLoad(edge);
                contractNodeList.setContractCurrencyCode(inuringContractNode.getContractCurrency());

                List<InuringContractLayer> layers = inuringContractLayerRepository.findByInuringContractNodeId(inuringContractNode.getInuringContractNodeId());
                List<Object> attributes = new ArrayList<>();
                if(layers != null && !layers.isEmpty()){
                    for(InuringContractLayer layer : layers){

                        List<InuringContractLayerParam> inuringContractLayerParamList = inuringContractLayerParamRepository.findByInuringContractLayerId(layer.getInuringContractLayerId());
                        List<InuringContractLayerReinstatementDetail> inuringContractLayerReinstatementDetailList = inuringContractLayerReinstatementDetailRepository.findByInuringContractLayerId(layer.getInuringContractLayerId());
                        List<InuringContractLayerPerilLimit> inuringContractLayerPerilLimitList = inuringContractLayerPerilLimitRepository.findByInuringContractLayerId(layer.getInuringContractLayerId());
                        List<InuringFilterCriteria> inuringFilterCriteriaList = inuringFilterCriteriaRepository.findByInuringObjectIdAndInuringObjectType(layer.getInuringContractLayerId(),InuringElementType.ContractLayer);

                        JsonObject innerObject = new JsonObject();
                        innerObject.addProperty("layerNumber", layer.getLayerNumber());
                        innerObject.addProperty("layerSequence", layer.getLayerSequence());
                        innerObject.addProperty("layerCode", layer.getLayerCode());
                        innerObject.addProperty("layerCurrencyCode", layer.getLayerCurrency());
                        innerObject.addProperty("layerDescription", layer.getLayerDescription());

                        if(inuringContractLayerParamList != null && !inuringContractLayerParamList.isEmpty()){
                            for(InuringContractLayerParam inuringContractLayerParam : inuringContractLayerParamList){
                                innerObject.addProperty(inuringContractLayerParam.getParamName(),inuringContractLayerParam.getParamValue());

                            }
                        }
                        JsonArray reinstatementDetailList = new JsonArray();
                        JsonArray layerPerilLimitList = new JsonArray();
                        JsonArray layerFilterList = new JsonArray();

                        if(inuringContractLayerReinstatementDetailList != null && !inuringContractLayerReinstatementDetailList.isEmpty()){
                            for(InuringContractLayerReinstatementDetail inuringContractLayerReinstatementDetail : inuringContractLayerReinstatementDetailList){
                                JsonObject reinstatementDetail = new JsonObject();
                                reinstatementDetail.addProperty("reinstatementsRank",inuringContractLayerReinstatementDetail.getReinstatementsRank());
                                reinstatementDetail.addProperty("reinstatementsNumber",inuringContractLayerReinstatementDetail.getReinstatementsNumber());
                                reinstatementDetail.addProperty("reinstatementsCharge",inuringContractLayerReinstatementDetail.getReinstatementsCharge());
                                reinstatementDetail.addProperty("prorataTemporis","to be added");

                                reinstatementDetailList.add(reinstatementDetail);
                            }
                        }
                        innerObject.add("reinstatementDetailList",reinstatementDetailList);

                        if(inuringContractLayerPerilLimitList != null && !inuringContractLayerPerilLimitList.isEmpty()){
                            for(InuringContractLayerPerilLimit inuringContractLayerPerilLimit: inuringContractLayerPerilLimitList){
                                JsonObject layerPerilLimit  = new JsonObject();
                                layerPerilLimit.addProperty("peril",inuringContractLayerPerilLimit.getPeril());
                                layerPerilLimit.addProperty("perilLimit",inuringContractLayerPerilLimit.getLimit());


                                layerPerilLimitList.add(layerPerilLimit);
                            }
                        }
                        innerObject.add("perilLimitList",layerPerilLimitList);

                        if(inuringFilterCriteriaList != null && !inuringFilterCriteriaList.isEmpty()){
                            for(InuringFilterCriteria inuringFilterCriteria: inuringFilterCriteriaList){
                                JsonObject layerFilter  = new JsonObject();
                                layerFilter.addProperty("filterKey",inuringFilterCriteria.getFilterKey());
                                layerFilter.addProperty("filterValue",inuringFilterCriteria.getFilterValue());
                                layerFilter.addProperty("including",inuringFilterCriteria.isIncluding());


                                layerFilterList.add(layerFilter);
                            }
                        }
                        innerObject.add("filterCriteriaList",layerFilterList);

                        Gson gson = new Gson();
                        attributes.add(gson.fromJson(innerObject.toString(),Object.class));



                    }

                }
                contractNodeList.setAttributes(attributes);

                contractNodeLists.add(contractNodeList);
            }
        }


        ContractNodeList contractNodeList = new ContractNodeList();
        contractNodeList.setIndex(nodeIndexMap.get(InuringNodeType.FinalNode+"-"+inuringFinalNodeId));
        contractNodeList.setId(inuringFinalNodeId);
        contractNodeList.setLevel(nodeLevelMap.get(InuringNodeType.FinalNode+ "-" + inuringFinalNodeId));
        contractNodeList.setFinal(true);
        List<Integer> edges = new ArrayList<Integer>();
        List<Integer> input = new ArrayList<Integer>();
        List<InuringEdge> inuringEdges = inuringEdgeRepository.findAllByTargetNodeIdAndTargetNodeType(inuringFinalNodeId,InuringNodeType.FinalNode);
        if(inuringEdges != null && !inuringEdges.isEmpty()){
            for(InuringEdge inuringEdge: inuringEdges){
                edges.add(nodeIndexMap.get("edge"+"-"+inuringEdge.getInuringEdgeId()));
            }
        }
        contractNodeList.setInputNodeToLoad(input);
        contractNodeList.setEdgeToLoad(edges);

        contractNodeLists.add(contractNodeList);

        return contractNodeLists;
    }

    private List<InputNodeList> generateInputNodeList(long inuringPackageId) {
        List<InuringInputNode> inuringInputNodes = inuringInputNodeRepository.findByInuringPackageId(inuringPackageId);
        generateMapLevel(inuringInputNodes);

        List<InputNodeList> inputNodeLists = new ArrayList<InputNodeList>();

        if (inuringInputNodes != null && !inuringInputNodes.isEmpty()) {
            for (InuringInputNode inuringInputNode : inuringInputNodes
            ) {

                InputNodeList inputNodeList = new InputNodeList();
                inputNodeList.setIndex(nodeIndexMap.get(InuringNodeType.InputNode+"-"+inuringInputNode.getInuringInputNodeId()));

                inputNodeList.setId(inuringInputNode.getInuringInputNodeId());
                inputNodeList.setSign("+");
                inputNodeList.setNbSelectedPLT(1);

                List<SelectedPLT> selectedPLTS = new ArrayList<SelectedPLT>();

                SelectedPLT selectedPLT = new SelectedPLT();
                selectedPLT.setFilename("ff");
                selectedPLT.setPath("ff");
                selectedPLT.setCurrency("ff");
                selectedPLT.setTargetCurrency("ff");
                selectedPLT.setSourceName("ff");
                selectedPLT.setTargetRapId(1);
                selectedPLT.setTargetRapCode("ff");
                selectedPLT.setRegionPeril("ff");
                selectedPLT.setPeril("ff");
                selectedPLT.setGrain("ff");
                selectedPLT.setPltStructureCode(0);

                selectedPLTS.add(selectedPLT);


                inputNodeList.setSelectedPLT(selectedPLTS);

                inputNodeLists.add(inputNodeList);
            }
        }


        return inputNodeLists;
    }

    private List<EdgeList> generateEdgeList(long inuringPackageId) {
        List<EdgeList> edgeLists = new ArrayList<EdgeList>();
        List<InuringEdge> inuringEdges = inuringEdgeRepository.findByInuringPackageId(inuringPackageId);
        if (inuringEdges != null && !inuringEdges.isEmpty()) {
            for (InuringEdge inuringEdge : inuringEdges
            ) {
                EdgeList edgeList = new EdgeList();
                edgeList.setIndex(nodeIndexMap.get("edge"+"-"+inuringEdge.getInuringEdgeId()));

                edgeList.setId(inuringEdge.getInuringEdgeId());

                String sign = "";
                if (inuringEdge.getFinancialTreatment() == InuringFinancialTreatment.Positive) sign = "+";
                if (inuringEdge.getFinancialTreatment() == InuringFinancialTreatment.Negative) sign = "-";
                edgeList.setSign(sign);

                edgeList.setPerspective(inuringEdge.getOutputPerspective());
                edgeList.setSourceNodeIndex(nodeIndexMap.get(inuringEdge.getSourceNodeType()+"-"+inuringEdge.getSourceNodeId()));
                edgeList.setTargetNodeIndex(nodeIndexMap.get(inuringEdge.getTargetNodeType()+"-"+inuringEdge.getTargetNodeId()));
                edgeList.setFilterCriteriaList(inuringFilterCriteriaRepository.findByInuringObjectIdAndInuringObjectType(inuringEdge.getInuringEdgeId(), InuringElementType.Edge));
                edgeList.setOutputAtLayerLevel(inuringEdge.isOutputAtLayerLevel());

                edgeLists.add(edgeList);


            }
        }

        return edgeLists;
    }

    public InuringPackageJsonResponse getJSON(long id) throws RRException {

        InuringPackageJsonResponse inuringPackageJsonResponse = new InuringPackageJsonResponse();
        generateIndexMap(id);

        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(id);
        if (inuringPackage == null) throw new InuringPackageNotFoundException(id);

        InuringFinalNode inuringFinalNode = inuringFinalNodeRepository.findByInuringPackageId(id);
        if (inuringFinalNode == null) throw new InuringFinalNodeNotFoundException(id);

        /**Getting the Package information**/
        inuringPackageJsonResponse.setFormatCode(1);
        inuringPackageJsonResponse.setInuringPackageId(inuringPackage.getInuringPackageId());
        inuringPackageJsonResponse.setCreationDate(inuringPackage.getCreatedOn());
        inuringPackageJsonResponse.setCreator("should be added after getting the user table");
        inuringPackageJsonResponse.setLastModificationDate(inuringPackage.getLastModifiedOn());
        inuringPackageJsonResponse.setLastModifier("should be added after getting the user table");
        inuringPackageJsonResponse.setOther("");

        boolean groupedToMinimumGrain = false;
        if (inuringFinalNode.getInuringOutputGrain() != InuringOutputGrain.MinimunRegionPeril) {
            groupedToMinimumGrain = true;
        }
        inuringPackageJsonResponse.setGroupedToMinimumGrainRP(groupedToMinimumGrain);
        List<String> finalNodeGroupingCriteria = new ArrayList<String>();
        finalNodeGroupingCriteria.add("MinimumGrainRegionPeril");
        finalNodeGroupingCriteria.add("TargetRap");
        inuringPackageJsonResponse.setFinalNodeGroupingCriteria(finalNodeGroupingCriteria);

        InuringNetworkDefinition inuringNetworkDefinition = new InuringNetworkDefinition();

        /**Getting the InputList information**/
        inuringNetworkDefinition.setInputNodeList(generateInputNodeList(id));

        /**Getting the edgeList information**/
        inuringNetworkDefinition.setEdgeList(generateEdgeList(id));

        /**Getting the ContractList information**/
        inuringNetworkDefinition.setContractNodeList(generateContractNodeList(id, inuringFinalNode.getInuringFinalNodeId()));



        inuringPackageJsonResponse.setInuringNetworkDefinition(inuringNetworkDefinition);

        for (Map.Entry<String, Integer> entry : nodeIndexMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue().toString());
        }

        return inuringPackageJsonResponse;
    }
}
