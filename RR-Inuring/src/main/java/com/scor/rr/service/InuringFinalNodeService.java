package com.scor.rr.service;

import com.google.gson.Gson;
import com.scor.rr.JsonFormat.*;
import com.scor.rr.domain.dto.FilterCriteriaList;
import com.scor.rr.domain.dto.InuringFinalAttachePltDto;
import com.scor.rr.entity.*;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringFinalAttachedPltNotFoundException;
import com.scor.rr.exceptions.inuring.InuringFinalNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.exceptions.inuring.InuringStructureNotValid;
import com.scor.rr.repository.*;
import com.scor.rr.request.InuringFinalAttachedPltUpdateRequest;
import com.scor.rr.request.InuringFinalNodeUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by u004602 on 16/09/2019.
 */
@Service
@Transactional
public class InuringFinalNodeService {
    @Autowired
    private InuringPackageRepository inuringPackageRepository;

    @Autowired
    private InuringFinalNodeRepository inuringFinalNodeRepository;
    @Autowired
    private InuringPackageService inuringPackageService;
    @Autowired
    private InuringFinalAttachedPltRepository inuringFinalAttachedPltRepository;

    public void createInuringFinalNodeForPackage(long inuringPackageId) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(inuringPackageId);
        if (inuringPackage == null) throw new InuringPackageNotFoundException(inuringPackageId);
        InuringFinalNode finalNode = new InuringFinalNode(inuringPackageId);
        inuringFinalNodeRepository.save(finalNode);
    }

    public void deleteByInuringPackageId(long inuringPackageId) {
        inuringFinalNodeRepository.deleteByInuringPackageId(inuringPackageId);
    }

    public void updateInuringFinalNode(InuringFinalNodeUpdateRequest request) throws RRException {
        InuringFinalNode inuringFinalNode = inuringFinalNodeRepository.findByInuringFinalNodeId(request.getInuringFinalNodeId());
        if (inuringFinalNode == null) throw new InuringFinalNodeNotFoundException(request.getInuringFinalNodeId());
        inuringFinalNode.setInuringOutputGrain(request.getInuringOutputGrain());
        inuringFinalNodeRepository.save(inuringFinalNode);
    }

    public InuringFinalNode readInuringFinalNodeByPackageId(long id) throws RRException {
        InuringFinalNode inuringFinalNode = inuringFinalNodeRepository.findByInuringPackageId(id);
        if (inuringFinalNode == null) throw new InuringFinalNodeNotFoundException(id);
        return inuringFinalNode;
    }

    public List<InuringFinalAttachedPLT> getListOfExpectedPLTs(long inuringFinalNodeId){
        return inuringFinalAttachedPltRepository.findAllByInuringFinalNodeId(inuringFinalNodeId);
    }

    public void updateFinalAttachedPLTs(InuringFinalAttachedPltUpdateRequest request) throws RRException {
        List<InuringFinalAttachedPLT> afterUpdateList = new ArrayList<>();
        for(InuringFinalAttachePltDto plt : request.getListOfPlts()){
            InuringFinalAttachedPLT contentPlt = inuringFinalAttachedPltRepository.findByInuringFinalAttachedPLT(plt.getInuringFinalAttachedPltId());
            if(contentPlt == null) throw new InuringFinalAttachedPltNotFoundException(plt.getInuringFinalAttachedPltId());

            contentPlt.setPLTCcy(plt.getInuringFinalAttachedPltCurrency());
            contentPlt.setPltName(plt.getInuringFinalAttachedPltName());
            afterUpdateList.add(contentPlt);
        }
        inuringFinalAttachedPltRepository.saveAll(afterUpdateList);
    }

    public void generateExpectedOutcomePLTS(int inuringPackageId) throws RRException, NoSuchFieldException, IllegalAccessException {

        Map<Integer, List<SelectedPLT>> contractMapOut = new HashMap<>();
        Map<Integer, InputNodeList> inputMap = new HashMap<>();
        Map<Integer, EdgeList> edgeMap = new HashMap<>();

        InuringPackageJsonResponse packageDetails = inuringPackageService.getJSON(inuringPackageId);

        InuringNetworkDefinition inuringNetworkDefinition = packageDetails.getInuringNetworkDefinition();

        List<InputNodeList> inputNodes = inuringNetworkDefinition.getInputNodeList();
        List<ContractNodeList> contractNodes = inuringNetworkDefinition.getContractNodeList();
        List<EdgeList> edges = inuringNetworkDefinition.getEdgeList();

        if (inputNodes == null || inputNodes.isEmpty()) throw new InuringStructureNotValid();
        if (contractNodes == null || contractNodes.isEmpty()) throw new InuringStructureNotValid();
        if (edges == null || edges.isEmpty()) throw new InuringStructureNotValid();

        for (InputNodeList input : inputNodes) {
            inputMap.put(input.getIndex(), input);
        }
        for (ContractNodeList contract : contractNodes) {
            contractMapOut.put(contract.getIndex(), new ArrayList<>());
        }
        for (EdgeList edge : edges) {
            edgeMap.put(edge.getIndex(), edge);
        }

        int size = inputNodes.size();
        int level = 1;
        int maxLevel = -1;

        for (ContractNodeList contract : contractNodes) {
            if (contract.isFinal()) {
                maxLevel = contract.getLevel();
            }
        }
        if (maxLevel == -1) throw new InuringStructureNotValid();
        int indexFinal = 0;
        long finalNodeId = 0;

        while(level <= maxLevel){
            for (ContractNodeList contract : contractNodes) {
                if (contract.getLevel() == level) {
                    List<Integer> edgeToLoad = contract.getEdgeToLoad();
                    if (edgeToLoad != null && !edgeToLoad.isEmpty()) {
                        for (int edgeId : edgeToLoad) {
                            EdgeList edge = edgeMap.get(edgeId);
                            int sourceIndex = edge.getSourceNodeIndex();
                            List<SelectedPLT> sourcePlts;
                            if (sourceIndex <= size) {
                                sourcePlts = inputMap.get(sourceIndex).getSelectedPLT();
                            } else {
                                sourcePlts = contractMapOut.get(sourceIndex);
                            }
                            List<InuringFilterCriteria> edgeFilters = edge.getFilterCriteriaList();
                            if (edgeFilters != null && !edgeFilters.isEmpty()) {
                                for (InuringFilterCriteria filter : edgeFilters) {
                                    List<String> filterValues = Arrays.asList(filter.getFilterValue().split(","));
                                    sourcePlts = applyFilters(filter.getFilterKey(), filterValues, filter.isIncluding(), sourcePlts);
                                }

                            }
                            List<List<SelectedPLT>> outcomePlts = new ArrayList<>();
                            boolean emptyFilters = true;

                            if(contract.isFinal()){
                                workTheMap(sourcePlts,contractMapOut,contract.getIndex());

                            }else{
                                for (Object attribute : contract.getAttributes()) {
                                    Gson json = new Gson();
                                    String jsonObject = json.toJson(attribute);
                                    List<FilterCriteriaList> fooBars = Gsons.asList(jsonObject, "FilterCriteriaList", FilterCriteriaList.class);

                                    if(!fooBars.isEmpty()){
                                        emptyFilters = false;
                                        List<SelectedPLT> outcomeOfLayer = sourcePlts;
                                        for(FilterCriteriaList filter : fooBars){
                                            String filterkey = filter.getFilterKey();
                                            List<String> filterValues = Arrays.asList(filter.getFilterValue().split(","));
                                            boolean isIncluding = filter.isIncluding();
                                            outcomeOfLayer = applyFilters(filterkey, filterValues, isIncluding, outcomeOfLayer);
                                        }
                                        outcomePlts.add(outcomeOfLayer);

                                    }

                                }
                                List<SelectedPLT> finalOutcomeFromContractNode = new ArrayList<>();
                                if(!outcomePlts.isEmpty()){
                                    for(List<SelectedPLT> selectedPLT : outcomePlts){
                                        for(SelectedPLT plt : selectedPLT){
                                            if(finalOutcomeFromContractNode.indexOf(plt) == -1){
                                                finalOutcomeFromContractNode.add(plt);
                                            }
                                        }
                                    }
                                    workTheMap(finalOutcomeFromContractNode,contractMapOut,contract.getIndex());

                                }else if(emptyFilters){
                                    workTheMap(sourcePlts,contractMapOut,contract.getIndex());
                                }
                            }




                        }
                    }
                }

                if(level == maxLevel){
                    indexFinal = contract.getIndex();
                    finalNodeId = contract.getId();
                }
            }

            level++;
        }
        List<InuringFinalAttachedPLT> returnList = new ArrayList<>();
        List<SelectedPLT> finalList = contractMapOut.get(indexFinal);
        if(finalList != null && !finalList.isEmpty()){

            for(SelectedPLT plt: finalList){

                InuringFinalAttachedPLT returnPlt = new InuringFinalAttachedPLT();
                returnPlt.setPltName(plt.getFilename());
                returnPlt.setPLTCcy(plt.getCurrency());
                returnPlt.setGrouped(false);
                returnPlt.setOriginalPLTId(plt.getSourceName());
                returnPlt.setOccurrenceBasisCode("PerEvent");
                returnPlt.setPeril(plt.getPeril());
                returnPlt.setMinimumGrainRegionPerilCode(plt.getRegionPeril());
                returnPlt.setTargetRapId(plt.getTargetRapId());
                returnPlt.setInuringFinalNodeId(finalNodeId);
                returnPlt.setEntity(1);
                returnList.add(returnPlt);
            }

        }

        inuringFinalAttachedPltRepository.deleteAllByInuringFinalNodeId(finalNodeId);
        createFinalAttachedPlts(returnList);

    }

    public void createFinalAttachedPlts(List<InuringFinalAttachedPLT> outcomePlts){
        inuringFinalAttachedPltRepository.saveAll(outcomePlts);

        List<InuringFinalAttachedPLT> combinedGrainRegionPerilPlts = new ArrayList<>();
        List<String> lisOfTargetRaRegionPeril = new ArrayList<>();
        for(InuringFinalAttachedPLT plt : outcomePlts ){
            String content = plt.getTargetRapId()+"-"+plt.getMinimumGrainRegionPerilCode();
             if(lisOfTargetRaRegionPeril.indexOf(content)  == -1){
                 lisOfTargetRaRegionPeril.add(content);
                 InuringFinalAttachedPLT returnPlt = new InuringFinalAttachedPLT();
                 returnPlt.setPltName("combined PLT");
                 returnPlt.setPLTCcy(plt.getPLTCcy());
                 returnPlt.setGrouped(true);
                 returnPlt.setOccurrenceBasisCode("PerEvent");
                 returnPlt.setMinimumGrainRegionPerilCode(plt.getMinimumGrainRegionPerilCode());
                 returnPlt.setTargetRapId(plt.getTargetRapId());
                 returnPlt.setInuringFinalNodeId(plt.getInuringFinalNodeId());
                 returnPlt.setEntity(1);
                 combinedGrainRegionPerilPlts.add(returnPlt);
             }
        }
        inuringFinalAttachedPltRepository.saveAll(combinedGrainRegionPerilPlts);


    }

    private void workTheMap(List<SelectedPLT> inputPlts,Map<Integer,List<SelectedPLT>> outcomeMap,int index){
        List<SelectedPLT> pltsAlreadyInMap = outcomeMap.get(index);
        if(pltsAlreadyInMap.isEmpty()){
            outcomeMap.put(index,inputPlts);
        }else{
            for(SelectedPLT plt : inputPlts){
                if(pltsAlreadyInMap.indexOf(plt) == -1){
                    pltsAlreadyInMap.add(plt);
                }
            }
            outcomeMap.put(index,pltsAlreadyInMap);
        }
    }

    public List<SelectedPLT> applyFilters(String filterKey, List<String> filterValues, boolean including, List<SelectedPLT> input) throws RRException {
        switch (filterKey) {
            case "Peril":
                if (including) {
                    List<SelectedPLT> found = new ArrayList<SelectedPLT>();
                    for (SelectedPLT plt : input) {
                        if (!filterValues.contains(plt.getPeril())) {
                            found.add(plt);
                        }
                    }
                    input.removeAll(found);
                } else {
                    List<SelectedPLT> found = new ArrayList<SelectedPLT>();
                    for (SelectedPLT plt : input) {
                        if (filterValues.contains(plt.getPeril())) {
                            found.add(plt);
                        }
                    }
                    input.removeAll(found);
                }

                break;
            case "Region-Peril":
                if (including) {
                    List<SelectedPLT> found = new ArrayList<SelectedPLT>();
                    for (SelectedPLT plt : input) {
                        if (!filterValues.contains(plt.getRegionPeril())) {
                            found.add(plt);
                        }
                    }
                    input.removeAll(found);
                } else {
                    List<SelectedPLT> found = new ArrayList<SelectedPLT>();
                    for (SelectedPLT plt : input) {
                        if (filterValues.contains(plt.getRegionPeril())) {
                            found.add(plt);
                        }
                    }
                    input.removeAll(found);
                }
                break;
            case "Grain":
                if (including) {
                    List<SelectedPLT> found = new ArrayList<SelectedPLT>();
                    for (SelectedPLT plt : input) {
                        if (!filterValues.contains(plt.getGrain())) {
                            found.add(plt);
                        }
                    }
                    input.removeAll(found);
                } else {
                    List<SelectedPLT> found = new ArrayList<SelectedPLT>();
                    for (SelectedPLT plt : input) {
                        if (filterValues.contains(plt.getGrain())) {
                            found.add(plt);
                        }
                    }
                    input.removeAll(found);
                }
                break;
            default:
                throw new InuringStructureNotValid();

        }
        return input;
    }
}
