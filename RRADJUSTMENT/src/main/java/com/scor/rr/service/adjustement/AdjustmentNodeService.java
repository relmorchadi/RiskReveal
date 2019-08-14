package com.scor.rr.service.adjustement;

import com.scor.rr.configuration.file.CSVPLTFileWriter;
import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.domain.dto.adjustement.AdjustmentParameterRequest;
import com.scor.rr.domain.dto.adjustement.loss.AdjustmentReturnPeriodBending;
import com.scor.rr.domain.dto.adjustement.loss.PLTLossData;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import com.scor.rr.service.adjustement.pltAdjustment.CalculAdjustement;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    @Autowired
    AdjustmentnodeRepository adjustmentnodeRepository;

    @Autowired
    AdjustmentnodeprocessingRepository adjustmentnodeprocessingRepository;

    @Autowired
    AdjustmentBasisRepository adjustmentBasisRepository;

    @Autowired
    AdjustmentThreadRepository adjustmentThread;

    @Autowired
    AdjustmentStateRepository adjustmentStateRepository;

    @Autowired
    AdjustmentCategoryRepository adjustmentCategoryRepository;

    @Autowired
    AdjustmentTypeRepository adjustmentTypeRepository;

    @Autowired
    AdjustmentScalingParameterService adjustmentScalingParameterService;

    @Autowired
    AdjustmentReturnPeriodBandingParameterService periodBandingParameterService;

    @Autowired
    AdjustmentEventBasedParameterService eventBasedParameterService;


    public AdjustmentNodeEntity findOne(Integer id){
        return adjustmentnodeRepository.findById(id).orElseThrow(throwException(NODENOTFOUND,NOT_FOUND));
    }

    public List<AdjustmentNodeEntity> findAll(){
        return adjustmentnodeRepository.findAll();
    }

    public List<AdjustmentNodeEntity> findByThread(Integer threadId){
        return adjustmentnodeRepository.findAll().stream().filter(adjustmentNodeEntity ->
                adjustmentNodeEntity.getAdjustmentThread().getIdAdjustmentThread() == threadId)
                .collect(Collectors.toList());
    }

    public AdjustmentNodeEntity save(AdjustmentNodeRequest adjustmentNodeRequest){
        AdjustmentNodeEntity adjustmentNodeEntity = new AdjustmentNodeEntity();
        if(adjustmentTypeRepository.findById(adjustmentNodeRequest.getAdjustmentType()).isPresent()) {
            adjustmentNodeEntity.setAdjustmentType(adjustmentTypeRepository.findById(adjustmentNodeRequest.getAdjustmentType()).get());
            if(adjustmentBasisRepository.findById(adjustmentNodeRequest.getAdjustmentBasis()).isPresent()) {
                adjustmentNodeEntity.setAdjustmentBasis(adjustmentBasisRepository.findById(adjustmentNodeRequest.getAdjustmentBasis()).get());
                if(adjustmentStateRepository.findById(adjustmentNodeRequest.getAdjustmentState()).isPresent()) {
                    adjustmentNodeEntity.setAdjustmentState(adjustmentStateRepository.findById(adjustmentNodeRequest.getAdjustmentState()).get());
                    if(adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).isPresent()) {
                        adjustmentNodeEntity.setAdjustmentThread(adjustmentThread.findById(adjustmentNodeRequest.getAdjustmentThreadId()).get());
                        adjustmentNodeEntity.setSequence(adjustmentNodeRequest.getSequence());
                        adjustmentNodeEntity = adjustmentnodeRepository.save(adjustmentNodeEntity);
                        saveParameterNode(adjustmentNodeEntity,adjustmentNodeRequest);
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

    public void delete(Integer id) {
        this.adjustmentnodeRepository.delete(
                this.adjustmentnodeRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private void saveParameterNode(AdjustmentNodeEntity node, AdjustmentNodeRequest parameterRequest) {
        if (Linear.getValue().equals(node.getAdjustmentType().getType())) {
            adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getLmf(),node));
        }

        else if (EEFFrequency.getValue().equals(node.getAdjustmentType().getType())) {
            adjustmentScalingParameterService.save(new AdjustmentScalingParameterEntity(parameterRequest.getRpmf(),node));
        }
        else if (NONLINEAROEP.getValue().equals(node.getAdjustmentType().getType())) {
            for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
                periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(),periodBanding.getLmf(),node));
            }
        }
        else if (NonLinearEventDriven.getValue().equals(node.getAdjustmentType().getType())) {
            savePeatDataFile(node, parameterRequest);
        }
        else if (NONLINEAIRERETURNPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            savePeatDataFile(node, parameterRequest);
        }
        else if (NONLINEARRETURNEVENTPERIOD.getValue().equals(node.getAdjustmentType().getType())) {
            for(AdjustmentReturnPeriodBending periodBanding:parameterRequest.getAdjustmentReturnPeriodBendings()) {
                periodBandingParameterService.save(new AdjustmentReturnPeriodBandingParameterEntity(periodBanding.getReturnPeriod(), periodBanding.getLmf(), node));
            }
        }
        else throwException(TYPENOTFOUND, NOT_FOUND);
    }

    private void savePeatDataFile(AdjustmentNodeEntity node, AdjustmentNodeRequest parameterRequest) {
        try {
            File file = new File("src/main/resources/file/peatData"+node.getIdAdjustmentNode()+".csv");
            FileUtils.touch(file);
            CSVPLTFileWriter csvpltFileWriter = new CSVPLTFileWriter();
            csvpltFileWriter.writePeatData(parameterRequest.getPeatData(),file);
            eventBasedParameterService.save(new AdjustmentEventBasedParameterEntity(file.getPath(),file.getName(),node));
        } catch (IOException | com.scor.rr.exceptions.fileExceptionPlt.RRException e) {
            e.printStackTrace();
        }
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
