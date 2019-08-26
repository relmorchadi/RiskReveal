package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.UtilsMethode;
import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.*;
import static com.scor.rr.domain.dto.adjustement.AdjustmentTypeEnum.NONLINEARRETURNEVENTPERIOD;
import static com.scor.rr.exceptions.ExceptionCodename.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AdjustmentNodeService {

    private static final Logger log = LoggerFactory.getLogger(AdjustmentNodeService.class);

    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

    @Autowired
    AdjustmentBasisRepository adjustmentBasisRepository;

    @Autowired
    AdjustmentThreadRepository adjustmentThread;

    @Autowired
    AdjustmentStateRepository adjustmentStateRepository;

    @Autowired
    AdjustmentTypeRepository adjustmentTypeRepository;

    @Autowired
    AdjustmentScalingParameterService adjustmentScalingParameterService;

    @Autowired
    AdjustmentReturnPeriodBandingParameterService periodBandingParameterService;

    @Autowired
    AdjustmentEventBasedParameterService eventBasedParameterService;

    @Autowired
    AdjustmentNodeProcessingService processingService;

    @Autowired
    AdjustmentNodeOrderService adjustmentNodeOrderService;


    //TODO: implementation for updating node

    public AdjustmentNodeEntity findOne(Integer id){
        return adjustmentnodeRepository.findById(id).orElseThrow(throwException(NODENOTFOUND,NOT_FOUND));
    }

    public List<AdjustmentNodeEntity> findAll(){
        return adjustmentnodeRepository.findAll();
    }

    public List<AdjustmentNodeEntity> findByThread(Integer threadId){
        return adjustmentnodeRepository.findAll().stream().filter(adjustmentNodeEntity ->
                adjustmentNodeEntity.getAdjustmentThread().getAdjustmentThreadId() == threadId)
                .collect(Collectors.toList());
    }
    @Transactional
    public AdjustmentNodeEntity save(AdjustmentNodeRequest adjustmentNodeRequest){
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
                        }
                        adjustmentNodeEntity = adjustmentnodeRepository.save(adjustmentNodeEntity);
                        log.info(" -----  Saving Parameter for Node ----------");
                        saveParameterNode(adjustmentNodeEntity, new AdjustmentParameterRequest(adjustmentNodeRequest.getLmf() != null ? adjustmentNodeRequest.getLmf() : 0, adjustmentNodeRequest.getRpmf() != null ? adjustmentNodeRequest.getRpmf() : 0, adjustmentNodeRequest.getPeatData(), 0, 0, adjustmentNodeRequest.getAdjustmentReturnPeriodBendings()));
                        return adjustmentNodeEntity;
                    } else {
                        throwException(THREADNOTFOUND, NOT_FOUND);
                        return null;
                    }
                } else {
                    throwException(STATENOTFOUND, NOT_FOUND);
                    return null;
                }
            } else {
                throwException(BASISNOTFOUND, NOT_FOUND);
                return null;
            }
        } else {
            throwException(TYPENOTFOUND, NOT_FOUND);
            return null;
        }
    }

    private void deleteParameterNode(Integer nodeId) {
        adjustmentScalingParameterService.deleteByNodeId(nodeId);
        periodBandingParameterService.deleteByNodeId(nodeId);
        eventBasedParameterService.deleteByNodeId(nodeId);
        processingService.delete(nodeId);
    }

    public AdjustmentNodeEntity getAdjustmentNode(Integer nodeId) {
        return adjustmentnodeRepository.getOne(nodeId);
    }

    private void saveParameterNode(AdjustmentNodeEntity node, AdjustmentParameterRequest parameterRequest) {
        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("Linear adjustment");
            adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getLmf(),node));
            log.info(" ----- success  Saving Parameter for Node ----------");
        }
        else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("{}",EEFFrequency.getValue());
            adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getRpmf(),node));
            log.info(" ----- success  Saving Parameter for Node ----------");
        }
        else if (NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("{}",NONLINEAROEP.getValue());
            if(parameterRequest.getAdjustmentReturnPeriodBendings()!=null) {
                for (AdjustmentReturnPeriodBending periodBanding : parameterRequest.getAdjustmentReturnPeriodBendings()) {
                    periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(), periodBanding.getLmf(), node));
                }
                log.info(" ----- success  Saving Parameter for Node ----------");
            } else throwException(PARAMETERNOTFOUND, NOT_FOUND);
        }
        else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("{}",NonLinearEventDriven.getValue());
            savePeatDataFile(node, parameterRequest);
            log.info(" ----- success  Saving Parameter for Node ----------");
        }
        else if (NONLINEAIRERETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("{}",NONLINEAIRERETURNPERIOD.getValue());
            savePeatDataFile(node, parameterRequest);
            log.info(" ----- success  Saving Parameter for Node ----------");
        }
        else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            log.info("{}",NONLINEARRETURNEVENTPERIOD.getValue());
            for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
                periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(), periodBanding.getLmf(), node));
            }
            log.info(" ----- success  Saving Parameter for Node ----------");
        }
        else throwException(TYPENOTFOUND, NOT_FOUND);
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
        } catch (IOException | com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
            e.printStackTrace();
        }
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
