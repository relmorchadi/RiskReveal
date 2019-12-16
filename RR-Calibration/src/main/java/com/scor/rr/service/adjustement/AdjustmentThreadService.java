package com.scor.rr.service.adjustement;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadBranchingRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadCreationRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentThreadUpdateRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.service.cloning.CloningScorPltHeaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Supplier;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;
import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.NONLINEAR_OEP_RPB;
import static com.scor.rr.exceptions.ExceptionCodename.THREAD_NOT_FOUND;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentThreadService {
    private static final Logger log = LoggerFactory.getLogger(AdjustmentThreadService.class);

    @Autowired
    private AdjustmentNodeRepository adjustmentNodeRepository;

    @Autowired
    private ScalingAdjustmentParameterRepository scalingAdjustmentParameterRepository;

    @Autowired
    private EventBasedAdjustmentParameterRepository eventBasedAdjustmentParameterRepository;

    @Autowired
    private AdjustmentScalingParameterService adjustmentScalingParameterService;

    @Autowired
    private AdjustmentReturnPeriodBandingParameterService periodBandingParameterService;

    @Autowired
    private AdjustmentEventBasedParameterService eventBasedParameterService;

    @Autowired
    private CloningScorPltHeaderService cloningScorPltHeaderService;

    @Autowired
    private ReturnPeriodBandingAdjustmentParameterRepository returnPeriodBandingAdjustmentParameterRepository;

    @Autowired
    private AdjustmentThreadRepository adjustmentThreadRepository;

    @Autowired
    private AdjustmentNodeOrderRepository adjustmentNodeOrderRepository;

    @Autowired
    private AdjustmentNodeProcessingRepository adjustmentNodeProcessingRepository;

    @Autowired
    private AdjustmentNodeService adjustmentNodeService;

    @Autowired
    private AdjustmentNodeProcessingService adjustmentNodeProcessingService;

    @Autowired
    private PltHeaderRepository pltHeaderRepository;

    @Autowired
    private DefaultAdjustmentService defaultAdjustmentService;

    public AdjustmentThreadEntity findOne(Integer id){
        return adjustmentThreadRepository.findById(id).orElseThrow(throwException(THREAD_NOT_FOUND,NOT_FOUND));
    }

    public List<AdjustmentThreadEntity> findAll(){
        return adjustmentThreadRepository.findAll();
    }

    //NOTE: thread is used to group a list of in-order nodes. How the implementation is done for persisting a thread with these nodes ?
    //Refactor need to be done (methods name does not refer to what they are doing)
    //Done

    public AdjustmentThreadEntity createNewAdjustmentThread(AdjustmentThreadCreationRequest adjustmentThreadCreationRequest) throws RRException {
        AdjustmentThreadEntity adjustmentThreadEntity = new AdjustmentThreadEntity();
//        adjustmentThreadEntity.setCreatedOn(new Timestamp(new Date().getTime()));
//        adjustmentThreadEntity.setLastModifiedOn(adjustmentThreadEntity.getCreatedOn());
//        adjustmentThreadEntity.setCreatedBy(adjustmentThreadCreationRequest.getCreatedBy());
        adjustmentThreadEntity.setLocked(false);
        adjustmentThreadEntity.setThreadStatus("Initialized");
        if(pltHeaderRepository.findById(adjustmentThreadCreationRequest.getPltPureId()).isPresent()) {
            PltHeaderEntity pltHeaderEntity = pltHeaderRepository.findById(adjustmentThreadCreationRequest.getPltPureId()).get();
            if(pltHeaderEntity.getPltType().equalsIgnoreCase("pure")) {
                adjustmentThreadEntity.setInitialPLT(pltHeaderRepository.findById(adjustmentThreadCreationRequest.getPltPureId()).get());
                adjustmentThreadEntity = adjustmentThreadRepository.save(adjustmentThreadEntity);
                if (adjustmentThreadCreationRequest.isGenerateDefaultThread()) {
                    adjustmentThreadEntity = defaultAdjustmentService.createDefaultThread(adjustmentThreadEntity);
                }
                return adjustmentThreadEntity;
            } else {
                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_TYPE_NOT_CORRECT, 1);
            }
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_NOT_FOUND, 1);
        }
    }

    public AdjustmentThreadEntity getByPltHeader(Long pltHeaderId){
        return adjustmentThreadRepository.getByFinalPLTPltHeaderId(pltHeaderId);
    }

    public AdjustmentThreadEntity updateAdjustmentThreadFinalPLT(AdjustmentThreadUpdateRequest request) throws RRException {
        AdjustmentThreadEntity adjustmentThreadEntity = adjustmentThreadRepository.getOne(request.getAdjustmentThreadId());
        if(adjustmentThreadEntity != null) {
            adjustmentThreadEntity.setFinalPLT(pltHeaderRepository.getOne(request.getPltFinalId()));
            adjustmentThreadEntity.setLocked(request.isLocked());
            return adjustmentThreadRepository.save(adjustmentThreadEntity);
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND,1);
        }
    }

    public Integer findOrderOfNode(AdjustmentNode node) {
        log.info("------ findOrderOfNode ------");
        AdjustmentNodeOrder nodeOrder = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(node.getAdjustmentNodeId());
        return nodeOrder.getAdjustmentOrder();
    }

    public AdjustmentThreadEntity cloneThread(Integer threadId) throws RRException {
        AdjustmentThreadEntity thread = adjustmentThreadRepository.getOne(threadId);

        if (thread == null) {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND, 1);
        }

        if (thread.getInitialPLT() == null) {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_NOT_FOUND, 1);
        }

        AdjustmentThreadEntity newThread = new AdjustmentThreadEntity();
//        newThread.setThreadIndex(thread.getThreadIndex()); // todo
        newThread.setThreadStatus(thread.getThreadStatus());
        newThread.setLocked(thread.getLocked());
        newThread.setInitialPLT(thread.getInitialPLT());
        newThread.setFinalPLT(cloningScorPltHeaderService.cloneScorPltHeader(thread.getFinalPLT().getPltHeaderId()));
        adjustmentThreadRepository.save(newThread); // save to take id

        // clone nodes
        List<AdjustmentNode> adjustmentNodes = adjustmentNodeRepository.findByAdjustmentThread(thread);
        cloneListOfNodes(adjustmentNodes, newThread);
        return newThread;
    }

    public void cloneListOfNodes(List<AdjustmentNode> nodes, AdjustmentThreadEntity newThread) throws RRException {
        if (nodes != null && !nodes.isEmpty()) {
            // sort adjustmentNodes by node order from 1 to n then processing
            nodes.sort(
                    Comparator.comparing(this::findOrderOfNode));
            PltHeaderEntity newInputPLT = null;
            Map<AdjustmentNode, Integer> mapNodeAndOrder = new HashMap<>();
            for (AdjustmentNode node : nodes) {
                mapNodeAndOrder.put(node, nodes.indexOf(node) + 1);
            }

            for (AdjustmentNode node : nodes) {
                AdjustmentNode newNode = new AdjustmentNode(node);
                newNode.setAdjustmentThread(newThread);
                newNode.setCloningSource(node);
                adjustmentNodeRepository.save(newNode);

                // clone nodes order
                cloneOrderNode(node, newNode);
                // clone parameters of nodes
                cloneParameterNode(node, newNode);
//                // clone nodes processing
//                AdjustmentNodeProcessingEntity processing = adjustmentNodeProcessingRepository.findByAdjustmentNode(node);
//                if (processing != null) {
//                    AdjustmentNodeProcessingEntity newProcessing = new AdjustmentNodeProcessingEntity();
//                    newProcessing.setAdjustmentNode(newNode);
//                    newProcessing.setAdjustedPLT(cloningScorPltHeaderService.cloneScorPltHeader(processing.getAdjustedPLT().getPltHeaderId()));
//                    if (mapNodeAndOrder.get(node) == null) {
//                        throw new IllegalStateException("node order = null, wrong");
//                    }
//                    if (1 == mapNodeAndOrder.get(node)) {
//                        newProcessing.setInputPLT(newNode.getAdjustmentThread().getInitialPLT());
//                    } else {
//                        newProcessing.setInputPLT(newInputPLT);
//                    }
//                    adjustmentNodeProcessingRepository.save(newProcessing); // check if newInputPLT changes, be safe : save before
//                    newInputPLT = newProcessing.getAdjustedPLT();
//                }
            }
        }
    }

    public void cloneListOfNodesWithoutDefaultAdjustment(Map<AdjustmentNode, Integer> mapNodeNonDefaultAndOrder, AdjustmentThreadEntity newThread) throws RRException {

        if (mapNodeNonDefaultAndOrder != null && !mapNodeNonDefaultAndOrder.isEmpty()) {

            PltHeaderEntity newInputPLT = null;

            // todo treat default adjustment nodes
            for (Map.Entry<AdjustmentNode, Integer> entryNode : mapNodeNonDefaultAndOrder.entrySet()) {
                AdjustmentNode node = entryNode.getKey();
                AdjustmentNode newNode = new AdjustmentNode(node);
                newNode.setAdjustmentThread(newThread);
                newNode.setCloningSource(node);
                adjustmentNodeRepository.save(newNode);

                // clone nodes order
                cloneOrderNodeNonDefault(node, newNode, entryNode.getValue());
                // clone parameters of nodes
                cloneParameterNode(node, newNode);
                // clone nodes processing
//                AdjustmentNodeProcessingEntity processing = adjustmentNodeProcessingRepository.findByAdjustmentNode(node);
//                if (processing != null) {
//                    AdjustmentNodeProcessingEntity newProcessing = new AdjustmentNodeProcessingEntity();
//                    newProcessing.setAdjustmentNode(newNode);
//                    newProcessing.setAdjustedPLT(cloningScorPltHeaderService.cloneScorPltHeader(processing.getAdjustedPLT().getPltHeaderId()));
//                    if (mapNodeNonDefaultAndOrder.get(node) == null) {
//                        throw new IllegalStateException("node order = null, wrong");
//                    }
//                    if (1 == mapNodeNonDefaultAndOrder.get(node)) {
//                        newProcessing.setInputPLT(newNode.getAdjustmentThread().getInitialPLT());
//                    } else {
//                        newProcessing.setInputPLT(newInputPLT);
//                    }
//                    adjustmentNodeProcessingRepository.save(newProcessing); // check if newInputPLT changes, be safe : save before
//                    newInputPLT = newProcessing.getAdjustedPLT();
//                }
            }
        }
    }

    public void cloneOrderNode(AdjustmentNode node, AdjustmentNode newNode) {
        AdjustmentNodeOrder order = adjustmentNodeOrderRepository.findByAdjustmentNode(node);
        if (order == null) {
            throw new IllegalStateException("order object = null, wrong");
        }
        AdjustmentNodeOrder newOrder = new AdjustmentNodeOrder();
        newOrder.setAdjustmentThread(newNode.getAdjustmentThread());
        newOrder.setAdjustmentNode(newNode);
        newOrder.setAdjustmentOrder(order.getAdjustmentOrder());
        adjustmentNodeOrderRepository.save(newOrder);
    }

    public void cloneOrderNodeNonDefault(AdjustmentNode node, AdjustmentNode newNode, Integer orderNonDefault) {
        AdjustmentNodeOrder order = adjustmentNodeOrderRepository.findByAdjustmentNode(node);
        if (order == null) {
            throw new IllegalStateException("order object = null, wrong");
        }
        AdjustmentNodeOrder newOrder = new AdjustmentNodeOrder();
        newOrder.setAdjustmentThread(newNode.getAdjustmentThread());
        newOrder.setAdjustmentNode(newNode);
        newOrder.setAdjustmentOrder(orderNonDefault);
        adjustmentNodeOrderRepository.save(newOrder);
    }

    public void cloneParameterNode(AdjustmentNode node, AdjustmentNode newNode) {
        log.info("cloneParameterNode, {}", node.getAdjustmentTypeCode());
        if (LINEAR.getValue().equals(node.getAdjustmentTypeCode()) || EEF_FREQUENCY.getValue().equals(node.getAdjustmentTypeCode())) {
            ScalingAdjustmentParameter parameter = scalingAdjustmentParameterRepository.findByAdjustmentNode(node);
            if (parameter != null) {
                adjustmentScalingParameterService.save(new ScalingAdjustmentParameter(parameter.getAdjustmentFactor(), newNode));
            }
        } else if (NONLINEAR_EVENT_PERIOD_DRIVEN.getValue().equals(node.getAdjustmentTypeCode()) || NONLINEAR_EVENT_DRIVEN.getValue().equals(node.getAdjustmentTypeCode())) {
            EventBasedAdjustmentParameter parameter = eventBasedAdjustmentParameterRepository.findByAdjustmentNode(node);
            if (parameter != null) {
                eventBasedParameterService.save(new EventBasedAdjustmentParameter(parameter.getInputFilePath(), parameter.getInputFileName(), newNode));
            }
        } else if (NONLINEAR_EEF_RPB.getValue().equals(node.getAdjustmentTypeCode()) || NONLINEAR_OEP_RPB.getValue().equals(node.getAdjustmentTypeCode())) {
            List<ReturnPeriodBandingAdjustmentParameter> parameters = returnPeriodBandingAdjustmentParameterRepository.findByAdjustmentNode(node);
            for (ReturnPeriodBandingAdjustmentParameter parameter : parameters) {
                periodBandingParameterService.save(new ReturnPeriodBandingAdjustmentParameter(parameter.getReturnPeriod(), parameter.getAdjustmentFactor(), newNode));
            }
        } else {
            throw new IllegalStateException("---------- cloneParameterNode, type not found ----------");
        }
    }

    public AdjustmentThreadEntity cloneThreadWithoutDefaultAdjustment(Integer threadId) throws RRException {
        AdjustmentThreadEntity thread = adjustmentThreadRepository.getOne(threadId);

        if (thread == null) {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND, 1);
        }

        if (thread.getInitialPLT() == null) {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PLT_NOT_FOUND, 1);
        }

        AdjustmentThreadEntity newThread = new AdjustmentThreadEntity();
        //        newThread.setThreadIndex(thread.getThreadIndex()); // todo
        newThread.setThreadStatus(thread.getThreadStatus());
        newThread.setLocked(thread.getLocked());
        newThread.setInitialPLT(thread.getInitialPLT());
        newThread.setFinalPLT(cloningScorPltHeaderService.cloneScorPltHeader(thread.getFinalPLT().getPltHeaderId()));
        adjustmentThreadRepository.save(newThread); // save to take id

        // clone nodes
        List<AdjustmentNode> adjustmentNodes = adjustmentNodeRepository.findByAdjustmentThread(thread);
        Map<AdjustmentNode, Integer> mapNodeNonDefaultAndOrder = new HashMap<>();
        List<AdjustmentNode> nodesDefault = new ArrayList<>();
        // sort adjustmentNodes by node order from 1 to n
        adjustmentNodes.sort(
                Comparator.comparing(this::findOrderOfNode));
        for (AdjustmentNode node : adjustmentNodes) {
            if (node.getAdjustmentCategoryCode().equals("Default")) {
                nodesDefault.add(node);
            }
        }

        for (AdjustmentNode node : adjustmentNodes) {
            if (node.getAdjustmentCategoryCode().equals("Base")) {
                mapNodeNonDefaultAndOrder.put(node, adjustmentNodes.indexOf(node) + 1);
            }
            // 1 /2 3/ 4 5

            if (!node.getAdjustmentCategoryCode().equals("Base") && !node.getAdjustmentCategoryCode().equals("Default")) {
                mapNodeNonDefaultAndOrder.put(node, adjustmentNodes.indexOf(node) + 1 - nodesDefault.size());
            }
        }

        cloneListOfNodesWithoutDefaultAdjustment(mapNodeNonDefaultAndOrder, newThread);
        return newThread;
    }
    public AdjustmentThreadEntity branchNewAdjustmentThread(AdjustmentThreadBranchingRequest request) throws RRException {
        AdjustmentThreadEntity adjustmentThreadEntity = adjustmentThreadRepository.getOne(request.getAdjustmentThreadId());
        if (adjustmentThreadEntity == null) {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND, 1);
        }
        if (adjustmentThreadEntity.getInitialPLT() == null) {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.NODE_NOT_FOUND, 1);
        }

       return createNewAdjustmentThread(new AdjustmentThreadCreationRequest(adjustmentThreadEntity.getInitialPLT().getPltHeaderId(), request.getCreatedBy(), request.isGenerateDefaultThread()));
    }

    public AdjustmentThreadEntity cloneThread(Long initialPlt, PltHeaderEntity clonedPlt) throws RRException {
       AdjustmentThreadEntity thread =  adjustmentThreadRepository.getByFinalPLTPltHeaderId(initialPlt);
       if (thread!=null) {
           AdjustmentThreadEntity threadClone = new AdjustmentThreadEntity();
           threadClone.setLocked(thread.getLocked());
           threadClone.setInitialPLT(clonedPlt);
           threadClone.setFinalPLT(cloningScorPltHeaderService.cloneScorPltHeader(thread.getFinalPLT().getPltHeaderId()));
           return adjustmentThreadRepository.save(threadClone);
       } else {
           throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND, 1);
       }
    }

    public void delete(Integer id) {
        this.adjustmentThreadRepository.delete(
                this.adjustmentThreadRepository.
                        findById(id)
                        .orElseThrow(throwException(THREAD_NOT_FOUND, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
