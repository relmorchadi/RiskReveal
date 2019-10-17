//package com.scor.rr.importBatch.processing.adjustment;
//
//import com.google.common.base.Predicate;
//import com.google.common.collect.Collections2;
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;
//import com.google.gson.stream.MalformedJsonException;
//import com.scor.almf.ihub.treaty.processing.io.JSONReader;
//import com.scor.almf.ihub.treaty.processing.io.JSONWriter;
//import com.scor.almf.ihub.treaty.processing.treaty.services.TransformationUtils;
//import com.scor.almf.ihub.treaty.processing.ylt.meta.XLTSubType;
//import com.scor.almf.treaty.cdm.adjustment.meta.Context;
//import com.scor.almf.treaty.cdm.adjustment.meta.Node;
//import com.scor.almf.treaty.cdm.domain.adjustment.*;
//import com.scor.almf.treaty.cdm.domain.adjustment.AdjustmentBasis;
//import com.scor.almf.treaty.cdm.domain.adjustment.meta.*;
//import com.scor.almf.treaty.cdm.domain.adjustment.meta.AdjustmentCategory;
//import com.scor.almf.treaty.cdm.domain.dss.agnostic.meta.RRAnalysis;
//import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RREPCurve;
//import com.scor.almf.treaty.cdm.domain.dss.agnostic.stat.RRStatisticHeader;
//import com.scor.almf.treaty.cdm.domain.plt.ScorPLTHeader;
//import com.scor.almf.treaty.cdm.domain.plt.meta.StatisticMetric;
//import com.scor.almf.treaty.cdm.domain.rap.TargetRap;
//import com.scor.almf.treaty.cdm.domain.rap.TargetRapDefaultModellingOption;
//import com.scor.almf.treaty.cdm.domain.rap.meta.ExpectedState;
//import com.scor.almf.treaty.cdm.domain.reference.meta.BinFile;
//import com.scor.almf.treaty.cdm.domain.region.RegionPeril;
//import com.scor.almf.treaty.cdm.domain.workspace.Project;
//import com.scor.almf.treaty.cdm.repository.adjustment.AdjustmentNodeRepository;
//import com.scor.almf.treaty.cdm.repository.dss.agnostic.RRAnalysisRepository;
//import com.scor.almf.treaty.cdm.repository.dss.agnostic.RRStatisticHeaderRepository;
//import com.scor.almf.treaty.cdm.repository.plt.ScorPLTHeaderRepository;
//import com.scor.almf.treaty.cdm.tools.AdjustmentUtils;
//import com.scor.almf.treaty.cdm.tools.sequence.MongoDBSequence;
//import com.scor.almf.treaty.dao.DAOService;
//import com.scor.almf.treaty.io.CSVWriter;
//import com.scor.rr.domain.entities.references.omega.BinFile;
//import com.scor.rr.importBatch.processing.io.CSVWriter;
//import com.scor.rr.importBatch.processing.io.JSONReader;
//import com.scor.rr.importBatch.processing.io.JSONWriter;
//import com.scor.rr.repository.plt.RRAnalysisRepository;
//import com.scor.rr.repository.plt.ScorPLTHeaderRepository;
//import com.scor.rr.repository.stat.RRStatisticHeaderRepository;
//import com.scor.rr.service.omega.DAOService;
//import org.apache.commons.lang.BooleanUtils;
//import org.apache.commons.lang.NotImplementedException;
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.reflect.Type;
//import java.text.DecimalFormat;
//import java.util.*;
//
//public class TreeHandlerImpl implements TreeHandler {
//
//    private static final Logger log = LoggerFactory.getLogger(TreeHandlerImpl.class);
//
//    @Autowired
//    private DAOService daoService;
//
//    @Autowired
//    private AdjustmentNodeRepository adjustmentNodeRepository;
//
//    @Autowired
//    private ScorPLTHeaderRepository scorPLTHeaderRepository;
//
////    @Autowired
////    private PLTEPHeaderRepository pltepHeaderRepository;
//
//    @Autowired
//    private RRStatisticHeaderRepository rrStatisticHeaderRepository;
//
////    @Autowired
////    MongoDBSequence mongoDBSequence;
//
//    @Autowired
//    private CSVWriter cSVWriter;
//
//    @Value("${ihub.treaty.out.path}")
//    private String tempOutPath;
//
//    @Autowired
//    private JSONReader jSONReader;
//
//    @Autowired
//    private JSONWriter jSONWriter;
//
//    @Autowired
//    private AdjustmentProcessor adjustmentProcessor;
//
//    @Autowired
//    private RRAnalysisRepository rrAnalysisRepository;
//
//    private String uriRequestCalculate;
//
//    private BinFile purePLTLossFile;
//
//    private CalculationRequestCallback callback;
//
//    @Value("${ihub.treaty.peat.path}")
//    private String peatPath;
//
//    static final Type typeContext = new TypeToken<Context>(){}.getType();
//    static final Type typeCloneDuo = new TypeToken<CloneDuo>(){}.getType();
//    static final Type typeNode = new TypeToken<Node>(){}.getType();
//
//
//
//*
//     * Object for storing data sent from fronted
//     * Couples of unique ids of cloning node and cloned node
//
//
//    private class CloneDuo {
//        public String cloningId;
//        public String clonedId;
//    }
//
//    private void requestCalculate(String structureId) {
//        // int response = adjustmentProcessor.processTree(structureId);
//        callback.onCalculationRequested(structureId);
//        // log.info("requestCalculate response = {}", response);
//    }
//
//
//    @Override
//    public void setCalculationRequestCallback(CalculationRequestCallback callback) {
//        this.callback = callback;
//    }
//
//    @Override
//    public boolean updateTree(String json) {
//        AdjustmentStructure adjustmentStructure = null;
//        try {
//            adjustmentStructure = updateAdjustmentStructure(json);
//
//            updateAdjustmentNodes(adjustmentStructure);
//        } catch (MalformedJsonException e) {
//            log.error("POST.update Exception: " + e);
//            return false;
//        }
//
////        requestCalculate(adjustmentStructure.getId());
//        return true;
//    }
//
//    @Override
//    public boolean adjustDefault(String projectId, String pltHeaderId, Long analysisId, String analysisName, Long rdmId, String rdmName, Integer targetRapId, String sourceConfigVendor) {
//        log.info("DEFAULT ADJUSTMENT for projectId {} analysisId {} targetRapId = {}", projectId, analysisId, targetRapId);
//        Project project = daoService.findProjectBy(projectId);
//        ScorPLTHeader purePLTHeader = daoService.findScorPLTHeaderById(pltHeaderId);
//        log.info("DEFAULT ADJUSTMENT for RDM {} - {}, PLT {}, PLTHeader = {}", rdmName, rdmId, pltHeaderId, purePLTHeader);
//        if (purePLTHeader == null) {
//            log.error("No mongoDB document found for PLT {}. Default adjustment error!", pltHeaderId);
//            return false;
//        }
//
//        String engineType = null;
//        if (purePLTHeader.getRrAnalysisId() != null) {
//            RRAnalysis rrAnalysis = rrAnalysisRepository.findOne(purePLTHeader.getRrAnalysisId());
//            if (rrAnalysis != null) {
//                if ("ALM".equals(rrAnalysis.getModel()))
//                    engineType = "AGG";
//                else if ("DLM".equals(rrAnalysis.getModel()))
//                    engineType = "DET";
//            }
//        }
//
//        TargetRap targetRap = daoService.findTargetRapBy(targetRapId);
//        boolean hasDefaultAdjustment = false;
//
//        if (targetRap == null || ! BooleanUtils.isTrue(targetRap.getScorDefault())) {
//            log.info("targetRapId {} targetRapCode {} is not a Scor Default", targetRap.getId(), targetRap.getTargetRapCode());
//        } else {
//
//            List<DefaultAdjustmentStructure> defaultAdjustmentStructures = daoService.findDefaultAdjustmentStructureBy(targetRapId, engineType);
//            if (defaultAdjustmentStructures == null || defaultAdjustmentStructures.isEmpty()) {
//                log.info("No default adjustment structure found for project id {} targetRapId {} targetRapCode {}", projectId, targetRap.getId(), targetRap.getTargetRapCode());
//            }
//
//            List<TargetRapDefaultModellingOption> defaultModellingOptions = daoService.findTargetRapDefaultModellingOptionBy(targetRapId);
//            if (defaultModellingOptions == null || defaultModellingOptions.isEmpty()) {
//                log.info("No default modeling options found for target RAP {} - nothing to do", targetRapId);
//            }
//
//            log.info("Found {} default modeling options found for target RAP {}", defaultModellingOptions.size(), targetRapId);
//
//            Map<String, TargetRapDefaultModellingOption> mapDMOCode = new HashMap<>(defaultModellingOptions.size());
//            for (TargetRapDefaultModellingOption dmo : defaultModellingOptions) {
//                mapDMOCode.put(dmo.getModellingOptionCode(), dmo);
//            }
//
//
//            if (defaultAdjustmentStructures != null && !defaultAdjustmentStructures.isEmpty()) {
//                RegionPeril rp = purePLTHeader.getRegionPeril();
//                log.debug("PLT region peril code {}", rp.getRegionPerilCode());
//
//                for (int i = 0; i < defaultAdjustmentStructures.size(); i++) {
//                    DefaultAdjustmentStructure defaultAdjustmentStructure = defaultAdjustmentStructures.get(i);
//
//                    List<String> includedRegionPerils = null;
//                    List<String> excludedRegionPerils = null;
//
////                IncludedExcludedRegionPeril includedExcludedRegionPeril = daoService.findIncludedExcludedRegionPerilBy(defaultAdjustmentStructure.getAdjStructureId());
////
////                if (includedExcludedRegionPeril != null) {
////                    includedRegionPerils = includedExcludedRegionPeril.getIncludedRPs();
////                    excludedRegionPerils = includedExcludedRegionPeril.getExcludedRPs();
////                }
//
//                    includedRegionPerils = defaultAdjustmentStructure.getIncludedRPs();
//                    excludedRegionPerils = defaultAdjustmentStructure.getExcludedRPs();
//
//                    log.debug("Default adjustment structure {}: includedRegionPerils {} excludedRegionPerils {}", defaultAdjustmentStructure.getAdjStructureId(), includedRegionPerils, excludedRegionPerils);
//
//                    if (excludedRegionPerils != null && !excludedRegionPerils.isEmpty()) {
//                        if (excludedRegionPerils.contains(rp.getRegionPerilCode())) {
//                            log.info("Region Peril for target rap id {} found in excluded region peril list - do not apply default adjustment {}", targetRapId, defaultAdjustmentStructure.getAdjStructureId());
//                            defaultAdjustmentStructures.remove(i);
//                            i--;
//                        }
//                    } else if (includedRegionPerils != null && !includedRegionPerils.isEmpty()) {
//                        if (!includedRegionPerils.contains(rp.getRegionPerilCode())) {
//                            log.info("Region Peril for target rap id {} not found in included region peril list - do not apply default adjustment {}", targetRapId, defaultAdjustmentStructure.getAdjStructureId());
//                            defaultAdjustmentStructures.remove(i);
//                            i--;
//                        }
//                    }
//                }
//            }
//
//
//            Comparator<DefaultAdjustmentStructure> cmp = new Comparator<DefaultAdjustmentStructure>() {
//                @Override
//                public int compare(DefaultAdjustmentStructure o1, DefaultAdjustmentStructure o2) {
//                    return o1.getSequence() - o2.getSequence();
//                }
//            };
//
//            //sort default adjustment structure by sequence
//            Collections.sort(defaultAdjustmentStructures, cmp);
//            for (DefaultAdjustmentStructure defaultAdjustmentStructure : defaultAdjustmentStructures) {
//                TargetRapDefaultModellingOption dmo = mapDMOCode.get(defaultAdjustmentStructure.getDefaultModellingOption());
//                if (dmo == null) {
//                    log.info("No default modeling options found for defaultAdjustmentStructure id {} - nothing to do", defaultAdjustmentStructure.getId());
//                    continue;
//                }
//
//                //check for modeling option setting
//                if (!ExpectedState.get(true).equals(dmo.getExpectedState())) {
//                    log.info("No enabled default modeling options found for defaultAdjustmentStructure id {} - nothing to do", defaultAdjustmentStructure.getId());
//                    continue;
//                }
//
//                AdjustmentStructure defaultStructure;
//                List<AdjustmentNode> entailedNodes = new ArrayList<>();
//                defaultStructure = createAdjustmentStructure(defaultAdjustmentStructure, targetRap);
//                defaultStructure.setScorPLTHeader(purePLTHeader);
//                defaultStructure.setRegionPeril(purePLTHeader.getRegionPeril());
//                defaultStructure.setProject(project);
//                daoService.persistAdjustmentStructure(defaultStructure);
//                List<DefaultAdjustmentNode> defaultAdjustmentNodesNodes = daoService.findDefaultAdjustmentNodeBy(defaultAdjustmentStructure.getAdjStructureId());
//                if (defaultAdjustmentNodesNodes == null || defaultAdjustmentNodesNodes.isEmpty()) {
//                    log.error("No default adjustment nodes for default adjustment structure {}", defaultAdjustmentStructure.getId());
//                }
//
//                log.info("Found {} default adjustment nodes for default adjustment structure {}", defaultAdjustmentNodesNodes.size(), defaultAdjustmentStructure.getId());
//                List<AdjustmentNode> nodes = createAdjustmentNodes(defaultAdjustmentNodesNodes, defaultStructure);
//                entailNodes(entailedNodes, nodes);
//
//
//                if (entailedNodes.size() == 0) {
//                    log.info("Default adjustment empty for targetRap {}, 0 node default nodes found", targetRapId);
//                    continue;
//                }
//
//                hasDefaultAdjustment = true;
//
//                log.info("Entailing all retrieved nodes from different enabled DMOs. Entailed nodes: {} nodes", entailedNodes.size());
//                List<AdjustmentNode> emptyTreeNodes = makeEmptyTree(AdjustementNodeMode.User);
//                List<AdjustmentNode> treeNodes = injectDefaultAdjustmentNodes(emptyTreeNodes, entailedNodes);
//
//                log.info("Making full adjustment tree, empty branch and structure");
//                List<AdjustmentNode> emptyBranchNodes = makeEmptyBranch(AdjustementNodeMode.User);
//                List<AdjustmentNode> fullTreeNodes = injectEmptyBranch(treeNodes, emptyBranchNodes);
//                log.info("emptyTreeNodes {} nodes, treeNodes {} nodes, emptyBranchNodes {} nodes, fullTreeNodes {} nodes", emptyTreeNodes.size(), treeNodes.size(), emptyBranchNodes.size(), fullTreeNodes.size());
//
//
//                log.info("Assigning PLTHeader {} and start calculation", pltHeaderId);
//
//                updateCommons(fullTreeNodes, defaultStructure, purePLTHeader);
//                persistAdjustmentNodes(fullTreeNodes);
//
//                log.info("Calculating... for default structure {}, PLT {}", defaultStructure.getId(), purePLTHeader.getId());
//                requestCalculate(defaultStructure.getId());
//            }
//        }
//
//
//
//        if (!hasDefaultAdjustment) {
//            AdjustmentStructure defaultStructure = new AdjustmentStructure();
//            defaultStructure.setJsonStructure(null); // TODO - can be changed
//            defaultStructure.setRegionPeril(purePLTHeader.getRegionPeril());
//            defaultStructure.setProject(project);
//            defaultStructure.setStructureMode(AdjustementStructureMode.UserAdjustement);
//            defaultStructure.setScorPLTHeader(purePLTHeader);
//            defaultStructure.setSequence(null);
//            defaultStructure.setTargetRap(null);
//            defaultStructure.setTargetRapDefaultModellingOption(null);
//            daoService.persistAdjustmentStructure(defaultStructure);
//            log.info("Making new adjustment structure {}", defaultStructure.getId());
//
//            List<AdjustmentNode> emptyTreeNodes = makeEmptyTree(AdjustementNodeMode.User);
////            log.info("Making full adjustment tree, empty branch and structure");
////            List<AdjustmentNode> emptyBranchNodes = makeEmptyBranch(AdjustementNodeMode.User);
////            List<AdjustmentNode> fullTreeNodes = injectEmptyBranch(emptyTreeNodes, emptyBranchNodes);
//            log.info("emptyTreeNodes {} nodes, treeNodes {} nodes", emptyTreeNodes.size(), emptyTreeNodes.size());
//
//            log.info("Assigning PLTHeader {} and start calculation", pltHeaderId);
//            updateCommons(emptyTreeNodes, defaultStructure, purePLTHeader);
//            persistAdjustmentNodes(emptyTreeNodes);
//
//            log.info("No default adjustments for targetRap {}, created {} nodes", targetRapId, emptyTreeNodes.size());
//
//            log.info("Calculating... for  structure {}, PLT {}", defaultStructure.getId(), purePLTHeader.getId());
//            // call "calculate" go to BaseAdjustmentProcessor
//            requestCalculate(defaultStructure.getId());
//        }
//
//
//
//        return hasDefaultAdjustment;
//    }
//
//
//    private AdjustmentStructure createAdjustmentStructure(DefaultAdjustmentStructure defaultAdjustmentStructure, TargetRap targetRap) {
//        AdjustmentStructure newStructure = new AdjustmentStructure();
//        newStructure.setSequence(defaultAdjustmentStructure.getSequence());
//        newStructure.setStructureMode(AdjustementStructureMode.DefaultAdjustement);
//        newStructure.setTargetRap(targetRap);
//        return newStructure;
//    }
//
//    //// TODO: need to set in config
//    private static String DEFAULT_PEAT_PATH = "/scor/data/ihub/peat";
//
//    private List<AdjustmentNode> createAdjustmentNodes(List<DefaultAdjustmentNode> defaultAdjustmentNodes, AdjustmentStructure structure) {
//        List<AdjustmentNode> nodes = new ArrayList<>(defaultAdjustmentNodes.size());
//        for (DefaultAdjustmentNode defaultNode : defaultAdjustmentNodes) {
//            //create adjustment node from default adjustment node
//            AdjustmentNode node = new AdjustmentNode();
//            daoService.getMongoDBSequence().nextSequenceId(node);
//            node.setStatus(AdjustmentNodeStatus.Invalid);
//            node.setAdjustmentParamsFile(null);
//            node.setId(node.getIdPrefix() + "_" + defaultNode.getDefaultAdjNodeId());
//            node.setAdjustmentCategory(AdjustmentCategory.DEFAULT);
//            node.setAdjustementNodeMode(AdjustementNodeMode.DefaultAdjustement);
//            AdjustmentLibrary adjustmentLibrary = daoService.findAdjustmentLibraryBy(defaultNode.getDefaultAdjustmentTypeId());
//            if (adjustmentLibrary == null) {
//                log.error("No adjustment library found for default adjustment node {} default adjustment type {}", defaultNode.getId(), defaultNode.getDefaultAdjustmentType());
//                continue;
//            }
//            node.setAdjustmentLibrary(adjustmentLibrary);
//            node.setAdjustmentStructure(structure);
//            node.setNarrative(defaultNode.getDefaultAdjNarrative());
//            if (defaultNode.getDefaultAdjParaValuesList() != null && !defaultNode.getDefaultAdjParaValuesList().isEmpty()) {
//                List<Map<String, Double>> defaultAdjParaValuesList = defaultNode.getDefaultAdjParaValuesList();
//                if (adjustmentLibrary.getNonLinAdjRP()) {
//                    // RP adjustments
//                    SortedMap<Double, Double> profileParams = new TreeMap<>();
//                    for (Map<String, Double> param : defaultAdjParaValuesList) {
//                        Double rp = param.get("RP");
//                        Double lmf = param.get("LMF");
//                        profileParams.put(rp, lmf);
//                    }
//                    node.setProfileParams(profileParams);
//                } else {
//                    // Linear adjustment
//                    Map<String, Double> param = defaultAdjParaValuesList.get(0);
//                    node.setAdjustmentParam(param.get("LMF"));
//                }
//            }
//            else {
//                node.setAdjustmentParam(null);
//            }
//            switch (defaultNode.getDefaultAdjParamBasis()) {
//                case "User Entered":
//                    node.setAdjustmentParamsSource(AdjustmentParamsSource.USER_INPUT);
//                    break;
//                case "Reference Data":
//                    node.setAdjustmentParamsSource(AdjustmentParamsSource.EXTERNAL_FILE);
//                    break;
//                default:
//                    node.setAdjustmentParamsSource(null);
//            }
//            if (defaultNode.getDefaultAdjParamPath() != null && !defaultNode.getDefaultAdjParamPath().isEmpty()) {
//                String path = peatPath != null  && !peatPath.isEmpty() ? peatPath : DEFAULT_PEAT_PATH;
//                node.setAdjustmentParamsFile(new BinFile(defaultNode.getDefaultAdjParamPath() + ".csv", path, null));
//            } else {
//                node.setAdjustmentParamsFile(null);
//            }
//
//            if (defaultNode.getDefaultAdjParamMapping() != null && !defaultNode.getDefaultAdjParamMapping().isEmpty()) {
//                Map<String, Integer> paramsMapping = new HashMap<>();
//
//                for (Map.Entry<String, String> entry : defaultNode.getDefaultAdjParamMapping().entrySet()) {
//                    //need to change later, using schema
//                    switch (entry.getKey()) {
//                        case "EventPeriod":
//                            if (entry.getValue() != null && !entry.getValue().isEmpty())
//                                paramsMapping.put(Node.KEY_ADJUSTMENT_PEATHEADERS_PERIOD, 0);
//                            break;
//                        case "EventID":
//                            if (entry.getValue() != null && !entry.getValue().isEmpty())
//                                paramsMapping.put(Node.KEY_ADJUSTMENT_PEATHEADERS_EVID, 1);
//                            break;
//                        case "RepSeq":
//                            if (entry.getValue() != null && !entry.getValue().isEmpty())
//                                paramsMapping.put(Node.KEY_ADJUSTMENT_PEATHEADERS_SEQ, 2);
//                            break;
//                        case "LMF":
//                            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
//                                List<String> pairs = Arrays.asList(entry.getValue().split("#"));
//                                int idx = Integer.parseInt(pairs.get(1).replaceAll("\\D+", ""));
//                                //from 1-base to 0-base index
//                                paramsMapping.put(Node.KEY_ADJUSTMENT_PEATHEADERS_LMF, idx - 1);
//                            }
//                            break;
//                        default:
//                            log.error("undefined PEAT param mapping: {} => {}", entry.getKey(), entry.getValue());
//                            continue;
//                    }
//
//                }
//
//                if (!paramsMapping.isEmpty()) {
//                    //need confirm: PEAT ref data has a header or not
//                    paramsMapping.put(Node.KEY_ADJUSTMENT_PEATHEADERS_TOPROW, 0);
//                    node.setAdjustmentParamsMapping(paramsMapping);
//                } else {
//                    node.setAdjustmentParamsMapping(null);
//                }
//
//            } else {
//                node.setAdjustmentParamsMapping(null);
//            }
//
//
//            node.setAdjustedPLT(null);
//            node.setSourcePLT(null);
//            node.setAdjustmentType(adjustmentLibrary.getTypeName());
//            node.setNodeDescription(defaultNode.getDefaultAdjNarrative());
//            node.setNodeName("Node " + node.getId());
//            AdjustmentNode parentNode = daoService.findAdjustmentNode(node.getIdPrefix() + defaultNode.getDefaultParentNodeId());
//            node.setParentNode(parentNode);
//            AdjustmentBasis basis = daoService.findDefaultAdjustmentBasisBy(defaultNode.getDefaultAdjBasis());
//            //FIXME: need to replace com.scor.almf.treaty.cdm.domain.adjustment.meta.AdjustmentBasis by com.scor.almf.treaty.cdm.domain.adjustment.AdjustmentBasis
//            if (basis != null)
//                node.setAdjustmentBasis(basis);
//            node.setChildrenNodes(null);
//            nodes.add(node);
//        }
//        return nodes;
//    }
//
//    private void persistAdjustmentNodes(List<AdjustmentNode> nodes) {
//        AdjustmentNode pureNode = AdjustmentUtils.findTopNode(nodes);
//        int threadIdx = pureNode.getThreadCounter();
//        threadIdx = indexThreadNode(pureNode, threadIdx);
//        pureNode.setThreadCounter(threadIdx);
//        daoService.persistAdjustmentNodes(nodes);
//    }
//
//    private int indexThreadNode(AdjustmentNode node, int index) {
//        if (AdjustmentUtils.isThreadNode(node)) {
//            if (node.getThreadId() != null) {
//                ScorPLTHeader threadPLT = node.getAdjustedPLT();
//                if (threadPLT != null) {
//                    threadPLT = daoService.findScorPLTHeaderById(threadPLT.getId());
//                    threadPLT.setThreadName("T" + node.getThreadId());
////                    if (node.getUserDefinedThreadName() != null)
////                        threadPLT.setUdName(node.getUserDefinedThreadName());
//                    daoService.persistScorPLTHeader(threadPLT);
//                }
//                return index;
//            }
//            log.info("Thread node {} has index {}", node.getId(), index);
//            node.setThreadId(index);
//            ScorPLTHeader threadPLT = node.getAdjustedPLT();
//            if (threadPLT != null) {
//                threadPLT = daoService.findScorPLTHeaderById(threadPLT.getId());
//                threadPLT.setThreadName("T" + node.getThreadId());
////                if (node.getUserDefinedThreadName() != null)
////                    threadPLT.setUdName(node.getUserDefinedThreadName());
//                daoService.persistScorPLTHeader(threadPLT);
//            }
//            index++;
//            return index;
//        }
//        if (node.getChildrenNodes() != null && !node.getChildrenNodes().isEmpty()) {
//            for (AdjustmentNode child : node.getChildrenNodes()) {
//                index = indexThreadNode(child, index);
//            }
//        }
//        return  index;
//    }
//
//    private int indexThreadNodeFullTree(AdjustmentNode node, int index) {
//        if (AdjustmentUtils.isThreadNode(node)) {
//            if (node.getThreadId() != null) {
//                ScorPLTHeader threadPLT = node.getAdjustedPLT();
//                if (threadPLT != null) {
//                    threadPLT = daoService.findScorPLTHeaderById(threadPLT.getId());
//                    threadPLT.setThreadName("T" + node.getThreadId());
////                    if (node.getUserDefinedThreadName() != null)
////                        threadPLT.setUdName(node.getUserDefinedThreadName());
//                    daoService.persistScorPLTHeader(threadPLT);
//                }
//                return index;
//            }
//            log.info("Thread node {} has index {}", node.getId(), index);
//            node.setThreadId(index);
//            ScorPLTHeader threadPLT = node.getAdjustedPLT();
//            if (threadPLT != null) {
//                threadPLT = daoService.findScorPLTHeaderById(threadPLT.getId());
//                threadPLT.setThreadName("T" + node.getThreadId());
////                if (node.getUserDefinedThreadName() != null)
////                    threadPLT.setUdName(node.getUserDefinedThreadName());
//                daoService.persistScorPLTHeader(threadPLT);
//            }
//            index++;
//            return index;
//        }
//        if (node.getChildrenNodes() != null && !node.getChildrenNodes().isEmpty()) {
//            for (AdjustmentNode child : node.getChildrenNodes()) {
//                index = indexThreadNodeFullTree(child, index);
//            }
//        }
//        return  index;
//    }
//
//    @Override
//    public List<Node> parseTree(String structureId) {
//        List<AdjustmentNode> adjustmentNodes = daoService.findAdjustmentNodeByStructureId(structureId);
//        AdjustmentNode topNode = null;
//        for (AdjustmentNode adjustmentNode : adjustmentNodes) {
//            if (adjustmentNode.getParentNode() == null) {
//                topNode = adjustmentNode;
//                break;
//            }
//        }
//        if (topNode == null) {
//            return null;
//        }
//        List<Node> nodes = convertToTree(topNode, 0);
//        return nodes;
//    }
//
//    private List<AdjustmentNode> parseParentNode(AdjustmentNode node) {
//        List<AdjustmentNode> pendingNodes = new ArrayList<>();
//        AdjustmentNode parent = node.getParentNode();
//        if (parent == null)
//            return null;
//        parent = daoService.findAdjustmentNode(parent.getId());
//        if (AdjustmentNodeStatus.Valid.equals(parent.getStatus()))
//            return null;
//        parent.setStatus(AdjustmentNodeStatus.Pending);
//        pendingNodes.add(parent);
//        List<AdjustmentNode> changingStatusParent = parseParentNode(parent);
//        if (changingStatusParent != null)
//            pendingNodes.addAll(changingStatusParent);
//        return pendingNodes;
//    }
//
//    @Override
//    public void preparePendingNodes(String structureId, List<String> nodeIds) {
//        if (nodeIds == null || nodeIds.isEmpty()) {
//            List<AdjustmentNode> adjustmentNodes = daoService.findAdjustmentNodeByStructureId(structureId);
//            for (AdjustmentNode node : adjustmentNodes) {
//                if (!AdjustmentNodeStatus.Valid.equals(node.getStatus())) {
//                    node.setStatus(AdjustmentNodeStatus.Pending);
//                }
//            }
//            daoService.persistAdjustmentNodes(adjustmentNodes);
//        } else {
//            List<AdjustmentNode> pendingNodes = new ArrayList<>();
//            for (String id : nodeIds) {
//                AdjustmentNode node = daoService.findAdjustmentNode(id);
//                node.setStatus(AdjustmentNodeStatus.Pending);
//                pendingNodes.add(node);
//                List<AdjustmentNode> changingStatusParent = parseParentNode(node);
//                if (changingStatusParent != null)
//                    pendingNodes.addAll(changingStatusParent);
//            }
//            daoService.persistAdjustmentNodes(pendingNodes);
//        }
//    }
//
//    private AdjustmentStructure updateAdjustmentStructure(String jsonStructure) throws MalformedJsonException {
//        JsonParser parser = new JsonParser();
//        JsonObject jObject = parser.parse(jsonStructure).getAsJsonObject();
//        JsonObject jContextObject = null;
//        JsonArray jCloneArray = null;
//        if (jObject.has("context")) {
//            jContextObject = parser.parse(jsonStructure).getAsJsonObject().getAsJsonObject("context");
//        }
//        if (jContextObject == null) {
//            throw new MalformedJsonException("Malformed adjustment structure");
//        }
//
//        // Find and update
//        Context context = new Gson().fromJson(jContextObject, typeContext);
//        String structureId = context.getStructureId();
//        AdjustmentStructure outStructure = daoService.findAdjustmentStructureById(structureId);
//        ScorPLTHeader pure = daoService.findScorPLTHeaderById(outStructure.getScorPLTHeader().getId());
//
//        purePLTLossFile = pure.getPltLossDataFile();
//
//        log.info("Adjustment Structure exists: id = {}", structureId);
//        BinFile jsonFile = jSONWriter.write(outStructure.getId() + ".json", jObject);
//        outStructure.setJsonStructure(jsonFile);
//        daoService.persistAdjustmentStructure(outStructure);
//        return outStructure;
//    }
//
//    private List<AdjustmentNode> updateAdjustmentNodes(AdjustmentStructure adjustmentStructure) throws MalformedJsonException {
//        BinFile jsonFile = adjustmentStructure.getJsonStructure();
//        JsonObject jsonObject = jSONReader.readJson(jsonFile);
//        JsonArray jNodeArray = null;
//        JsonArray jCloneArray = null;
//        if (jsonObject.has("nodes")) {
//            jNodeArray = jsonObject.getAsJsonArray("nodes");
//        }
//        if (jsonObject.has("clones")) {
//            JsonElement jelem = jsonObject.getAsJsonArray("clones");
//            if (!(jelem instanceof JsonNull)) {
//                jCloneArray = (JsonArray) jelem;
//            }
//        }
//        if (jNodeArray == null) {
//            throw new MalformedJsonException("Malformed adjustment structure");
//        }
//
//        final List<Node> nodes = new ArrayList<Node>();
//        final Map<String, String> cloneMap = new HashMap<>();
//        for (JsonElement je : jNodeArray) {
//            Node node = new Gson().fromJson(je, typeNode);
//            nodes.add(node);
//        }
//
//        List<AdjustmentNode> generatedNodes = new ArrayList<>();
//        generateTree(null, null, nodes, generatedNodes, adjustmentStructure); // null denotes starting generation process from top node
//
//        if (jCloneArray != null) {
//            for (JsonElement je : jCloneArray) {
//                CloneDuo duo = new Gson().fromJson(je, typeCloneDuo);
//                cloneMap.put(duo.cloningId, duo.clonedId);
//            }
//        }
//
//        List<AdjustmentNode> persistedNodes = daoService.findAdjustmentNodeByStructureId(adjustmentStructure.getId());
//        if (persistedNodes != null && persistedNodes.size() > 0) {
//            mergeHierarchy(persistedNodes, generatedNodes, cloneMap);
//            log.info("mergeHierarchy: {} persistedNode, {} generatedNodes, {} cloneNodes", persistedNodes.size(), generatedNodes.size(), cloneMap.size());
//        }
//
//        AdjustmentNode pureNode = AdjustmentUtils.findTopNode(generatedNodes);
//        int threadIdx = pureNode.getThreadCounter();
//        threadIdx = indexThreadNodeFullTree(pureNode, threadIdx);
//        pureNode.setThreadCounter(threadIdx);
//
//        //for Plt Naming
//        for (AdjustmentNode adjustmentNode : generatedNodes) {
//            if (AdjustmentUtils.isThreadNode(adjustmentNode)) {
//                String pltName = null;
//                String threadName = null;
//
//                PLTNaming pltNaming = buildDefaultPltName(adjustmentNode);
//                pltName = pltNaming.pltName;
//
//                String threadId = ".T" + adjustmentNode.getThreadId();
//                String pltNameSuffix = adjustmentNode.getPltNameSuffix();
//                if (pltNameSuffix != null && !("").equals(pltNameSuffix.trim())) {
//                    pltNameSuffix = "." + pltNameSuffix.trim();
//                } else {
//                    pltNameSuffix = null;
//                }
//
//                if (BooleanUtils.isTrue(adjustmentNode.getGeneratedBySystemPltNameLAC())) {
//                    threadName = pltNaming.threadName;
//
//                    adjustmentNode.setDefaultPltName(pltName);
//                    adjustmentNode.setPltName(pltName);
//                    adjustmentNode.setUserDefinedThreadName(threadName);
//                } else {
//                    threadName = adjustmentNode.getPltName() + Objects.toString(pltNameSuffix, "") + threadId;
//
//                    adjustmentNode.setDefaultPltName(pltName);
//                    adjustmentNode.setUserDefinedThreadName(threadName);
//                }
//
//                if (AdjustmentNodeStatus.Valid.equals(adjustmentNode.getStatus())) {
//                    ScorPLTHeader threadPLT = adjustmentNode.getAdjustedPLT();
//                    if (threadPLT != null) {
//                        threadPLT = daoService.findScorPLTHeaderById(threadPLT.getId());
//                        threadPLT.setDefaultPltName(adjustmentNode.getDefaultPltName());
//                        threadPLT.setPltName(adjustmentNode.getPltName());
//                        threadPLT.setUdName(adjustmentNode.getUserDefinedThreadName());
//                        daoService.persistScorPLTHeader(threadPLT);
//                    }
//                }
//
//                AdjustmentNode parentNode = adjustmentNode.getParentNode();
//                while (parentNode != null && !AdjustmentUtils.isPureNode(parentNode)) {
//                    parentNode.setAdjustementNodeMode(adjustmentNode.getAdjustementNodeMode());
//                    parentNode = parentNode.getParentNode();
//                }
//            }
//        }
//
//        // Persistence of existing and new nodes
////        persistAdjustmentNodes(generatedNodes);
//        daoService.persistAdjustmentNodes(generatedNodes);
//
//        // Deletion of obsolete nodes
//        if (persistedNodes != null && persistedNodes.size() != 0) {
//            Collection<AdjustmentNode> deletedNodes = findDeletedNodes(persistedNodes, generatedNodes);
//            if (deletedNodes == null) {
//                log.info("no nodes to be deleted");
//            } else {
//                log.info("to delete {} nodes", deletedNodes.size());
//            }
//            deleteNodes(deletedNodes);
//        }
//        return generatedNodes;
//    }
//
//    private void deleteNodes(Collection<AdjustmentNode> nodes) {
//        if (nodes == null) return;
////        Set<PLTEPHeader> deletingPLTEPHeaders = new HashSet<>();
//        Set<RRStatisticHeader> deletingRRStatisticHeaders = new HashSet<>();
//        Set<ScorPLTHeader> deletingScorPLTHeader = new HashSet<>();
//        for (AdjustmentNode node : nodes) {
//            // null nodes use shared PLTs of non-null nodes, so that no deletion related to null node
//            if (!AdjustmentUtils.isNullNode(node)) {
//                ScorPLTHeader pltHeader = node.getAdjustedPLT();
//                if (pltHeader == null) {
//                    continue;
//                }
//                pltHeader = scorPLTHeaderRepository.findOne(pltHeader.getId());
//                deletingScorPLTHeader.add(pltHeader);
////                List<PLTEPHeader> pltepHeaders = pltHeader.getPLTEPHeaders();
//                List<RRStatisticHeader> rrStatisticHeaders = pltHeader.getPltStatisticList();
//
////                if (pltepHeaders != null) {
////                    deletingPLTEPHeaders.addAll(pltepHeaders);
////                }
//
//                if (rrStatisticHeaders != null) {
//                    deletingRRStatisticHeaders.addAll(rrStatisticHeaders);
//                }
//            }
//        }
//        for (AdjustmentNode node : nodes) {
//            log.info("Deleting node {}", node.getId());
//        }
//        for (ScorPLTHeader pltHeader : deletingScorPLTHeader) {
//            log.info("Deleting pltHeader {}", pltHeader.getId());
//            deleteOldAdjustedPLTBin(pltHeader);
//        }
////        for (PLTEPHeader pltepHeader : deletingPLTEPHeaders) {
//        for (RRStatisticHeader rrStatisticHeader : deletingRRStatisticHeaders) {
//            log.info("Deleting rrStatisticHeader {}", rrStatisticHeader.getId());
//        }
//        adjustmentNodeRepository.delete(nodes);
////        pltepHeaderRepository.delete(deletingPLTEPHeaders);
//        rrStatisticHeaderRepository.delete(deletingRRStatisticHeaders);
//        scorPLTHeaderRepository.delete(deletingScorPLTHeader);
//    }
//
//    private void entailNodes(List<AdjustmentNode> parents, List<AdjustmentNode> children) {
//        log.info("{} entailed nodes (parents), {} entailing nodes (children)", parents.size(), children.size());
//        AdjustmentNode tail = AdjustmentUtils.findBottomNode(parents);
//        AdjustmentNode head = AdjustmentUtils.findTopNode(children);
//        if (tail != null && head != null) {
//            tail.addChildrenNode(head);
//            head.setParentNode(tail);
//            log.info("Entailing head node {}-{}-{} to tail node {}-{}-{}", head.getId(), head.getId(), head.getAdjustmentType(), tail.getId(), tail.getId(), tail.getAdjustmentType());
//        }
//        parents.addAll(children);
//        log.info("Entailing {} children nodes to {} parents", children.size(), parents.size());
//    }
//
//*
//     * We are creating new nodes from default adjustment nodes. Renewal is mandatory
//     * @param nodes
//
//
//    private void renewDefaultNodes(List<AdjustmentNode> nodes) {
//        for (AdjustmentNode node : nodes) {
//            node.setId(null);
//            //node.setAdjustementNodeMode(AdjustementNodeMode.User);
//        }
//    }
//
//    private void updateCommons(List<AdjustmentNode> adjustmentNodes, AdjustmentStructure structure, ScorPLTHeader purePLTHeader) {
//        AdjustmentNode pureNode = AdjustmentUtils.findTopNode(adjustmentNodes);
//        pureNode.setSourcePLT(purePLTHeader);
//        pureNode.setAdjustedPLT(purePLTHeader);
//        AdjustmentNodeStatus status = AdjustmentUtils.checkStatus(pureNode, daoService);
//        pureNode.setStatus(status);
//
//        for (AdjustmentNode adjustmentNode : adjustmentNodes) {
//            adjustmentNode.setAdjustmentStructure(structure);
//            if (!adjustmentNode.equals(pureNode)) {
//                adjustmentNode.setPureNode(pureNode);
//            }
//        }
//        purePLTHeader.setAdjustmentStructure(structure);
//        // TODO Tien
//        daoService.persistScorPLTHeader(purePLTHeader);
//    }
//
//*
//     * Generate an empty tree with pure, null nodes and final nodes
//     * @param adjustementNodeMode
//     * @return
//
//
//    private List<AdjustmentNode> makeEmptyTree(AdjustementNodeMode adjustementNodeMode) {
//        List<AdjustmentNode> adjustmentNodes = new ArrayList<>();
//        AdjustmentNode parentNode = null;
//        AdjustmentNode pureNode = null;
//
//        for (int zone = 0; zone < 6; zone++) {
//            AdjustmentNode node = new AdjustmentNode();
//            daoService.getMongoDBSequence().nextSequenceId(node);
//            node.setAdjustedPLT(null); // Do not touch me
//            node.setAdjustementNodeMode(adjustementNodeMode);
//            node.setAdjustmentBasis(null);
//            node.setAdjustmentCategory(Node.getAdjustmentCategory(zone));
//            node.setAdjustmentLibrary(null);
//            node.setAdjustmentParam(null);
//            node.setAdjustmentParamsFile(null);
//            node.setAdjustmentParamsSource(AdjustmentParamsSource.USER_INPUT);
//            node.setAdjustmentParamsMapping(null);
//            node.setAdjustmentStructure(null);
//            node.setAdjustmentType(AdjustmentType.NONE);
//            node.setNarrative(null);
//            node.setNodeDescription(null);
//            node.setNodeName("Node " + node.getId());
//            node.setParentNode(parentNode);
//            node.setProfileParams(null);
//            node.setPureNode(pureNode);
//            node.setSourcePLT(null); // Do not touch me
//            node.setStatus(AdjustmentNodeStatus.Invalid);
//            node.setAdjustmentObjectParams(null);
//            node.setGeneratedBySystemPltNameLAC(true);
//            adjustmentNodes.add(node);
//            if (parentNode != null) {
//                parentNode.addChildrenNode(node);
//            }
//            if (zone == 0) {
//                pureNode = node;
//                pureNode.setThreadCounter(1);
//            }
//            parentNode = node;
//        }
//        return adjustmentNodes;
//    }
//
//*
//     * Empty branch to be added to the right of default adjustment thread
//     * @param adjustementNodeMode
//     * @return
//
//
//    private List<AdjustmentNode> makeEmptyBranch(AdjustementNodeMode adjustementNodeMode) {
//        // AdjustmentLibrary adjustmentLibraryNone = daoService.findAdjustmentLibraryById("0");
//
//        List<AdjustmentNode> adjustmentNodes = new ArrayList<>();
//        AdjustmentNode parentNode = null;
//        for (int zone = 1; zone < 6; zone++) {
//            AdjustmentNode node = new AdjustmentNode();
//            daoService.getMongoDBSequence().nextSequenceId(node);
//            node.setAdjustedPLT(null); // Do not touch me
//            node.setAdjustementNodeMode(adjustementNodeMode);
//            node.setAdjustmentBasis(null);
//            node.setAdjustmentCategory(Node.getAdjustmentCategory(zone));
//            node.setAdjustmentLibrary(null);
//            node.setAdjustmentParam(null);
//            node.setAdjustmentParamsFile(null);
//            node.setAdjustmentParamsSource(AdjustmentParamsSource.USER_INPUT);
//            node.setAdjustmentParamsMapping(null);
//            node.setAdjustmentStructure(null);
//            node.setAdjustmentType(AdjustmentType.NONE);
//            node.setNarrative(null);
//            node.setNodeDescription(null);
//            node.setNodeName("Node " + node.getId());
//            node.setParentNode(parentNode);
//            node.setProfileParams(null);
//            node.setPureNode(null); // to be updated
//            node.setSourcePLT(null); // Do not touch me
//            node.setAdjustmentObjectParams(null);
//            node.setStatus(AdjustmentNodeStatus.Invalid);
//            node.setGeneratedBySystemPltNameLAC(true);
//            adjustmentNodes.add(node);
//            if (parentNode != null) {
//                parentNode.addChildrenNode(node);
//            }
//            parentNode = node;
//
//        }
//        return adjustmentNodes;
//    }
//
//*
//     * Replace empty default node in the empty tree with default nodes
//     * @param emptyTreeNodes
//     * @param defaultNodes
//     * @return
//     *      all nodes of the tree after the replacement
//
//
//    private List<AdjustmentNode> injectDefaultAdjustmentNodes(List<AdjustmentNode> emptyTreeNodes, List<AdjustmentNode> defaultNodes) {
//        log.info("Replacing 1 empty default node with {} default nodes", defaultNodes.size());
//        renewDefaultNodes(defaultNodes);
//
//        AdjustmentNode emptyDefaultNode = AdjustmentUtils.findTopDefaultNode(emptyTreeNodes);
//        AdjustmentNode pureNode = AdjustmentUtils.findTopNode(emptyTreeNodes);
//        AdjustmentNode finalNode = AdjustmentUtils.findBottomNode(emptyTreeNodes);
//
//        AdjustmentNode parentNode = emptyDefaultNode.getParentNode();
//        List<AdjustmentNode> childrenNodes = emptyDefaultNode.getChildrenNodes();
//        if (childrenNodes.size() != 1) {
//            throw new IllegalStateException();
//        }
//        AdjustmentNode childrenNode = childrenNodes.get(0);
//
//        AdjustmentNode topDefaultNode = AdjustmentUtils.findTopNode(defaultNodes);
//        AdjustmentNode bottomDefaultNode = AdjustmentUtils.findBottomNode(defaultNodes);
//
//        parentNode.getChildrenNodes().remove(emptyDefaultNode);
//        parentNode.getChildrenNodes().add(topDefaultNode);
//        childrenNode.setParentNode(bottomDefaultNode);
//
//        topDefaultNode.setParentNode(parentNode);
//
//        bottomDefaultNode.addChildrenNode(childrenNodes);
//
//        for (AdjustmentNode defaultNode : defaultNodes) {
//            defaultNode.setPureNode(pureNode);
//        }
//        emptyTreeNodes.remove(emptyDefaultNode);
//        emptyTreeNodes.addAll(defaultNodes);
//
//        return emptyTreeNodes;
//    }
//
//*
//     * Add an empty branch to the right of a tree
//     *
//     * @param treeNodes
//     * @param emptyBranchNodes
//     * @return
//     *      all nodes of the tree after the addition
//
//
//    private List<AdjustmentNode> injectEmptyBranch(List<AdjustmentNode> treeNodes, List<AdjustmentNode> emptyBranchNodes) {
//        AdjustmentNode pureNode = AdjustmentUtils.findTopNode(treeNodes);
//        AdjustmentNode topEmptyBranchNode = AdjustmentUtils.findTopNode(emptyBranchNodes);
//
//        pureNode.addChildrenNode(topEmptyBranchNode);
//        topEmptyBranchNode.setParentNode(pureNode);
//
//        for (AdjustmentNode emptyBranchNode : emptyBranchNodes) {
//            emptyBranchNode.setPureNode(pureNode);
//        }
//        treeNodes.addAll(emptyBranchNodes);
//
//        return treeNodes;
//    }
//
//    private class ExclusionPredicate implements Predicate<AdjustmentNode> {
//
//        private Set<AdjustmentNode> nodes;
//
//        public ExclusionPredicate(Collection<AdjustmentNode> nodes) {
//            this.nodes = new HashSet<>(nodes);
//        }
//
//        @Override
//        public boolean apply(AdjustmentNode input) {
//            return !this.nodes.contains(input);
//        }
//    }
//
//    private Collection<AdjustmentNode> findDeletedNodes(List<AdjustmentNode> persistedNodes, List<AdjustmentNode> frontendNodes) {
//        if (persistedNodes == null || persistedNodes.size() == 0) {
//            return null;
//        }
//        Collection<AdjustmentNode> deleteds = Collections2.filter(persistedNodes, new ExclusionPredicate(frontendNodes));
//        return deleteds;
//    }
//
//    private String makeCSVFilename(AdjustmentNode adjustmentNode) {
//        String structureId = adjustmentNode.getAdjustmentStructure().getId();
//        String id = adjustmentNode.getId();
//        String name = adjustmentNode.getNodeName();
//        String type = adjustmentNode.getAdjustmentType();
//
//        if (id == null) {
//            id = "temp";
//        }
//
//        String separator = "_";
//        String extension = ".csv";
//        return "".concat(structureId).concat(separator)
//                .concat(id).concat(extension)
//                .concat(name).concat(extension)
//                .concat(type).concat(extension);
//    }
//
//*
//     * Parse data sent from frontend. Build an initial tree with all constituent fields
//     * except AdjustedPLt. For purpose of retaining branches's order. Jsplumb's input data
//     * defines branch order in compliance with the order in the list of children of each
//     * node. All nodes/steps are marked invalid, before comparing with the saved tree on database.
//     *
//     * @param rawNode   Raw node to be processed, initially null to signal the top node
//     * @param rawNodes  List of remaining raw nodes to be parsed
//     * @param generatedNodes
//     *                  List of generated adjustment nodes, initially null to signal the top node
//     * @param structure
//     *                  AdjustmentStructure of all generated nodes
//     * @throws Exception
//
//
//    private void generateTree(Node rawNode, AdjustmentNode parentAdjNode, List<Node> rawNodes, List<AdjustmentNode> generatedNodes, AdjustmentStructure structure) {
//        Boolean isTopNode = false;
//        if (rawNode == null) {
//            isTopNode = rawNode == null && parentAdjNode == null;
//            rawNode = rawNodes.get(0);
//        }
//        rawNodes.remove(rawNode); // remaining raw nodes
//
//        // Initialize an adjustment step for one node
//        AdjustmentNode adjNode = new AdjustmentNode();
//        adjNode.setAdjustmentStructure(structure);
//        adjNode.setId(rawNode.getId());
//        adjNode.setNodeName(rawNode.getName());
//        adjNode.setNodeDescription(null);
//        adjNode.setAdjustmentBasis(rawNode.getBasis());
//        adjNode.setNarrative(rawNode.getAdjustmentNarrative());
//        adjNode.setAdjustmentCategory(AdjustmentCategory.getZone(rawNode.getZone()));
//        adjNode.setStatus(AdjustmentNodeStatus.Invalid);
//        adjNode.setAdjustementNodeMode(AdjustementNodeMode.User);
//        adjNode.setParentNode(parentAdjNode);
//        adjNode.setAdjustmentLibrary(null);
//        adjNode.setAdjustmentParamsMapping(rawNode.getPEATMap());
//        adjNode.setGeneratedBySystemPltNameLAC(rawNode.getGeneratedBySystemPltNameLAC());
//        adjNode.setDefaultPltName(rawNode.getDefaultPltName());
//        adjNode.setPltName(rawNode.getPltName());
//        adjNode.setPltNameSuffix(rawNode.getPltNameSuffix());
//
//        adjNode.setInputChanged(rawNode.getInputChange());
//
//        if (isTopNode) {
//            ScorPLTHeader scorPLTHeader = structure.getScorPLTHeader();
//            adjNode.setPureNode(null);
//            adjNode.setSourcePLT(scorPLTHeader);
//            adjNode.setAdjustedPLT(scorPLTHeader);
//            adjNode.setStatus(AdjustmentUtils.checkStatus(adjNode, daoService));
//        } else {
//            if (parentAdjNode.getParentNode() == null) {
//                adjNode.setPureNode(parentAdjNode);
//            } else {
//                adjNode.setPureNode(parentAdjNode.getPureNode());
//            }
//            adjNode.setSourcePLT(parentAdjNode.getAdjustedPLT());
//            adjNode.setAdjustedPLT(null);
//        }
//
//        String type = rawNode.getAdjustmentType();
//        List<AdjustmentLibrary> adjustmentLibraries = daoService.findAdjustmentTypeByName(type);
//        if (adjustmentLibraries == null || adjustmentLibraries.size() == 0) {
//            throw new IllegalStateException();
//        }
//        adjNode.setAdjustmentLibrary(adjustmentLibraries.get(0));
//        adjNode.setAdjustmentType(type);
//
//        if (StringUtils.equalsIgnoreCase(rawNode.getAdjustmentType(), AdjustmentType.NONE)) {
//            adjNode.setAdjustmentParam(null);
//            adjNode.setAdjustmentParamsFile(null);
//            adjNode.setAdjustmentParamsSource(AdjustmentParamsSource.USER_INPUT);
//        } else if (StringUtils.equalsIgnoreCase(rawNode.getAdjustmentType(), AdjustmentType.LINEAR)
//                || StringUtils.equalsIgnoreCase(rawNode.getAdjustmentType(), AdjustmentType.FREQUENCY_EEF)) {
//            adjNode.setAdjustmentParam(rawNode.getSingleAdjustmentRate());
//            adjNode.setAdjustmentParamsFile(null);
//            adjNode.setAdjustmentParamsSource(AdjustmentParamsSource.USER_INPUT);
//        } else if (StringUtils.equalsIgnoreCase(rawNode.getAdjustmentType(), AdjustmentType.EV_SPEC)) {
//            adjNode.setAdjustmentParam(null);
//            adjNode.setAdjustmentParamsSource(AdjustmentParamsSource.EXTERNAL_FILE);
//
//            boolean isNewPEAT = rawNode.isNewPEAT(); // TODO - recompare peat file
//            adjNode.flagNewParamsFile(isNewPEAT); // Important TODO - consider influence when clone or copying // cost: bigger REST payload
//            if (isNewPEAT) {
//                List<String> peatRows = rawNode.getPEAT();
//                if (peatRows == null) throw new IllegalStateException();
//
//                // we store file name and path in the payload sent to frontend and vice versa
//                BinFile oldFile = rawNode.getAdjustmentParamsFile();
//                if (oldFile == null) {
//                    String filename = makeCSVFilename(adjNode);
//                    String path = tempOutPath + "/csv-files";
//                    oldFile = new BinFile(filename, path, null);
//                }
//
//                cSVWriter.writeCSV(peatRows, oldFile.getPath(), oldFile.getFileName());
//                adjNode.setAdjustmentParamsFile(oldFile);
//            } else { // assign old existing file
//                adjNode.setAdjustmentParamsFile(rawNode.getAdjustmentParamsFile());
//            }
//        } else if (StringUtils.equalsIgnoreCase(rawNode.getAdjustmentType(), AdjustmentType.RP_BANDING_OEP)
//                || StringUtils.equalsIgnoreCase(rawNode.getAdjustmentType(), AdjustmentType.RP_BANDING_EEF)) {
//            adjNode.setAdjustmentParam(null);
//            adjNode.setAdjustmentParamsSource(AdjustmentParamsSource.USER_INPUT);
//            adjNode.setAdjustmentParamsFile(null);
//
//            adjNode.flagNewParamsFile(rawNode.isNewProfile()); // Important TODO - consider influence when clone or copying // cost: bigger REST payload
//            adjNode.setProfileParams(rawNode.getProfile());
//        } else if (StringUtils.equalsIgnoreCase(rawNode.getAdjustmentType(), AdjustmentType.CAT_XL)) {
//            adjNode.setAdjustmentObjectParams(rawNode.getAdjustmentObjectParams());
//            adjNode.setAdjustmentParamsFile(null);
//            adjNode.setAdjustmentParamsSource(AdjustmentParamsSource.USER_INPUT);
//            adjNode.setAdjustmentObjectParams(rawNode.getAdjustmentObjectParams());
//            adjNode.setLossNetFlag(rawNode.getLossNetFlag());
//        } else if (StringUtils.equalsIgnoreCase(rawNode.getAdjustmentType(), AdjustmentType.QUOTA_SHARE)) {
//            adjNode.setAdjustmentParam(null);
//            adjNode.setAdjustmentObjectParams(null);
//            adjNode.setProfileParams(rawNode.getProfile());
//            adjNode.setAdjustmentParamsFile(null);
//            adjNode.setAdjustmentParamsSource(AdjustmentParamsSource.USER_INPUT);
//            adjNode.setLossNetFlag(rawNode.getLossNetFlag());
//        } else {
//            throw new NotImplementedException();
//        }
//
//        if (parentAdjNode != null) {
//            parentAdjNode.addChildrenNode(adjNode);
//        }
//
//        if (rawNode.getUserDefinedThreadName() != null && !rawNode.getUserDefinedThreadName().isEmpty())
//            adjNode.setUserDefinedThreadName(rawNode.getUserDefinedThreadName());
//        else
//            adjNode.setUserDefinedThreadName(null);
//
//        generatedNodes.add(adjNode);
//
//        final String[] childrenNodeIds = rawNode.getChildren();
//        if (childrenNodeIds != null && childrenNodeIds.length > 0) {
//            for (String childNodeId : childrenNodeIds) {
//                Node childNode = null;
//                for (int i = 0; i < rawNodes.size(); ++i) {
//                    if (rawNodes.get(i).getId().equals(childNodeId)) {
//                        childNode = rawNodes.get(i);
//                        break;
//                    }
//                }
//                generateTree(childNode, adjNode, rawNodes, generatedNodes, structure);
//            }
//        } else {
//            adjNode.setChildrenNodes(null);
//        }
//    }
//
//    private AdjustmentNode convertToAdjustmentNode(Node node, AdjustmentNode parentNode, AdjustmentStructure structure) {
//        Boolean isTopNode = false;
//        if (node.getParent() == null) {
//            isTopNode = true;
//        }
//        AdjustmentNode adjNode = new AdjustmentNode();
//        adjNode.setAdjustmentStructure(structure);
//        adjNode.setId(node.getId());
//        adjNode.setNodeName(node.getName());
//        adjNode.setNodeDescription(null);
//        adjNode.setAdjustmentBasis(node.getBasis());
//        adjNode.setAdjustmentCategory(AdjustmentCategory.getZone(node.getZone()));
//        adjNode.setStatus(AdjustmentNodeStatus.Invalid);
//        adjNode.setParentNode(isTopNode ? null : parentNode);
//        adjNode.setAdjustmentLibrary(null);
//        adjNode.setAdjustedPLT(null);
//
//        return adjNode;
//    }
//
//    private void mergeHierarchy(List<AdjustmentNode> backendNodes, List<AdjustmentNode> frontendNodes, Map<String, String> cloneMap) {
//        AdjustmentNode topBackendNode = null;
//        for (AdjustmentNode backendNode : backendNodes) {
//            if (backendNode.isTopNode()) {
//                topBackendNode = backendNode;
//                break;
//            }
//        }
//
//        AdjustmentNode topFrontedNode = null;
//        for (AdjustmentNode frontendNode : frontendNodes) {
//            if (frontendNode.isTopNode()) {
//                topFrontedNode = frontendNode;
//                break;
//            }
//        }
//
//        if (topBackendNode == null || topFrontedNode == null) {
//            throw new IllegalStateException();
//        }
//
//        if (!AdjustmentUtils.isNodeIdentityEqual(topBackendNode, topFrontedNode)) {
//            throw new IllegalStateException();
//        }
//
//        // Ensure PLTs comply with default adjustment spec (source and adjusted are different)
//        topFrontedNode.setSourcePLT(topBackendNode.getSourcePLT());
//        topFrontedNode.setAdjustedPLT(topBackendNode.getAdjustedPLT());
//        AdjustmentNodeStatus status = AdjustmentUtils.checkStatus(topFrontedNode, daoService);
//        topFrontedNode.setStatus(status);
//        topFrontedNode.setThreadCounter(topBackendNode.getThreadCounter());
//        topFrontedNode.setCloningSource(topBackendNode.getCloningSource());
//
//        if (status.equals(AdjustmentNodeStatus.Valid)) {
//            mergeSubHierarchy(topBackendNode, topFrontedNode, cloneMap, false, false);
//        }
//    }
//
//    private void deleteOldAdjustedPLTBin(ScorPLTHeader plt) {
//        final BinFile oldBinFile = plt.getPltLossDataFile();
//        File oldDATFile = new File(oldBinFile.getPath(), oldBinFile.getFileName());
//        oldDATFile.delete();
//        log.info("Deleted file {}", oldDATFile);
//        String oldEPCName = oldBinFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPC + "$2");
//        File oldEPCFile = new File(oldBinFile.getPath(), oldEPCName);
//        oldEPCFile.delete();
//        log.info("Deleted file {}", oldEPCFile);
//        String oldEPSName = oldBinFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPS + "$2");
//        File oldEPSFile = new File(oldBinFile.getPath(), oldEPSName);
//        oldEPSFile.delete();
//        log.info("Deleted file {}", oldEPSFile);
//    }
//
//
//    private AdjustmentNode findMatchedDeletedNode(List<AdjustmentNode> adjustmentNodes, String nodeId) {
//        for (AdjustmentNode adjustmentNode : adjustmentNodes) {
//            List<AdjustmentNode> childrenNodes = adjustmentNode.getChildrenNodes();
//            if (childrenNodes != null && !childrenNodes.isEmpty()) {
//                AdjustmentNode matchedBackendChild = findMatchedNode(childrenNodes, nodeId);
//                if (matchedBackendChild != null)
//                    return adjustmentNode;
//            }
//
//        }
//        return null;
//    }
//
//    private void mergeSubHierarchy(AdjustmentNode backendParentStep, AdjustmentNode frontendParentStep, Map<String, String> cloneMap , boolean backEndNodeDeletedInd, boolean hasdeletedparent ) {
//
//
//        log.debug("------------------------------- DATA ------------------------------");
//        log.debug("BacKend Parent Node: {}", backendParentStep != null ? backendParentStep.getId() : "NULL");
//        log.debug("FrontEnd Parent Node: {}", frontendParentStep != null ? frontendParentStep.getId() : "NULL");
//        log.debug("BackEnd Node Deleted: {}", backEndNodeDeletedInd ? "TRUE":"FALSE");
//        log.debug("Has Deleted Parent: {}", hasdeletedparent ? "TRUE":"FALSE");
//        log.debug("-------------------------------------------------------------------");
//        log.info("mergeSubHierarchy");
//        boolean isParentValid = AdjustmentNodeStatus.Valid.equals(frontendParentStep.getStatus()) && AdjustmentNodeStatus.Valid.equals(backendParentStep.getStatus()) ? true : false;
//        List<AdjustmentNode> frontendChildren = null;
//        if( backEndNodeDeletedInd) {
//            frontendChildren = new ArrayList<>();
//            frontendChildren.add(frontendParentStep);
// if the backendparentStep reprensente a deleted node, then we want to iterate into the backendnodechildren but maintain the same level of frontendnode
//
//        }else {
//            frontendChildren = frontendParentStep.getChildrenNodes();
//        }
//        List<AdjustmentNode> backendChildren = backendParentStep.getChildrenNodes();
//        if (frontendChildren == null || backendChildren == null) {
//            return;
//        }
//        for (AdjustmentNode frontendChild : frontendChildren) {
//            AdjustmentNode matchedBackendChild = findMatchedNode(backendChildren, frontendChild.getId());
//            AdjustmentNode matchedDeletedNode = findMatchedDeletedNode(backendChildren, frontendChild.getId());
//
//            log.debug("TEST 1 :  Matched Backend Child: {}", matchedBackendChild != null ? matchedBackendChild : "NULL");
//
//            if (matchedBackendChild != null) { // is an EXISTING NODE
//
//                log.debug("TEST 2 : Node Identical {}  ### Is Parent Valid {}", AdjustmentUtils.isNodeInputIdentical(matchedBackendChild, frontendChild) ? "YES":"NO", isParentValid ? "YES":"NO");
//                if (AdjustmentUtils.isNodeInputIdentical(matchedBackendChild, frontendChild) && isParentValid) {
//                    // copy PLT data
//                    frontendChild.setId(matchedBackendChild.getId());
//                    frontendChild.setSourcePLT(matchedBackendChild.getSourcePLT());
//                    frontendChild.setAdjustedPLT(matchedBackendChild.getAdjustedPLT());
//                    frontendChild.setxActLocked(matchedBackendChild.getxActLocked());
//                    AdjustmentNodeStatus status = AdjustmentUtils.checkStatus(frontendChild, daoService);
//                    frontendChild.setStatus(status);
//                    frontendChild.setAdjustementNodeMode(matchedBackendChild.getAdjustementNodeMode());
//                    frontendChild.setNonGAMSourceNodeId(matchedBackendChild.getNonGAMSourceNodeId());
//                    if (AdjustmentUtils.isThreadNode(frontendChild)) {
//                        frontendChild.setThreadId(matchedBackendChild.getThreadId());
//                    }
//
//                    log.debug("TEST 3 : Has deleted parent {} ", hasdeletedparent ? "YES":"NO");
//                    if (hasdeletedparent)
//                    {
// because one or more of the node parents have been deleted, the node status needs to be set as invalid
//
//                        log.info("mergeSubHierarchy: Node {} already exists but have a deleted parent, so that it and its children are invalid", frontendChild.getId());
//                        frontendChild.setStatus(AdjustmentNodeStatus.Invalid);
// copy all the code part to handle the deletion of interim DAT/EPC/EPC bin file or add a flag to not replicate the code
//
//                        //for interim node: delete all DAT/EPC/EPS bin file
//                        if (!AdjustmentUtils.isNullNode(matchedBackendChild) && !AdjustmentUtils.isThreadNode(matchedBackendChild) && matchedBackendChild.getAdjustedPLT() != null)
//                            deleteOldAdjustedPLTBin(matchedBackendChild.getAdjustedPLT());
//                        //because we create always new ScorPLTHeader for thread node, so this adjustedPLT is not referenced by another node
//                        //we don't need to change it if it existed, only need to update it
//                        //FIXME: it's not a good approach, need to change soon
//                        if (AdjustmentUtils.isThreadNode(frontendChild)) {
//                            frontendChild.setAdjustedPLT(matchedBackendChild.getAdjustedPLT());
//                            frontendChild.setThreadId(matchedBackendChild.getThreadId());
//                        }
//
//                    }
//                    else
//                    {
// Node has no deleted parent so can remain in its current state
//
//                        frontendChild.setStatus(matchedBackendChild.getStatus());
//                        log.info("mergeSubHierarchy: Node {} already exists and is unchanged", frontendChild.getId());
//                    }
//
////                    mergeSubHierarchy(matchedBackendChild, frontendChild, cloneMap);
//                } else {
//                    // stop copying, its children steps are affected
//                    log.info("mergeSubHierarchy: Node {} already exists, and has been changed, so that it and its children are invalid", frontendChild.getId());
//                    frontendChild.setStatus(AdjustmentNodeStatus.Invalid);
//                    //for interim node: delete all DAT/EPC/EPS bin file
//                    if (!AdjustmentUtils.isNullNode(matchedBackendChild) && !AdjustmentUtils.isThreadNode(matchedBackendChild) && matchedBackendChild.getAdjustedPLT() != null)
//                        deleteOldAdjustedPLTBin(matchedBackendChild.getAdjustedPLT());
//                    //because we create always new ScorPLTHeader for thread node, so this adjustedPLT is not referenced by another node
//                    //we don't need to change it if it existed, only need to update it
//                    //FIXME: it's not a good approach, need to change soon
//                    if (AdjustmentUtils.isThreadNode(frontendChild)) {
//                        frontendChild.setAdjustedPLT(matchedBackendChild.getAdjustedPLT());
//                        frontendChild.setThreadId(matchedBackendChild.getThreadId());
//                    }
//                }
//                mergeSubHierarchy(matchedBackendChild, frontendChild, cloneMap,
//backEndNodeDeletedInd
//false,hasdeletedparent);
//                // further step: assignDBId - loop and assign id to avoid duplication
//                //&&  (matchedDeletedNode.getxActLocked() != null && !matchedDeletedNode.getxActLocked())
//            } else if (matchedDeletedNode != null) {
//                log.debug("Node {} was deleted in frontend, so its children is invalid", matchedDeletedNode.getId());
//                matchedDeletedNode.setStatus(AdjustmentNodeStatus.Invalid);
//                mergeSubHierarchy(matchedDeletedNode, frontendChild, cloneMap,
//backEndNodeDeletedInd
//true,true);
//            } else { // NEW node (possible in same/different branch) possible clone
//                if (cloneMap.containsKey(frontendChild.getId())) { // cloning node
//                    String clonedNodeId = cloneMap.get(frontendChild.getId());
//                    cloneMap.remove(clonedNodeId);
//                    // get the cloned backend node
//                    AdjustmentNode clonedBackendNode = findMatchedNode(backendChildren, clonedNodeId);
//                    if (clonedBackendNode != null) {
//                        cloneNode(clonedBackendNode, frontendChild);
////                        AdjustmentNodeStatus status = AdjustmentUtils.checkStatus(frontendChild, daoService);
////                        frontendChild.setStatus(status);
//                        frontendChild.setId(null);
//                    }
//                    mergeSubHierarchy(clonedBackendNode, frontendChild, cloneMap,
//backEndNodeDeletedInd
//false,hasdeletedparent); // keep cloning subnodes
//                } else {
//                    log.info("mergeSubHierarchy: Node {} is new, so that it and its children is invalid", frontendChild.getId());
//                    frontendChild.setStatus(AdjustmentNodeStatus.Invalid);
//                    frontendChild.setId(null);
//                    // further step: assignDBId - loop and assign id to avoid duplication
//                    mergeSubHierarchy(backendParentStep, frontendChild, cloneMap,
//backEndNodeDeletedInd
//false,hasdeletedparent);
//                }
//            }
//        }
//    }
//
//    private void cloneNode(AdjustmentNode clonedNode, AdjustmentNode cloningNode) {
//        log.info("Cloner node {} from cloned node {}", cloningNode.getId(), clonedNode.getId());
//        AdjustmentNode clonedParent = clonedNode.getParentNode();
//        AdjustmentNode cloningParent = cloningNode.getParentNode();
//        ScorPLTHeader clonedParentAdjPLT = clonedParent.getAdjustedPLT();
//        ScorPLTHeader cloningParentAdjPLT = cloningParent.getAdjustedPLT();
//
//        if (clonedParentAdjPLT == null) {
//            clonedParent = adjustmentNodeRepository.findOne(clonedParent.getId());
//            clonedParentAdjPLT = clonedParent.getAdjustedPLT();
//        }
//        if (cloningParentAdjPLT == null && cloningParent != null && cloningParent.getId() != null) {
//            cloningParent = adjustmentNodeRepository.findOne(cloningParent.getId());
//            cloningParentAdjPLT = cloningParent.getAdjustedPLT();
//        }
//
//        AdjustmentNodeStatus clonedStatus = AdjustmentUtils.checkStatus(clonedParent, daoService);
//        AdjustmentNodeStatus parentStatus = AdjustmentUtils.checkStatus(cloningParent, daoService);
//
//
//        final AdjustmentNode pureNode = clonedNode.getPureNode() == null ? adjustmentNodeRepository.findOne(clonedNode.getId()).getPureNode() : clonedNode.getPureNode();
//
//        cloningNode.setSourcePLT(cloningParentAdjPLT);
//
////        if (AdjustmentUtils.isThreadNode(clonedNode)) {
////            cloningNode.setUserDefinedThreadName("Final PLT");
////        }
//
//
//        //if cloned node is not VALID: we don't have adjust PLT and EPHeader
//        if (!AdjustmentNodeStatus.Valid.equals(clonedNode.getStatus())) {
//            cloningNode.setStatus(clonedNode.getStatus());
//            return;
//        }
//
//        if (AdjustmentUtils.isNullNode(clonedNode)) {
//            cloningNode.setAdjustedPLT(cloningParentAdjPLT);
//            cloningNode.setStatus(clonedNode.getStatus());
//            log.info("mergeSubHierarchy: PLT {} from parent || Null Node",cloningParentAdjPLT.getId());
//        } else {
//            ScorPLTHeader clonedAdjPLT = clonedNode.getAdjustedPLT() == null ? adjustmentNodeRepository.findOne(clonedNode.getId()).getAdjustedPLT() : clonedNode.getAdjustedPLT();
//            clonedAdjPLT = scorPLTHeaderRepository.findOne(clonedAdjPLT.getId());
//
//            // Cloning EPHeaders
////            final List<PLTEPHeader> clonedEPHeaders = clonedAdjPLT.getPLTEPHeaders() == null ? scorPLTHeaderRepository.findOne(clonedAdjPLT.getId()).getPLTEPHeaders() : clonedAdjPLT.getPLTEPHeaders();
//            final List<RRStatisticHeader> clonedRRStatisticHeaders = clonedAdjPLT.getPltStatisticList() == null ? scorPLTHeaderRepository.findOne(clonedAdjPLT.getId()).getPltStatisticList() : clonedAdjPLT.getPltStatisticList();
//
////            List<PLTEPHeader> cloningEPHeaders = new ArrayList<>();
//            List<RRStatisticHeader> cloningRRStatisticHeaders = new ArrayList<>();
////            for (PLTEPHeader clonedEPHeader : clonedRRStatisticHeaders) {
//            for (RRStatisticHeader clonedRRStatisticHeader : clonedRRStatisticHeaders) {
////                PLTEPHeader cloningEPHeader = new PLTEPHeader(clonedEPHeader, null); // FILL pltheader later **
//                RRStatisticHeader cloningRRStatisticHeader = new RRStatisticHeader(clonedRRStatisticHeader, null);
////                mongoDBSequence.nextSequenceId(cloningEPHeader);
//                mongoDBSequence.nextSequenceId(cloningRRStatisticHeader);
////                cloningEPHeaders.add(cloningEPHeader);
//                cloningRRStatisticHeaders.add(cloningRRStatisticHeader);
////                log.info("mergeSubHierarchy: EPHeader {} cloning from {}", cloningEPHeader.getId(), clonedEPHeader.getId());
//                log.info("mergeSubHierarchy: EPHeader {} cloning from {}", cloningRRStatisticHeader.getId(), cloningRRStatisticHeader.getId());
//            }
//            cloningNode.setStatus(AdjustmentUtils.checkStatus(clonedNode, daoService));
//
//            //// Cloning PLT
////            ScorPLTHeader cloningAdjPLT = new ScorPLTHeader(clonedAdjPLT, cloningEPHeaders, null);
//            ScorPLTHeader cloningAdjPLT = new ScorPLTHeader(clonedAdjPLT, cloningRRStatisticHeaders, null);
//            mongoDBSequence.nextSequenceId(cloningAdjPLT);
//
//            // Cloning binary PLT: NEW PLT TO BE CLONED FROM CLONEDADJPLT
//            final BinFile clonedBinFile = clonedAdjPLT.getPltLossDataFile();
//            //NOTE: for interim PLT, there is nodeID in filename -> need to replace it by cloning's nodeID
//            BinFile destBinFile;
//            if (AdjustmentUtils.isThreadNode(clonedNode))
//                destBinFile = TransformationUtils.makeScorPLTLossDataFile(clonedBinFile, cloningAdjPLT.getId());
//            else
//                destBinFile = TransformationUtils.makeScorPLTLossDataFile(clonedBinFile, cloningAdjPLT.getId(), cloningNode.getId());
//
//            File sourceFile = new File(clonedBinFile.getPath(), clonedBinFile.getFileName());
//            File destFile = new File(destBinFile.getPath(), destBinFile.getFileName());
//            try {
//                TransformationUtils.copyFile(sourceFile, destFile);
//                log.info("mergeSubHierarchy: Cloning node {}: copying {} to {}", cloningNode.getId(), clonedBinFile, destBinFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            log.info("mergeSubHierarchy: File {} cloning from {}", destFile.getName(), sourceFile.getName());
//            cloningAdjPLT.setPltLossDataFile(destBinFile);
//
////            for (PLTEPHeader cloningEPHeader : cloningEPHeaders) {
////                cloningEPHeader.setScorPLTHeader(cloningAdjPLT); // **
////            }
//
//            for (RRStatisticHeader cloningRRStatisticHeader : cloningRRStatisticHeaders) {
//                cloningRRStatisticHeader.setLossTableId(cloningAdjPLT.getId()); // * only scorPLTHeader id*
//            }
//            cloningNode.setAdjustedPLT(cloningAdjPLT);
//            log.info("mergeSubHierarchy: PLT {} cloning from {}", cloningAdjPLT.getId(), clonedAdjPLT.getId());
//
//            //Cloning EPC/EPS
//            String srcEPC = clonedBinFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPC + "$2");
//            String dstEPC = destBinFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPC + "$2");
//            File srcEPCFile = new File(clonedBinFile.getPath(), srcEPC);
//            File dstEPCFile = new File(destBinFile.getPath(), dstEPC);
//            try {
//                TransformationUtils.copyFile(srcEPCFile, dstEPCFile);
//                log.info("mergeSubHierarchy: Cloning node {}: copying {} to {}", cloningNode.getId(), srcEPC, dstEPC);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            String srcEPS = clonedBinFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPS + "$2");
//            String dstEPS = destBinFile.getFileName().replaceAll("(.+_)DAT(_.+)", "$1" + XLTSubType.EPS + "$2");
//            File srcEPSFile = new File(clonedBinFile.getPath(), srcEPS);
//            File dstEPSFile = new File(destBinFile.getPath(), dstEPS);
//            try {
//                TransformationUtils.copyFile(srcEPSFile, dstEPSFile);
//                log.info("mergeSubHierarchy: Cloning node {}: copying {} to {}", cloningNode.getId(), srcEPS, dstEPS);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            scorPLTHeaderRepository.save(cloningAdjPLT);
////            pltepHeaderRepository.save(cloningEPHeaders);
//            rrStatisticHeaderRepository.save(cloningRRStatisticHeaders);
//        }
//        // notice - do not save
//        // adjustmentNodeRepository.save(cloningNode);
//        // we are to save cloningNode in a following method
//
//    }
//
//    private AdjustmentNode findMatchedNode(List<AdjustmentNode> adjustmentNodes, AdjustmentNode candidate) {
//        for (AdjustmentNode adjustmentNode : adjustmentNodes) {
//            if (adjustmentNode.equals(candidate)) {
//                return adjustmentNode;
//            }
//        }
//        return null;
//    }
//
//    private AdjustmentNode findMatchedNode(List<AdjustmentNode> adjustmentNodes, String nodeId) {
//        for (AdjustmentNode adjustmentNode : adjustmentNodes) {
//            if (StringUtils.equalsIgnoreCase(adjustmentNode.getId(), nodeId)) {
//                return adjustmentNode;
//            }
//        }
//        return null;
//    }
//
//    private List<Node> convertToTree(AdjustmentNode adjNode, int depthInZone) {
//        List<Node> nodes = new ArrayList<>();
//        AdjustmentNode parentAdjNode = adjNode.getParentNode();
//        if (adjNode.getParentNode() != null && adjNode.getAdjustmentCategory().equals(parentAdjNode.getAdjustmentCategory())) {
//            depthInZone++;
//        } else {
//            depthInZone = 0;
//        }
//        nodes.add(convertToNode(adjNode, depthInZone));
//        if (adjNode.getChildrenNodes() == null || adjNode.getChildrenNodes().size() == 0)
//            return nodes;
//        else {
//            for (AdjustmentNode childAdjNode : adjNode.getChildrenNodes()) {
//                nodes.addAll(convertToTree(childAdjNode, depthInZone));
//            }
//        }
//        return nodes;
//    }
//
//    private String getBasisShortname(AdjustmentBasis adjustmentBasis) {
//        if (adjustmentBasis == null)
//            return null;
//        if (adjustmentBasis.getBasisShortname() != null)
//            return adjustmentBasis.getBasisShortname();
//        else if (adjustmentBasis.getAdjustmentBasis() != null)
//            return  adjustmentBasis.getAdjustmentBasis();
//
//        return null;
//    }
//
//*
//     * Convert adjustment node to object for frontend use
//     * @param adjNode
//     * @param depthInZone
//     * @return
//
//
//    private Node convertToNode(AdjustmentNode adjNode, int depthInZone) {
////        try {
//
////        log.info("convertToNode {}", adjNode.getId());
//        String parentId = adjNode.getParentNode() == null ? null : adjNode.getParentNode().getId();
//        List<String> childrenIds = new ArrayList<>();
//        if (adjNode.getChildrenNodes() != null) {
//            for (AdjustmentNode adjustmentNode : adjNode.getChildrenNodes()) {
//                childrenIds.add(adjustmentNode.getId());
//            }
//        }
//
//        String[] childrenArr = new String[childrenIds.size()];
//        childrenIds.toArray(childrenArr);
//        Map<String, Object> data = new HashMap<>();
//        Node node = new Node(adjNode.getId(),
//                adjNode.getNodeName(),
//                adjNode.getAdjustmentCategory().getValue(),
//                depthInZone,
//                parentId,
//                childrenArr,
//                adjNode.getStatus().getValue(),
//                data,
//                AdjustementNodeMode.AdjustmentMatrix.equals(adjNode.getAdjustementNodeMode()) ? true : false,
//                adjNode.getGeneratedBySystemPltNameLAC() == null ? false : adjNode.getGeneratedBySystemPltNameLAC(),
//                adjNode.getDefaultPltName(),
//                adjNode.getPltName(),
//                adjNode.getPltNameSuffix());
//
//
//        if (adjNode.getxActLocked() != null)
//            node.setIs_published(adjNode.getxActLocked());
//
//        // Adjustment data
//        Map<String, Object> adjData = new HashMap<>();
//        data.put(Node.KEY_ADJUSTMENT, adjData);
//
//        // filling
//        String adjName = null;
//        Double adjParam = null;
//        List<List<String>> adjPEATParams = null;
//        List<List<Double>> adjProfileParams = null;
//        List<Object> adjObjectParams = null;
//        List<String> adjComments = new ArrayList<>();
//
//        if (adjNode.getAdjustmentType() == null
//                || StringUtils.equalsIgnoreCase(adjNode.getAdjustmentType(), AdjustmentType.NONE)) {
//            adjParam = 0d;
//            adjName = "+";
//        } else if (StringUtils.equalsIgnoreCase(adjNode.getAdjustmentType(), AdjustmentType.LINEAR)
//                || StringUtils.equalsIgnoreCase(adjNode.getAdjustmentType(), AdjustmentType.FREQUENCY_EEF)) { // single param
//            String basisShortname = getBasisShortname(adjNode.getAdjustmentBasis());
//            adjName =  basisShortname != null ? basisShortname : node.getDisplayName();
//            adjParam = adjNode.getAdjustmentParam();
//        } else if (StringUtils.equalsIgnoreCase(adjNode.getAdjustmentType(), AdjustmentType.RP_BANDING_EEF)
//                || StringUtils.equalsIgnoreCase(adjNode.getAdjustmentType(), AdjustmentType.RP_BANDING_OEP)) { // external files
//            String basisShortname = getBasisShortname(adjNode.getAdjustmentBasis());
//            adjName =  basisShortname != null ? basisShortname : node.getDisplayName();
//            adjProfileParams = adjNode.getProfileDelivery();
//        } else if (StringUtils.equalsIgnoreCase(adjNode.getAdjustmentType(), AdjustmentType.EV_SPEC)) {
//            String basisShortname = getBasisShortname(adjNode.getAdjustmentBasis());
//            adjName =  basisShortname != null ? basisShortname : node.getDisplayName();
////            adjPEATParams = new RawCSVReaderImpl().readCSV(adjNode.getAdjustmentParamsFile().getPath(), adjNode.getAdjustmentParamsFile().getFileName(), 11);
//        } else if (StringUtils.equalsIgnoreCase(adjNode.getAdjustmentType(), AdjustmentType.CAT_XL)) {
//            String basisShortname = getBasisShortname(adjNode.getAdjustmentBasis());
//            adjName =  basisShortname != null ? basisShortname : node.getDisplayName();
//            adjObjectParams = adjNode.getAdjustmentObjectParams();
//        } else if (StringUtils.equalsIgnoreCase(adjNode.getAdjustmentType(), AdjustmentType.QUOTA_SHARE)) {
//            String basisShortname = getBasisShortname(adjNode.getAdjustmentBasis());
//            adjName =  basisShortname != null ? basisShortname : node.getDisplayName();
//            adjProfileParams = adjNode.getProfileDelivery();
//        }
//
//        if (AdjustmentUtils.isThreadNode(adjNode)) {
//            if (adjNode.getUserDefinedThreadName() != null) {
//                adjName = adjNode.getUserDefinedThreadName();
//            } else {
//                ScorPLTHeader sourcePLT = adjustmentNodeRepository.findOne(adjNode.getId()).getSourcePLT();
//                if (sourcePLT != null && sourcePLT.getUdName() != null) {
//                    adjName = sourcePLT.getUdName().replaceAll(" ", "");
//                } else {
//                    adjName = "Final PLT";
//                }
//            }
//        }
//
//        if (AdjustmentUtils.isThreadNode(adjNode)) {
//            if (adjNode.getPltName() == null) {
//                if (adjNode.getUserDefinedThreadName() != null) {
//                    node.setPltName(adjNode.getUserDefinedThreadName());
//                } else {
//                    ScorPLTHeader sourcePLT = adjustmentNodeRepository.findOne(adjNode.getId()).getSourcePLT();
//                    if (sourcePLT != null && sourcePLT.getPltName() != null) {
//                        node.setPltName(sourcePLT.getPltName().replaceAll(" ", ""));
//                    } else {
//                        if (sourcePLT != null && sourcePLT.getUdName() != null) {
//                            node.setPltName(sourcePLT.getUdName().replaceAll(" ", ""));
//                        } else {
//                            node.setPltName("Final PLT");
//                        }
//                    }
//                }
//            }
//        }
//
//        if (AdjustmentUtils.isThreadNode(adjNode)) {
//            if (adjNode.getDefaultPltName() == null) {
//                ScorPLTHeader sourcePLT = adjustmentNodeRepository.findOne(adjNode.getId()).getSourcePLT();
//                if (sourcePLT != null && sourcePLT.getDefaultPltName() != null) {
//                    node.setDefaultPltName(sourcePLT.getDefaultPltName());
//                } else {
//                    PLTNaming pltNaming = buildDefaultPltName(adjNode);
//                    log.info("pltNaming when convert to Node {}", pltNaming);
//                    node.setDefaultPltName(pltNaming.pltName);
//                }
//            }
//        }
//
//        adjData.put(Node.KEY_ADJUSTMENT_NAME, adjName);
//        adjData.put(Node.KEY_ADJUSTMENT_TYPE, adjNode.getAdjustmentType());
//        adjData.put(Node.KEY_ADJUSTMENT_TYPE_SHORTNAME, adjNode.getAdjustmentTypeShortName() != null ? adjNode.getAdjustmentTypeShortName() : "");
//        adjData.put(Node.KEY_ADJUSTMENT_RATE, adjParam);
//        adjData.put(Node.KEY_PLT_ID, adjNode.getAdjustedPLT() != null ? "[" + adjNode.getAdjustedPLT().getId().replace("SPLTH-", "").replaceFirst("^0*", "") + "]" : "");
//        adjData.put(Node.KEY_ADJUSTMENT_COMMENTS, adjComments);
//        adjData.put(Node.KEY_ADJUSTMENT_PEAT, adjPEATParams);
//        adjData.put(Node.KEY_ADJUSTMENT_FL_NEW_PEAT, false);
//        adjData.put(Node.KEY_ADJUSTMENT_PEATHEADERS, adjNode.getAdjustmentParamsMapping());
//        adjData.put(Node.KEY_ADJUSTMENT_PROFILE, adjProfileParams);
//        adjData.put(Node.KEY_ADJUSTMENT_FL_NEW_PROFILE, false);
//        adjData.put(Node.KEY_ADJUSTMENT_BASIS, adjNode.getBasisMap());
//        adjData.put(Node.KEY_ADJUSTMENT_FILE, adjNode.getParamsFileMap());
//        adjData.put(Node.KEY_ADJUSTMENT_NARRATIVE, adjNode.getNarrative());
//        adjData.put(Node.KEY_ADJUSTMENT_OBJECT_PARAM, adjObjectParams);
//        adjData.put(Node.KEY_ADJUSTMENT_LOSS_NET, adjNode.getLossNetFlag() != null ? adjNode.getLossNetFlag() : "net");
//
//
//        // Chart data
//        Map<String, Object> chartData = new HashMap<>();
//        data.put(Node.CHART, chartData);
//
//        ScorPLTHeader adjustedPLT = adjNode.getAdjustedPLT();
//        if (adjustedPLT == null) {
//            return node;
//        }
//
//        adjustedPLT = scorPLTHeaderRepository.findById(adjustedPLT.getId());
//
////        List<PLTEPHeader> pltepHeaders = adjustedPLT.getPLTEPHeaders();
////        if (pltepHeaders == null) {
////            return node;
////        }
//
//        List<RRStatisticHeader> rrStatisticHeaders = adjustedPLT.getPltStatisticList();
//        if (rrStatisticHeaders == null) {
//            return node;
//        }
//
////        log.info("convertToNode {} has loss data, total {} pltepheaders, adjPLTid = {}", node.getId(), pltepHeaders.size(), adjustedPLT.getId());
//
////        for (PLTEPHeader pltepHeader : rrStatisticHeaders) {
//        for (RRStatisticHeader rrStatisticHeader : rrStatisticHeaders) {
////            log.info("Node {}, pltepHeader.getId() = {}", adjNode.getId(), pltepHeader.getId());
////            pltepHeader = pltepHeaderRepository.findOne(pltepHeader.getId());
//            rrStatisticHeader = rrStatisticHeaderRepository.findOne(rrStatisticHeader.getId());
////            StatisticMetric metric = pltepHeader.getStatisticMetric();
//            StatisticMetric metric = rrStatisticHeader.getStatisticData().getStatisticMetric();
//            if (metric.equals(StatisticMetric.CEP)) { // TODO - REVIEW
//                continue;
//            }
//            List<Integer> rps = new ArrayList<>();
//            List<Double> losses = new ArrayList<>();
////            getChartData(metric, pltepHeader.getEpCurves(), rps, losses);
//            getChartData(metric, rrStatisticHeader.getStatisticData().getEpCurves(), rps, losses);
//            chartData.put(getKeyRP(metric), rps);
//            chartData.put(getKeyLoss(metric), losses);
//        }
//
//        return node;
////        } finally {
////            ScorPLTHeader ertestPLTHeader = adjNode.getAdjustedPLT();
////            ertestPLTHeader = scorPLTHeaderRepository.findOne(ertestPLTHeader.getId());
////            if (ertestPLTHeader == null) {
////                log.info("convertoNode after has null pltepheaders");
////            } else {
////                log.info("convertoNode after has {} pltepheaders", ertestPLTHeader.getPLTEPHeaders().size());
////            }
////        }
//    }
//
////    private void getChartData(StatisticMetric statisticMetric, List<PLTEPCurve> pltEPCurves, List<Integer> rp, List<Double> losses) {
////        for (PLTEPCurve pltEPCurve : pltEPCurves) {
////            if (pltEPCurve.getReturnPeriod() % 5 != 0) {
////                continue;
////            }
////            rp.add(pltEPCurve.getReturnPeriod());
////            losses.add(pltEPCurve.getLossAmount());
////        }
////    }
//
//    private void getChartData(StatisticMetric statisticMetric, List<RREPCurve> pltEPCurves, List<Integer> rp, List<Double> losses) {
//        for (RREPCurve pltEPCurve : pltEPCurves) {
//            if (pltEPCurve.getReturnPeriod() % 5 != 0) {
//                continue;
//            }
//            rp.add(pltEPCurve.getReturnPeriod());
//            losses.add(pltEPCurve.getLossAmount());
//        }
//    }
//
//    private String getKeyRP(StatisticMetric statisticMetric) {
//        switch (statisticMetric) {
//            case EEF:
//                return Node.CHART_RP_EEF;
//            case OEP:
//                return Node.CHART_RP_OEP;
//            case AEP:
//                return Node.CHART_RP_AEP;
//            case TVAR_OEP:
//                return Node.CHART_RP_OEP_TCE;
//            case TVAR_AEP:
//                return Node.CHART_RP_AEP_TCE;
//            case CEP:
//                return Node.CHART_RP_CEP;
//            default:
//                return null;
//        }
//    }
//
//    private String getKeyLoss(StatisticMetric statisticMetric) {
//        switch (statisticMetric) {
//            case EEF:
//                return Node.CHART_LOSS_EEF;
//            case OEP:
//                return Node.CHART_LOSS_OEP;
//            case AEP:
//                return Node.CHART_LOSS_AEP;
//            case TVAR_OEP:
//                return Node.CHART_LOSS_OEP_TCE;
//            case TVAR_AEP:
//                return Node.CHART_LOSS_AEP_TCE;
//            case CEP:
//                return Node.CHART_LOSS_CEP;
//            default:
//                return null;
//        }
//    }
//
//    private List<AdjustmentNode> findParentsFinalNode(AdjustmentNode adjNode) {
//        List<AdjustmentNode> nodes = new ArrayList<>();
//        if (AdjustmentUtils.isPureNode(adjNode.getParentNode()))
//            return nodes;
//        else {
//            nodes.add(adjNode.getParentNode());
//            nodes.addAll(findParentsFinalNode(adjNode.getParentNode()));
//        }
//        return nodes;
//    }
//
//    public class PLTNaming{
//        private String pltName;
//        private String threadName;
//        public PLTNaming(String fname, String sname){
//            pltName = fname;
//            threadName = sname;
//        }
//
//        public String getPltName() {
//            return pltName;
//        }
//
//        public void setPltName(String pltName) {
//            this.pltName = pltName;
//        }
//
//        public String getThreadName() {
//            return threadName;
//        }
//
//        public void setThreadName(String threadName) {
//            this.threadName = threadName;
//        }
//    }
//
//    public PLTNaming buildDefaultPltName(AdjustmentNode adjustmentNode) {
//        String pltName = null;
//        String threadName = null;
//
//        AdjustmentStructure adjustmentStructure = adjustmentNode.getAdjustmentStructure();
//        String regionPeril = null;
//        if (adjustmentStructure.getRegionPeril() != null) {
//            regionPeril = adjustmentStructure.getRegionPeril().getRegionPerilCode();
//        }
//
//        String sourceFinPersp = null;
//        RRAnalysis rrAnalysis = rrAnalysisRepository.findOne(adjustmentStructure.getScorPLTHeader().getRrAnalysisId());
//        if (rrAnalysis != null) {
//            sourceFinPersp = "_" + rrAnalysis.getFpDisplayCode();
//        }
//
//        String threadId = ".T" + adjustmentNode.getThreadId();
//
//        List<AdjustmentNode> listParentFinalNode = findParentsFinalNode(adjustmentNode);
//        boolean defaultAdjustment = checkDefaultAdjustment(listParentFinalNode);
//        String returnPeriodAdj = buildReturnPeriodAdj(listParentFinalNode);
//        String simpleInuringAdj = buildSimpleInuringAdj(listParentFinalNode);
//        String overallLMF = buildOverallLMF(listParentFinalNode, defaultAdjustment);
//        String pltNameSuffix = adjustmentNode.getPltNameSuffix();
//        if (pltNameSuffix != null && !("").equals(pltNameSuffix.trim())) {
//            pltNameSuffix = "." + pltNameSuffix.trim();
//        } else {
//            pltNameSuffix = null;
//        }
//
//        pltName = regionPeril + sourceFinPersp + overallLMF + Objects.toString(returnPeriodAdj, "") + Objects.toString(simpleInuringAdj, "");
//        threadName = pltName + Objects.toString(pltNameSuffix, "") + threadId;
//
//        PLTNaming pltNaming = new PLTNaming(pltName, threadName);
//        return pltNaming;
//    }
//
//    private boolean checkDefaultAdjustment(List<AdjustmentNode> listParentFinalNode) {
//        boolean defaultAdjustment = false;
//        for (AdjustmentNode adjustmentNode : listParentFinalNode) {
//            if (AdjustmentCategory.DEFAULT.equals(adjustmentNode.getAdjustmentCategory())) {
//                if (!adjustmentNode.getAdjustmentType().equals("None")) {
//                    defaultAdjustment = true;
//                    break;
//                }
//            }
//        }
//        return defaultAdjustment;
//    }
//
//    private String buildOverallLMF(List<AdjustmentNode> listParentFinalNode, boolean defaultAdjustment) {
//        String overallLMFStr = null;
//        double overallLMF = 1.00;
//        double adjustmentParam = 1.00;
//        int countLinearAdj = 0;
//
//        for (AdjustmentNode adjustmentNode1 : listParentFinalNode) {
//            if (adjustmentNode1.getAdjustmentType().equals("Linear")) {
//                countLinearAdj++;
//                adjustmentParam = (double) adjustmentNode1.getAdjustmentParam();
//                overallLMF = overallLMF * adjustmentParam;
//            }
//        }
//
//        DecimalFormat formatter = new DecimalFormat("0.00");
//        String lmf = (formatter.format(overallLMF)).replaceAll(",", ".");
//        if (countLinearAdj == 0) {
//            lmf = "1";
//        }
//
//        if (defaultAdjustment) {
//            overallLMFStr = "_DefAdj_LMF" + lmf;
//        } else {
//            overallLMFStr = "_LMF" + lmf;
//        }
//
//        return overallLMFStr;
//    }
//
//    private String buildReturnPeriodAdj(List<AdjustmentNode> listParentFinalNode) {
//        String returnPeriodAdj = null;
//        for (AdjustmentNode adjustmentNode1 : listParentFinalNode) {
//            if (adjustmentNode1.getAdjustmentType().equals("Return Period Banding Severity (OEP)") || adjustmentNode1.getAdjustmentType().equals("Return Period Banding Severity (EEF)")) {
//                returnPeriodAdj = "_RPadj";
//                break;
//            }
//        }
//        return returnPeriodAdj;
//    }
//
//    private String buildSimpleInuringAdj(List<AdjustmentNode> listParentFinalNode) {
//        String simpleInuringAdj = null;
//        for (AdjustmentNode adjustmentNode1 : listParentFinalNode) {
//            if (adjustmentNode1.getAdjustmentType().equals("CAT XL") || adjustmentNode1.getAdjustmentType().equals("Quota Share")) {
//                simpleInuringAdj = "_Simple";
//                break;
//            }
//        }
//        return simpleInuringAdj;
//    }
//}
