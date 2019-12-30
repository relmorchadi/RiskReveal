package com.scor.rr.service.adjustement;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeUpdateRequest;
import com.scor.rr.domain.dto.adjustement.loss.PEATDataRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.service.cloning.CloningScorPltHeaderService;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;
import static com.scor.rr.exceptions.ExceptionCodename.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Transactional
public class AdjustmentNodeService {

    private static final Logger log = LoggerFactory.getLogger(AdjustmentNodeService.class);

    @Autowired
    private AdjustmentNodeRepository adjustmentNodeRepository;

    @Autowired
    private AdjustmentThreadRepository adjustmentThreadRepository;

    @Autowired
    private ScalingAdjustmentParameterRepository scalingAdjustmentParameterRepository;

    @Autowired
    private EventBasedAdjustmentParameterRepository eventBasedAdjustmentParameterRepository;

    @Autowired
    private AdjustmentBasisRepository adjustmentBasisRepository;

    @Autowired
    private AdjustmentThreadRepository adjustmentThread;

    @Autowired
    private AdjustmentStateRepository adjustmentStateRepository;

    @Autowired
    private AdjustmentTypeRepository adjustmentTypeRepository;

    @Autowired
    private AdjustmentScalingParameterService adjustmentScalingParameterService;

    @Autowired
    private AdjustmentReturnPeriodBandingParameterService periodBandingParameterService;

    @Autowired
    private AdjustmentEventBasedParameterService eventBasedParameterService;

    @Autowired
    private AdjustmentNodeProcessingService processingService;

    @Autowired
    private AdjustmentNodeOrderService adjustmentNodeOrderService;

    @Autowired
    private AdjustmentNodeOrderService nodeOrderService;

    @Autowired
    private DefaultScalingAdjustmentParameterRepository defaultScalingAdjustmentParameterRepository;

    @Autowired
    private DefaultReturnPeriodBandingAdjustmentParameterRepository defaultReturnPeriodBandingAdjustmentParameterRepository;

    @Autowired
    private ReturnPeriodBandingAdjustmentParameterRepository returnPeriodBandingAdjustmentParameterRepository;

    public AdjustmentNode findOne(Integer id){
        return adjustmentNodeRepository.findById(id).orElseThrow(throwException(NODE_NOT_FOUND, NOT_FOUND));
    }

    public List<AdjustmentNode> findAll(){
        return adjustmentNodeRepository.findAll();
    }

    public List<AdjustmentNode> findByThread(Integer threadId){ // crazy ?
        return adjustmentNodeRepository.findAll().stream().filter(adjustmentNodeEntity ->
                adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId() == threadId)
                .collect(Collectors.toList());
    }

    public void deleteNode(Integer nodeId) {
        AdjustmentNode adjustmentNodeEntity = findOne(nodeId);
        if (adjustmentNodeEntity != null) {
            AdjustmentThread thread = adjustmentThreadRepository.findById(adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId()).get();
            if (thread != null) {
                AdjustmentState adjustmentState = adjustmentStateRepository.findById(1).get();
                thread.setThreadStatus(adjustmentState.getCode());
            } else {
                throw new IllegalStateException("deleteNode, thread not found, null");
            }
            deleteParameterNode(nodeId);
            processingService.deleteProcessingByNode(nodeId);
            nodeOrderService.deleteByNodeIdAndReorder(adjustmentNodeEntity.getAdjustmentNodeId(), adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId());
            adjustmentNodeRepository.delete(adjustmentNodeEntity);
        } else {
            throw new IllegalStateException("deleteNode, node not found, null");
        }

    }

//    tien : create node from default parameters found from data tables of ref data
    public AdjustmentNode createAdjustmentNodeFromDefaultAdjustmentReference(AdjustmentThread adjustmentThread,
                                                                             DefaultAdjustmentNode defaultNode) throws RRException {
        Double lmf = null; // linear, type 1
        Double rpmf = null; // EEF frequency, type 2
        List<DefaultReturnPeriodBandingAdjustmentParameter> defaultReturnPeriodBandings = null;

        // default adjustment has only 4 types, 2 tables :
        // scaling : linear, frequency
        // return period banding : EEF, OEP
        // TODO : change type id in the future
        if (defaultNode.getAdjustmentType().getAdjustmentTypeId() == 1 || defaultNode.getAdjustmentType().getAdjustmentTypeId() == 5) {
            DefaultScalingAdjustmentParameter defaultScalingAdjustmentParameter = defaultScalingAdjustmentParameterRepository.findByDefaultAdjustmentNodeDefaultAdjustmentNodeId(defaultNode.getDefaultAdjustmentNodeId());
            if (defaultNode.getAdjustmentType().getAdjustmentTypeId() == 1) {
                lmf = defaultScalingAdjustmentParameter.getAdjustmentFactor();
            } else {
                rpmf = defaultScalingAdjustmentParameter.getAdjustmentFactor();
            }
        } else if (defaultNode.getAdjustmentType().getAdjustmentTypeId() == 2 || defaultNode.getAdjustmentType().getAdjustmentTypeId() == 4) {
            defaultReturnPeriodBandings = defaultReturnPeriodBandingAdjustmentParameterRepository.findByDefaultAdjustmentNodeDefaultAdjustmentNodeId(defaultNode.getDefaultAdjustmentNodeId());
        }

        PEATDataRequest peatData = null; //todo, now default adj has not this type
//        List<ReturnPeriodBandingAdjustmentParameterRequest> adjustmentReturnPeriodBandings = null; //todo
//        DefaultRetPerBandingParamsEntity defaultRetPerBandingParamsEntity = defaultRetPerBandingParamsRepository.getByDefaultAdjustmentNodeByIdDefaultNode(defaultNode.getDefaultAdjustmentNodeId());
//        if (defaultRetPerBandingParamsEntity != null) {
//            if (defaultRetPerBandingParamsEntity.getLmf() != null) {
//                lmf = defaultRetPerBandingParamsEntity.getLmf();
//            }
//            if (defaultRetPerBandingParamsEntity.getRpmf() != null) {
//                rpmf = defaultRetPerBandingParamsEntity.getRpmf();
//            }
//        }
        List<ReturnPeriodBandingAdjustmentParameterRequest> rpbParametersRequests = new ArrayList<>();
        if (defaultReturnPeriodBandings != null && !defaultReturnPeriodBandings.isEmpty() ) {
            for (DefaultReturnPeriodBandingAdjustmentParameter defaultRPB : defaultReturnPeriodBandings) {
                rpbParametersRequests.add(new ReturnPeriodBandingAdjustmentParameterRequest(defaultRPB.getReturnPeriod(), defaultRPB.getAdjustmentFactor()));
            }
        }
        AdjustmentNodeRequest adjustmentNodeRequest = new AdjustmentNodeRequest(
                defaultNode.getSequence(),
                defaultNode.getCappedMaxExposure(),
                defaultNode.getAdjustmentBasis().getAdjustmentBasisId(),
                defaultNode.getAdjustmentType().getAdjustmentTypeId(),
                adjustmentThread.getAdjustmentThreadId(),
                lmf,
                rpmf,
                peatData,
                rpbParametersRequests);
        return createAdjustmentNode(adjustmentNodeRequest);
    }

//    @Transactional
//    public AdjustmentNode save(AdjustmentNodeRequest adjustmentNodeRequest) throws RRException {
//        AdjustmentNode adjustmentNodeEntity = new AdjustmentNode();
//        adjustmentNodeEntity.setCapped(adjustmentNodeRequest.getCapped());
//        if (adjustmentTypeRepository.findById(adjustmentNodeRequest.getAdjustmentType()).isPresent()) {
//            adjustmentNodeEntity.setAdjustmentType(adjustmentTypeRepository.findById(adjustmentNodeRequest.getAdjustmentType()).get());
//            log.info("Type : {}",adjustmentNodeEntity.getAdjustmentType().getType());
//            if (adjustmentBasisRepository.findById(adjustmentNodeRequest.getAdjustmentBasis()).isPresent()) {
//                AdjustmentBasis adjustmentBasis = adjustmentBasisRepository.findById(adjustmentNodeRequest.getAdjustmentBasis()).get();
//                adjustmentNodeEntity.setAdjustmentBasis(adjustmentBasis);
//                adjustmentNodeEntity.setAdjustmentCategory(adjustmentBasis.getAdjustmentCategory());
//                log.info("Basis : {}",adjustmentNodeEntity.getAdjustmentBasis().getAdjustmentBasisName());
//                if (adjustmentStateRepository.findById(adjustmentNodeRequest.getAdjustmentState()).isPresent()) {
//                    adjustmentNodeEntity.setAdjustmentState(adjustmentStateRepository.findById(adjustmentNodeRequest.getAdjustmentState()).get());
//                    log.info("State : {}",adjustmentNodeEntity.getAdjustmentState().getCode());
//                    if (adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).isPresent()) {
//                        adjustmentNodeEntity.setAdjustmentThread(adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).get());
//                        log.info("Thread getting successful : {}", adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId());
//                        if (adjustmentNodeRequest.getAdjustmentNodeId() != 0) { // existing node
//                            adjustmentNodeEntity.setAdjustmentNodeId(adjustmentNodeRequest.getAdjustmentNodeId());
//                            deleteParameterNode(adjustmentNodeRequest.getAdjustmentNodeId());
//                            adjustmentNodeOrderService.updateOrder(adjustmentNodeRequest.getAdjustmentNodeId(), adjustmentNodeRequest.getSequence(), adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId());
//                        }
//                        adjustmentNodeEntity = adjustmentNodeRepository.save(adjustmentNodeEntity);
//
//                        log.info(" -----  save order for node ----------");
//                        if (adjustmentNodeRequest.getAdjustmentNodeId() == 0) { // new node
//                            nodeOrderService.saveNodeOrder(new AdjustmentNodeOrderRequest(adjustmentNodeEntity.getAdjustmentNodeId(), adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId(), adjustmentNodeRequest.getSequence()));
//                        }
//
//                        log.info(" -----  save parameter for node ----------");
//                        if (saveParameterNode(adjustmentNodeEntity, new AdjustmentParameterRequest(adjustmentNodeRequest.getLmf() != null ? adjustmentNodeRequest.getLmf() : 0,
//                                        adjustmentNodeRequest.getRpmf() != null ? adjustmentNodeRequest.getRpmf() : 0,
//                                        adjustmentNodeRequest.getPeatData(),
//                                        0,
//                                        0,
//                                        adjustmentNodeRequest.getAdjustmentReturnPeriodBandings())) != null ) {
//                            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PARAMETER_NOT_FOUND,1);
//                        }
//                        return adjustmentNodeEntity;
//                    } else {
//                        throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND,1);
//                    }
//                } else {
//                    throw new com.scor.rr.exceptions.RRException(ExceptionCodename.STATE_NOT_FOUND,1);
//                }
//            } else {
//                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.BASIS_NOT_FOUND,1);
//            }
//        } else {
//            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.TYPE_NOT_FOUND,1);
//        }
//    }

    @Transactional
    public AdjustmentNode createAdjustmentNode(AdjustmentNodeRequest adjustmentNodeRequest) throws RRException {
        log.info("---------- start createAdjustmentNode ----------");

        AdjustmentNode node = new AdjustmentNode();

        if (adjustmentNodeRequest.getAdjustmentThreadId() == null) {
            throw new IllegalStateException("---------- createAdjustmentNode, thread id null, wrong ----------");
        } else {
            AdjustmentThread thread = adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).get();
            if (thread == null) {
                throw new IllegalStateException("---------- createAdjustmentNode, thread null, wrong ----------");
            } else if (BooleanUtils.isTrue(thread.getLocked())) {
                throw new IllegalStateException("---------- createAdjustmentNode, thread is locked, not permitted ----------");
            } else {
                node.setAdjustmentThread(thread);
                AdjustmentState adjustmentState = adjustmentStateRepository.findById(1).get();
                thread.setThreadStatus(adjustmentState.getCode());
            }
        }

//        if (adjustmentNodeRequest.getAdjustmentState() == null) {
//            throw new IllegalStateException("---------- createAdjustmentNode, state id null, wrong ----------");
//        } else {
//            AdjustmentState state = adjustmentStateRepository.findById(adjustmentNodeRequest.getAdjustmentState()).get();
//            if (state == null) {
//                throw new IllegalStateException("---------- createAdjustmentNode, state null, wrong ----------");
//            } else {
//                node.setAdjustmentState(state);
//            }
//        }

        AdjustmentState state = adjustmentStateRepository.getAdjustmentStateEntityByCodeInvalid();
        if (state == null) {
            throw new IllegalStateException("---------- createAdjustmentNode, state null, wrong ----------");
        } else {
            node.setAdjustmentState(state);
        }

        if (adjustmentNodeRequest.getAdjustmentBasis() == null) {
            throw new IllegalStateException("---------- createAdjustmentNode, basis id null, wrong ----------");
        } else {
            AdjustmentBasis basis = adjustmentBasisRepository.findById(adjustmentNodeRequest.getAdjustmentBasis()).get();
            if (basis == null) {
                throw new IllegalStateException("---------- createAdjustmentNode, basis null, wrong ----------");
            } else {
                node.setAdjustmentBasisCode(basis.getAdjustmentBasisName());
            }
            if (basis.getAdjustmentCategory() == null) {
                throw new IllegalStateException("---------- createAdjustmentNode, category null, wrong ----------");
            } else {
                node.setAdjustmentCategoryCode(basis.getAdjustmentCategory().getCode());
            }
        }

        if (adjustmentNodeRequest.getAdjustmentType() == null) {
            throw new IllegalStateException("---------- createAdjustmentNode, type id null, wrong ----------");
        } else {
            AdjustmentType type = adjustmentTypeRepository.findById(adjustmentNodeRequest.getAdjustmentType()).get();
            if (type == null) {
                throw new IllegalStateException("---------- createAdjustmentNode, type null, wrong ----------");
            } else {
                node.setAdjustmentTypeCode(type.getType());
            }
        }

        if (adjustmentNodeRequest.getCapped() == null) {
            throw new IllegalStateException("---------- createAdjustmentNode, capped null, wrong ----------");
        } else  {
            node.setCapped(adjustmentNodeRequest.getCapped());
        }

        // save node before creating dependencies
        node = adjustmentNodeRepository.save(node);

        // create node order
        log.info("---------- create node order ----------");
        // todo
        if (adjustmentNodeRequest.getSequence() == null) {
            throw new IllegalStateException("---------- createAdjustmentNode, node sequence null, wrong ----------");
        }
        nodeOrderService.createNodeOrder(node, adjustmentNodeRequest.getSequence());

        log.info("---------- save parameter for node ----------");
        saveParameterNode(node, adjustmentNodeRequest);

        return node;
    }

    @Transactional
    public AdjustmentNode updateAdjustmentNode(AdjustmentNodeUpdateRequest adjustmentNodeRequest) throws RRException {
        log.info("---------- start updateAdjustmentNode ----------");

        // check info request
        AdjustmentNode node;
        if (adjustmentNodeRequest.getAdjustmentNodeId() == null) {
            throw new IllegalStateException("---------- updateAdjustmentNode, node id null, wrong ----------");
        } else {
            node = adjustmentNodeRepository.findById(adjustmentNodeRequest.getAdjustmentNodeId()).get();
            if (node == null) {
                throw new IllegalStateException("---------- updateAdjustmentNode, node null, wrong ----------");
            }
        }

        // if update thread for node
        if (adjustmentNodeRequest.getAdjustmentThreadId() != null) {
            AdjustmentThread thread = adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).get();
            if (thread == null) {
                throw new IllegalStateException("---------- updateAdjustmentNode, thread null, wrong ----------");
            } else if (BooleanUtils.isTrue(thread.getLocked())) {
                throw new IllegalStateException("---------- updateAdjustmentNode, thread is locked, not permitted ----------");
            } else {
                node.setAdjustmentThread(thread);
                AdjustmentState adjustmentState = adjustmentStateRepository.findById(1).get();
                thread.setThreadStatus(adjustmentState.getCode());
            }
        }

        // if request update state
        if (adjustmentNodeRequest.getAdjustmentState() != null) {
            AdjustmentState state = adjustmentStateRepository.findById(adjustmentNodeRequest.getAdjustmentState()).get();
            if (state == null) {
                throw new IllegalStateException("---------- updateAdjustmentNode, state null, wrong ----------");
            } else {
                node.setAdjustmentState(state);
            }
        }

        // todo processing recap

        // if request update basis
        if (adjustmentNodeRequest.getAdjustmentBasis() != null) {
            AdjustmentBasis basis = adjustmentBasisRepository.findById(adjustmentNodeRequest.getAdjustmentBasis()).get();
            if (basis == null) {
                throw new IllegalStateException("---------- updateAdjustmentNode, basis null, wrong ----------");
            } else {
                node.setAdjustmentBasisCode(basis.getAdjustmentBasisName());
            }
            if (basis.getAdjustmentCategory() == null) {
                throw new IllegalStateException("---------- updateAdjustmentNode, category null, wrong ----------");
            } else {
                node.setAdjustmentCategoryCode(basis.getAdjustmentCategory().getCode());
            }
        }

        if (adjustmentNodeRequest.getAdjustmentType() != null) {
            AdjustmentType type = adjustmentTypeRepository.findById(adjustmentNodeRequest.getAdjustmentType()).get();
            if (type == null) {
                throw new IllegalStateException("---------- updateAdjustmentNode, type null, wrong ----------");
            } else {
                node.setAdjustmentTypeCode(type.getType());
            }
        }

        if (adjustmentNodeRequest.getCapped() != null) {
            node.setCapped(adjustmentNodeRequest.getCapped());
        }

        // update node order
        log.info("---------- update node order ----------");
        if (adjustmentNodeRequest.getSequence() != null) {
            adjustmentNodeOrderService.updateNodeOrder(adjustmentNodeRequest.getAdjustmentNodeId(), adjustmentNodeRequest.getSequence(), node.getAdjustmentThread().getAdjustmentThreadId());
        }

        if (adjustmentNodeRequest.getLmf() != null || adjustmentNodeRequest.getRpmf() != null || adjustmentNodeRequest.getPeatData() != null || adjustmentNodeRequest.getAdjustmentReturnPeriodBandings() != null) {
            log.info("----------  delete old parameter for node ----------");
            deleteParameterNode(adjustmentNodeRequest.getAdjustmentNodeId());

            log.info("----------  create new parameter for node ----------");
            saveParameterNode(node, adjustmentNodeRequest);
        }

        node = adjustmentNodeRepository.save(node);

        return node;
    }

//    private void deleteParameterNode(Long nodeId) {
//        adjustmentScalingParameterService.deleteByNodeId(nodeId);
//        periodBandingParameterService.deleteByNodeId(nodeId);
//        eventBasedParameterService.deleteByNodeId(nodeId);
//        if (processingService.findByNode(nodeId) != null) {
//            processingService.deleteProcessingByNode(nodeId);
//        }
//    }

    private void deleteParameterNode(Integer nodeId) {
        adjustmentScalingParameterService.deleteByNodeId(nodeId);
        periodBandingParameterService.deleteByNodeId(nodeId);
        eventBasedParameterService.deleteByNodeId(nodeId);
    }

    public AdjustmentNode getAdjustmentNode(Integer nodeId) {
        return adjustmentNodeRepository.getOne(nodeId);
    }

//    private Supplier saveParameterNode(AdjustmentNode node, AdjustmentParameterRequest parameterRequest) {
//        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
//            log.info("Linear adjustment");
//            if(parameterRequest.getLmf()!=0) {
//                if(parameterRequest.getRpmf() != 0 || parameterRequest.getPeatData() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
//                    log.info("Warning : Parameter redundant");
//                }
//                adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getLmf(), node));
//                log.info(" ----- success  Saving Parameter for Node ----------");
//                return null;
//            } else {
//                log.info("Exception Parameter : LMF not found");
//                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
//            }
//        }
//        else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
//            log.info("{}",EEFFrequency.getValue());
//            if(parameterRequest.getRpmf() != 0) {
//                if(parameterRequest.getLmf() != 0 || parameterRequest.getPeatData() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
//                    log.info("Warning : Parameter redundant");
//                }
//                adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getRpmf(), node));
//                log.info(" ----- success  Saving Parameter for Node ----------");
//                return null;
//            } else {
//                log.info("Exception Parameter : RPMF not found");
//                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
//            }
//        } else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
//            if(parameterRequest.getPeatData() != null) {
//                if(parameterRequest.getLmf() != 0 || parameterRequest.getRpmf() != 0 || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
//                    log.info("Warning : Parameter redundant");
//                }
//                log.info("{}", NonLinearEventDriven.getValue());
//                savePeatDataFile(node, parameterRequest);
//                log.info(" ----- success  Saving Parameter for Node ----------");
//                return null;
//            } else {
//                log.info("Exception Parameter : Peat data not found");
//                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
//            }
//        }
//        else if (NONLINEARRETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
//            if(parameterRequest.getPeatData() != null) {
//                if(parameterRequest.getLmf() != 0 || parameterRequest.getRpmf() != 0 || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
//                    log.info("Warning : Parameter redundant");
//                }
//                log.info("{}",NONLINEARRETURNPERIOD.getValue());
//                savePeatDataFile(node, parameterRequest);
//                log.info(" ----- success  Saving Parameter for Node ----------");
//                return null;
//            } else {
//                log.info("Exception Parameter : Peat data not found");
//                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
//            }
//        }
//        else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType()) || NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
//            log.info("{}",NONLINEARRETURNEVENTPERIOD.getValue());
//            if(parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
//                for (AdjustmentReturnPeriodBandingParameterEntity periodBanding : parameterRequest.getAdjustmentReturnPeriodBandings()) {
//                    periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(), periodBanding.getFactor(), node));
//                }
//                if(parameterRequest.getLmf() != 0 || parameterRequest.getRpmf() != 0 || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
//                    log.info("Warning : Parameter redundant");
//                }
//                log.info(" ----- success  Saving Parameter for Node ----------");
//                return null;
//            } else {
//                log.info("Exception Parameter : Return period banding not found");
//                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
//            }
//        }
//        else return throwException(TYPE_NOT_FOUND, NOT_FOUND);
//    }

    private void saveParameterNode(AdjustmentNode node, AdjustmentNodeRequest parameterRequest) {
        if (LINEAR.getValue().equals(node.getAdjustmentTypeCode())) {
            log.info("saveParameterNode, linear adjustment");
            if (parameterRequest.getLmf() != null) {
                if (parameterRequest.getRpmf() != null || parameterRequest.getPeatData() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
                    log.info("saveParameterNode, warning : parameter redundant out of parameterRequest.getLmf()");
                }
                adjustmentScalingParameterService.save(new ScalingAdjustmentParameter(parameterRequest.getLmf(), node));
                log.info(" ----- saveParameterNode, success saving parameter for node ----------");
            } else {
                throw new IllegalStateException("---------- saveParameterNode, exception parameter : lmf not found ----------");
            }
        }
        else if (EEF_FREQUENCY.getValue().equals(node.getAdjustmentTypeCode())) {
            log.info("saveParameterNode, {}",EEF_FREQUENCY.getValue());
            if (parameterRequest.getRpmf() != null) {
                if (parameterRequest.getLmf() != null || parameterRequest.getPeatData() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
                    log.info("saveParameterNode, warning : parameter redundant out of parameterRequest.getRpmf()");
                }
                adjustmentScalingParameterService.save(new ScalingAdjustmentParameter(parameterRequest.getRpmf(), node));
                log.info(" ----- saveParameterNode, success saving parameter for node ----------");
            } else {
                throw new IllegalStateException("---------- saveParameterNode, exception parameter : rpmf not found ----------");
            }
        }
        else if (NONLINEAR_EVENT_DRIVEN.getValue().equals(node.getAdjustmentTypeCode())) {
            if (parameterRequest.getPeatData() != null) {
                if (parameterRequest.getLmf() != null || parameterRequest.getRpmf() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
                    log.info("saveParameterNode, warning : parameter redundant out of parameterRequest.getPeatData()");
                }
                log.info("saveParameterNode, {}", NONLINEAR_EVENT_DRIVEN.getValue());
                savePeatDataFile(node, parameterRequest);
                log.info(" ----- saveParameterNode, success saving parameter for node ----------");
            } else {
                throw new IllegalStateException("---------- saveParameterNode, exception parameter : paet data not found ----------");
            }
        }
        else if (NONLINEAR_EVENT_PERIOD_DRIVEN.getValue().equals(node.getAdjustmentTypeCode())) {
            if (parameterRequest.getPeatData() != null) {
                if(parameterRequest.getLmf() != null || parameterRequest.getRpmf() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
                    log.info("saveParameterNode, warning : parameter redundant out of parameterRequest.getLmf");
                }
                log.info("saveParameterNode, {}", NONLINEAR_EVENT_PERIOD_DRIVEN.getValue());
                savePeatDataFile(node, parameterRequest);
                log.info(" ----- saveParameterNode, success saving parameter for node ----------");
            } else {
                throw new IllegalStateException("---------- saveParameterNode, exception parameter : paet data not found ----------");
            }
        }
        else if (NONLINEAR_EEF_RPB.getValue().equals(node.getAdjustmentTypeCode()) || NONLINEAR_OEP_RPB.getValue().equals(node.getAdjustmentTypeCode())) {
            log.info("saveParameterNode, {}", node.getAdjustmentTypeCode());
            if (parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
                for (ReturnPeriodBandingAdjustmentParameterRequest adjustmentReturnPeriodBanding : parameterRequest.getAdjustmentReturnPeriodBandings()) {
                    periodBandingParameterService.save(new ReturnPeriodBandingAdjustmentParameter(adjustmentReturnPeriodBanding.getReturnPeriod(), adjustmentReturnPeriodBanding.getAdjustmentFactor(), node));
                }
                if (parameterRequest.getLmf() != null || parameterRequest.getRpmf() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
                    log.info("saveParameterNode, warning : parameter redundant out of parameterRequest.getLmf");
                }
                log.info(" ----- saveParameterNode, success saving parameter for node ----------");
            } else {
                throw new IllegalStateException("---------- saveParameterNode, exception parameter : return period banding not found ----------");
            }
        } else {
            throw new IllegalStateException("---------- saveParameterNode, type not found ----------");
        }
    }

    void savePeatDataFile(AdjustmentNode node, AdjustmentNodeRequest parameterRequest) {
        try {
            log.info("------ Saving PEAT DATA FILE ------");
            eventBasedParameterService.save(new EventBasedAdjustmentParameter(parameterRequest.getPeatData().getPath(), parameterRequest.getPeatData().getFileName(), node));
            log.info("------ Success save file ------");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<AdjustmentNode> cloneNode(AdjustmentThread threadClone, AdjustmentThread threadParent) {
        List<AdjustmentNode> nodeEntities = adjustmentNodeRepository.findByAdjustmentThread(threadParent);
        if(nodeEntities != null) {
            List<AdjustmentNode> nodeEntitiesCloned = new ArrayList<>();
            for (AdjustmentNode nodeParent : nodeEntities) {
                AdjustmentNode nodeCloned = new AdjustmentNode(nodeParent);
                nodeCloned.setCloningSource(nodeParent);
                nodeCloned.setAdjustmentThread(threadClone);
                nodeEntitiesCloned.add(adjustmentNodeRepository.save(nodeCloned));
            }
            return nodeEntitiesCloned;
        }
        return null;
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
