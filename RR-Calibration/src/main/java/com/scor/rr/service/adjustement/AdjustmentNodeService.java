package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeOrderRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeUpdateRequest;
import com.scor.rr.domain.dto.adjustement.loss.PEATData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.service.cloning.CloningScorPltHeader;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private CloningScorPltHeader cloningScorPltHeader;

    @Autowired
    private DefaultRetPerBandingParamsRepository defaultRetPerBandingParamsRepository;

    //TODO: implementation for updating node

    public AdjustmentNode findOne(Integer id){
        return adjustmentNodeRepository.findById(id).orElseThrow(throwException(NODE_NOT_FOUND,NOT_FOUND));
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
        deleteParameterNode(nodeId);
        processingService.deleteProcessingByNode(nodeId);
        nodeOrderService.deleteByNodeIdAndReorder(adjustmentNodeEntity.getAdjustmentNodeId(), adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId());
        adjustmentNodeRepository.delete(adjustmentNodeEntity);
    }

    public AdjustmentNode createAdjustmentNodeFromDefaultAdjustmentReference(AdjustmentThreadEntity adjustmentThreadEntity,
                                                                             DefaultAdjustmentNode defaultAdjustmentNodeEntity) throws RRException {

        Double lmf = null;
        Double rpmf = null;
        List<PEATData> peatData = null; //todo
        List<ReturnPeriodBandingAdjustmentParameterRequest> adjustmentReturnPeriodBandings = null; //todo
        DefaultRetPerBandingParamsEntity defaultRetPerBandingParamsEntity = defaultRetPerBandingParamsRepository.getByDefaultAdjustmentNodeByIdDefaultNode(defaultAdjustmentNodeEntity.getDefaultAdjustmentNodeId());
        if (defaultRetPerBandingParamsEntity != null) {
            if (defaultRetPerBandingParamsEntity.getLmf() != null) {
                lmf = defaultRetPerBandingParamsEntity.getLmf();
            }
            if (defaultRetPerBandingParamsEntity.getRpmf() != null) {
                rpmf = defaultRetPerBandingParamsEntity.getRpmf();
            }
        }
        AdjustmentNodeRequest adjustmentNodeRequest = new AdjustmentNodeRequest(
                defaultAdjustmentNodeEntity.getSequence(),
                defaultAdjustmentNodeEntity.getCappedMaxExposure(),
                defaultAdjustmentNodeEntity.getAdjustmentBasis().getAdjustmentBasisId(),
                defaultAdjustmentNodeEntity.getAdjustmentType().getAdjustmentTypeId(),
                adjustmentThreadEntity.getAdjustmentThreadId(),
                lmf,
                rpmf,
                peatData,
                adjustmentReturnPeriodBandings);
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
            AdjustmentThreadEntity thread = adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).get();
            if (thread == null) {
                throw new IllegalStateException("---------- createAdjustmentNode, thread null, wrong ----------");
            } else {
                node.setAdjustmentThread(thread);
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
            AdjustmentThreadEntity thread = adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).get();
            if (thread == null) {
                throw new IllegalStateException("---------- updateAdjustmentNode, thread null, wrong ----------");
            } else {
                node.setAdjustmentThread(thread);
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
        adjustmentNodeOrderService.updateNodeOrder(adjustmentNodeRequest.getAdjustmentNodeId(), adjustmentNodeRequest.getSequence(), node.getAdjustmentThread().getAdjustmentThreadId());

        log.info("----------  delete old parameter for node ----------");
        deleteParameterNode(adjustmentNodeRequest.getAdjustmentNodeId());

        log.info("----------  create new parameter for node ----------");
        saveParameterNode(node, adjustmentNodeRequest);

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
        if (Linear.getValue().equals(node.getAdjustmentTypeCode())) {
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
        else if (EEFFrequency.getValue().equals(node.getAdjustmentTypeCode())) {
            log.info("saveParameterNode, {}",EEFFrequency.getValue());
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
        else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentTypeCode())) {
            if (parameterRequest.getPeatData() != null) {
                if (parameterRequest.getLmf() != null || parameterRequest.getRpmf() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
                    log.info("saveParameterNode, warning : parameter redundant out of parameterRequest.getPeatData()");
                }
                log.info("saveParameterNode, {}", NonLinearEventDriven.getValue());
                savePeatDataFile(node, parameterRequest);
                log.info(" ----- saveParameterNode, success saving parameter for node ----------");
            } else {
                throw new IllegalStateException("---------- saveParameterNode, exception parameter : paet data not found ----------");
            }
        }
        else if (NONLINEARRETURNPERIOD.getValue().equals(node.getAdjustmentTypeCode())) {
            if (parameterRequest.getPeatData() != null) {
                if(parameterRequest.getLmf() != null || parameterRequest.getRpmf() != null || parameterRequest.getAdjustmentReturnPeriodBandings() != null) {
                    log.info("saveParameterNode, warning : parameter redundant out of parameterRequest.getLmf");
                }
                log.info("saveParameterNode, {}",NONLINEARRETURNPERIOD.getValue());
                savePeatDataFile(node, parameterRequest);
                log.info(" ----- saveParameterNode, success saving parameter for node ----------");
            } else {
                throw new IllegalStateException("---------- saveParameterNode, exception parameter : paet data not found ----------");
            }
        }
        else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentTypeCode()) || NONLINEAROEP.getValue().equals(node.getAdjustmentTypeCode())) {
            log.info("saveParameterNode, {}",NONLINEARRETURNEVENTPERIOD.getValue());
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
        }
        else {
            throw new IllegalStateException("---------- saveParameterNode, type not found ----------");
        }
    }

    void savePeatDataFile(AdjustmentNode node, AdjustmentNodeRequest parameterRequest) {
        try {
            log.info("------ Saving PEAT DATA FILE ------");
            File file = new File("src/main/resources/file/peatData"+node.getAdjustmentNodeId()+".csv");
            FileUtils.touch(file);
            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
            csvpltFileWriter.writePeatData(parameterRequest.getPeatData(),file);
            eventBasedParameterService.save(new AdjustmentEventBasedParameterEntity(file.getPath(),file.getName(),node));
            log.info("------ Success save file ------");
        } catch (IOException | RRException e) {
            e.printStackTrace();
        }
    }

    public List<AdjustmentNode> cloneNode(AdjustmentThreadEntity threadClone, AdjustmentThreadEntity threadParent) {
        List<AdjustmentNode> nodeEntities = adjustmentNodeRepository.findByAdjustmentThread(threadParent);
        if(nodeEntities != null) {
            List<AdjustmentNode> nodeEntitiesCloned = new ArrayList<>();
            for (AdjustmentNode nodeParent : nodeEntities) {
                AdjustmentNode nodeCloned = new AdjustmentNode(nodeParent);
                nodeCloned.setAdjustmentNodeCloning(nodeParent);
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
