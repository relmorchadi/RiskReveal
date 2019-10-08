package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeOrderRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
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
    private AdjustmentnodeRepository adjustmentnodeRepository;

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

    public AdjustmentNodeEntity findOne(Integer id){
        return adjustmentnodeRepository.findById(id).orElseThrow(throwException(NODE_NOT_FOUND,NOT_FOUND));
    }

    public List<AdjustmentNodeEntity> findAll(){
        return adjustmentnodeRepository.findAll();
    }

    public List<AdjustmentNodeEntity> findByThread(Integer threadId){
        return adjustmentnodeRepository.findAll().stream().filter(adjustmentNodeEntity ->
                adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId() == threadId)
                .collect(Collectors.toList());
    }

    public void deleteNode(Integer nodeId) {
        AdjustmentNodeEntity adjustmentNodeEntity = findOne(nodeId);
        deleteParameterNode(nodeId);
        nodeOrderService.deleteByNodeId(adjustmentNodeEntity.getAdjustmentNodeId(),adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId());
        adjustmentnodeRepository.delete(adjustmentNodeEntity);
    }

    public AdjustmentNodeEntity createAdjustmentNodeFromDefaultAdjustmentReference(AdjustmentThreadEntity adjustmentThreadEntity,
                                                                                    DefaultAdjustmentNodeEntity defaultAdjustmentNodeEntity) throws RRException {

        Double lmf = null;
        Double rpmf = null;
        List<PEATData> peatData = null; //todo
        List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings = null; //todo
        DefaultRetPerBandingParamsEntity defaultRetPerBandingParamsEntity = defaultRetPerBandingParamsRepository.getByDefaultAdjustmentNodeByIdDefaultNode(defaultAdjustmentNodeEntity.getDefaultAdjustmentNodeId());
        if (defaultRetPerBandingParamsEntity != null) {
            if (defaultRetPerBandingParamsEntity.getLmf() != null) {
                lmf = defaultRetPerBandingParamsEntity.getLmf();
            }
            if (defaultRetPerBandingParamsEntity.getRpmf() != null) {
                rpmf = defaultRetPerBandingParamsEntity.getRpmf();
            }
        }
        AdjustmentNodeRequest adjustmentNodeRequest = new AdjustmentNodeRequest("Default",
                defaultAdjustmentNodeEntity.getSequence(),
                defaultAdjustmentNodeEntity.getCappedMaxExposure(),
                defaultAdjustmentNodeEntity.getAdjustmentBasis().getAdjustmentBasisId(),
                defaultAdjustmentNodeEntity.getAdjustmentType().getAdjustmentTypeId(),
                adjustmentStateRepository.getAdjustmentStateEntityByCodeInvalid().getAdjustmentStateId(),
                adjustmentThreadEntity.getAdjustmentThreadId(),
                lmf,
                rpmf,
                peatData,
                adjustmentThreadEntity.getScorPltHeaderByFkScorPltHeaderThreadPureId().getPltHeaderId(),
                adjustmentReturnPeriodBendings);
        return save(adjustmentNodeRequest);
    }

    @Transactional
    public AdjustmentNodeEntity save(AdjustmentNodeRequest adjustmentNodeRequest) throws RRException {
        log.info(" -----  Creating Node ----------");
        AdjustmentNodeEntity adjustmentNodeEntity = new AdjustmentNodeEntity();
        if(adjustmentTypeRepository.findById(adjustmentNodeRequest.getAdjustmentType()).isPresent()) {
            adjustmentNodeEntity.setAdjustmentType(adjustmentTypeRepository.findById(adjustmentNodeRequest.getAdjustmentType()).get());
            log.info("Type : {}",adjustmentNodeEntity.getAdjustmentType().getType());
            if(adjustmentBasisRepository.findById(adjustmentNodeRequest.getAdjustmentBasis()).isPresent()) {
                adjustmentNodeEntity.setAdjustmentBasis(adjustmentBasisRepository.findById(adjustmentNodeRequest.getAdjustmentBasis()).get());
                log.info("Basis : {}",adjustmentNodeEntity.getAdjustmentBasis().getAdjustmentBasisName());
                if(adjustmentStateRepository.findById(adjustmentNodeRequest.getAdjustmentState()).isPresent()) {
                    adjustmentNodeEntity.setAdjustmentState(adjustmentStateRepository.findById(adjustmentNodeRequest.getAdjustmentState()).get());
                    log.info("State : {}",adjustmentNodeEntity.getAdjustmentState().getCode());
                    if(adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).isPresent()) {
                        adjustmentNodeEntity.setAdjustmentThread(adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).get());
                        log.info("Thread getting successfull : {}",adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId());
                        adjustmentNodeEntity.setSequence(adjustmentNodeRequest.getSequence());
                        if(adjustmentNodeRequest.getAdjustmentNodeId() != 0) {
                            adjustmentNodeEntity.setAdjustmentNodeId(adjustmentNodeRequest.getAdjustmentNodeId());
                            deleteParameterNode(adjustmentNodeRequest.getAdjustmentNodeId());
                            adjustmentNodeOrderService.updateOrder(adjustmentNodeRequest.getAdjustmentNodeId(),adjustmentNodeRequest.getSequence(),adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId());
                        }
                        adjustmentNodeEntity = adjustmentnodeRepository.save(adjustmentNodeEntity);
                        log.info(" -----  Saving Parameter for Node ----------");
                        if(adjustmentNodeRequest.getAdjustmentNodeId() == 0) {
                            nodeOrderService.saveNodeOrder(new AdjustmentNodeOrderRequest(adjustmentNodeEntity.getAdjustmentNodeId(), adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId(), adjustmentNodeRequest.getSequence()));
                        }
                        if(saveParameterNode(adjustmentNodeEntity, new AdjustmentParameterRequest(adjustmentNodeRequest.getLmf() != null ? adjustmentNodeRequest.getLmf() : 0,
                                        adjustmentNodeRequest.getRpmf() != null ? adjustmentNodeRequest.getRpmf() : 0,
                                        adjustmentNodeRequest.getPeatData(),
                                        0,
                                        0,
                                        adjustmentNodeRequest.getAdjustmentReturnPeriodBendings())) !=null ) {
                            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.PARAMETER_NOT_FOUND,1);
                        }
                        return adjustmentNodeEntity;
                    } else {
                        throw new com.scor.rr.exceptions.RRException(ExceptionCodename.THREAD_NOT_FOUND,1);
                    }
                } else {
                    throw new com.scor.rr.exceptions.RRException(ExceptionCodename.STATE_NOT_FOUND,1);
                }
            } else {
                throw new com.scor.rr.exceptions.RRException(ExceptionCodename.BASIS_NOT_FOUND,1);
            }
        } else {
            throw new com.scor.rr.exceptions.RRException(ExceptionCodename.TYPE_NOT_FOUND,1);
        }
    }

    private void deleteParameterNode(Integer nodeId) {
        adjustmentScalingParameterService.deleteByNodeId(nodeId);
        periodBandingParameterService.deleteByNodeId(nodeId);
        eventBasedParameterService.deleteByNodeId(nodeId);
        if(processingService.findByNode(nodeId) != null) {
            processingService.deleteProcessingByNode(nodeId);
        }
    }

    public AdjustmentNodeEntity getAdjustmentNode(Integer nodeId) {
        return adjustmentnodeRepository.getOne(nodeId);
    }

    private Supplier saveParameterNode(AdjustmentNodeEntity node, AdjustmentParameterRequest parameterRequest) {
        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("Linear adjustment");
            if(parameterRequest.getLmf()!=0) {
                if(parameterRequest.getRpmf() != 0 || parameterRequest.getPeatData() != null || parameterRequest.getAdjustmentReturnPeriodBendings() != null) {
                    log.info("Warning : Parameter redundant");
                }
                adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getLmf(), node));
                log.info(" ----- success  Saving Parameter for Node ----------");
                return null;
            } else {
                log.info("Exception Parameter : LMF not found");
                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
            }
        }
        else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("{}",EEFFrequency.getValue());
            if(parameterRequest.getRpmf() != 0) {
                if(parameterRequest.getLmf() != 0 || parameterRequest.getPeatData() != null || parameterRequest.getAdjustmentReturnPeriodBendings() != null) {
                    log.info("Warning : Parameter redundant");
                }
                adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getRpmf(), node));
                log.info(" ----- success  Saving Parameter for Node ----------");
                return null;
            } else {
                log.info("Exception Parameter : RPMF not found");
                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
            }
        } else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
            if(parameterRequest.getPeatData() != null) {
                if(parameterRequest.getLmf() != 0 || parameterRequest.getRpmf() != 0 || parameterRequest.getAdjustmentReturnPeriodBendings() != null) {
                    log.info("Warning : Parameter redundant");
                }
                log.info("{}", NonLinearEventDriven.getValue());
                savePeatDataFile(node, parameterRequest);
                log.info(" ----- success  Saving Parameter for Node ----------");
                return null;
            } else {
                log.info("Exception Parameter : Peat data not found");
                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
            }
        }
        else if (NONLINEARRETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            if(parameterRequest.getPeatData() != null) {
                if(parameterRequest.getLmf() != 0 || parameterRequest.getRpmf() != 0 || parameterRequest.getAdjustmentReturnPeriodBendings() != null) {
                    log.info("Warning : Parameter redundant");
                }
                log.info("{}",NONLINEARRETURNPERIOD.getValue());
                savePeatDataFile(node, parameterRequest);
                log.info(" ----- success  Saving Parameter for Node ----------");
                return null;
            } else {
                log.info("Exception Parameter : Peat data not found");
                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
            }
        }
        else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType()) || NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("{}",NONLINEARRETURNEVENTPERIOD.getValue());
            if(parameterRequest.getAdjustmentReturnPeriodBendings() != null) {
                for (AdjustmentReturnPeriodBending periodBanding : parameterRequest.getAdjustmentReturnPeriodBendings()) {
                    periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(), periodBanding.getLmf(), node));
                }
                if(parameterRequest.getLmf() != 0 || parameterRequest.getRpmf() != 0 || parameterRequest.getAdjustmentReturnPeriodBendings() != null) {
                    log.info("Warning : Parameter redundant");
                }
                log.info(" ----- success  Saving Parameter for Node ----------");
                return null;
            } else {
                log.info("Exception Parameter : Return period banding not found");
                return throwException(PARAMETER_NOT_FOUND, NOT_FOUND);
            }
        }
        else return throwException(TYPE_NOT_FOUND, NOT_FOUND);
    }

    void savePeatDataFile(AdjustmentNodeEntity node, AdjustmentParameterRequest parameterRequest) {
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

    public List<AdjustmentNodeEntity> cloneNode(AdjustmentThreadEntity threadClone, AdjustmentThreadEntity threadParent) {
        List<AdjustmentNodeEntity> nodeEntities = adjustmentnodeRepository.getAdjustmentNodeEntitiesByAdjustmentThread(threadParent);
        if(nodeEntities != null) {
            List<AdjustmentNodeEntity> nodeEntitiesCloned = new ArrayList<>();
            for (AdjustmentNodeEntity nodeParent : nodeEntities) {
                AdjustmentNodeEntity nodeCloned = new AdjustmentNodeEntity(nodeParent);
                nodeCloned.setAdjustmentNodeByFkAdjustmentNodeIdCloning(nodeParent);
                nodeCloned.setAdjustmentThread(threadClone);
                nodeEntitiesCloned.add(adjustmentnodeRepository.save(nodeCloned));
            }
            return nodeEntitiesCloned;
        }
        return null;
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
