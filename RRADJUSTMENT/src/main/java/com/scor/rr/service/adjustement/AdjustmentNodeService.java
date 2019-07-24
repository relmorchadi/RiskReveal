package com.scor.rr.service.adjustement;

import com.scor.rr.domain.*;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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
                        return adjustmentnodeRepository.save(adjustmentNodeEntity);
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
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
